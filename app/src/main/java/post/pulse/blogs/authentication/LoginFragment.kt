package post.pulse.blogs.authentication

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import post.pulse.blogs.R
import post.pulse.blogs.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var navController: NavController


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavigation()
        auth = Firebase.auth
        binding?.signInButton?.setSize(SignInButton.SIZE_WIDE)

        binding?.signInButton?.setOnClickListener {
            binding?.apply {
                signInButton.isEnabled = false
                progressBar.visibility = View.VISIBLE
                signInRequest()
            }
        }
    }
    private fun setUpNavigation() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

    }


    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val credential = oneTapClient.getSignInCredentialFromIntent(it.data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                binding?.progressBar?.visibility = View.VISIBLE
                googleSignIn(idToken)
            } else {
                binding?.progressBar?.visibility = View.GONE
                binding?.signInButton?.isEnabled = true
                Toast.makeText(requireContext(), "Token not taken", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding?.progressBar?.visibility = View.GONE
            binding?.signInButton?.isEnabled = true

        }
    }

    private fun signInRequest() {
        oneTapClient = Identity.getSignInClient(requireContext())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                binding?.progressBar?.visibility = View.GONE
                resultLauncher.launch(
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                )
            }
            .addOnFailureListener { e ->
                binding?.signInButton?.isEnabled = true
                binding?.progressBar?.visibility = View.GONE
                e.localizedMessage?.let {
                    Log.e("LoginActivity", it)
                }
                Toast.makeText(requireContext(), e.message + "failed0", Toast.LENGTH_SHORT).show()
            }
    }
    private fun googleSignIn(token: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(), "Login Successful", Toast.LENGTH_SHORT
                    ).show()
                    binding?.progressBar?.visibility = View.GONE
                    val bundle = Bundle()
                    bundle.putString("name", auth.currentUser?.displayName)
                    navController = findNavController()
                    navController.navigate(R.id.action_loginFragment_to_mainActivity, bundle)
//                    checkUserExist()
                } else {
                    Toast.makeText(
                        requireContext(),
                        task.exception?.localizedMessage + "failed2",
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
                    binding?.signInButton?.isEnabled = true
                    binding?.progressBar?.visibility = View.GONE
                }

            }
    }

//    private fun checkUserExist() {
//        if (auth.currentUser != null) {
//            val userId = auth.currentUser!!.uid
//            val mUserCollection = FirebaseFirestore.getInstance().collection("Users")
//            mUserCollection.document(userId).get()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val documentSnapshot = task.result
//                        if (documentSnapshot.exists()) {
//                            binding?.progressBar?.visibility = View.GONE
//                            Log.d("LoginActivity", "signInWithCredential:success")
//                            val intent = Intent()
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                            startActivity(intent)
//                        }
//                    } else {
//                        mUserCollection.document(userId).set(
//                            SetupUser(
//                                userId = userId,
//                                displayName = auth.currentUser!!.displayName!!,
//                                email = auth.currentUser!!.email!!,
//                                photoUrl = auth.currentUser!!.photoUrl.toString()
//                            )
//                        ).addOnCompleteListener { task2 ->
//                            if (task2.isSuccessful) {
//                                binding?.progressBar?.visibility = View.GONE
//                                val intent = Intent()
//                                intent.putExtra("isNewUser", true)
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                                startActivity(intent)
//
//                            } else {
//                                binding?.progressBar?.visibility = View.GONE
//                                binding?.signInButton?.isEnabled = true
//                            }
//                        }
//                    }
//                }
//        } else {
//            binding?.progressBar?.visibility = View.GONE
//            Toast.makeText(
//                requireContext(),
//                "Something went wrong",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
}