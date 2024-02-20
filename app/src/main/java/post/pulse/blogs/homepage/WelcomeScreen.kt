package post.pulse.blogs.homepage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import post.pulse.blogs.R
import post.pulse.blogs.data.models.UsersData
import post.pulse.blogs.databinding.WelcomeScreenLayoutBinding

class WelcomeScreen : Fragment(){
    private var _binding: WelcomeScreenLayoutBinding? = null
    private val binding get() = _binding
    private val userData: UsersData by lazy {
        arguments?.getParcelable("userData") as? UsersData ?: UsersData("","","","","")
    }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WelcomeScreenLayoutBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNavigation()
        binding?.userName?.text = "Welcome ${userData.name}"
        binding?.userName?.setOnClickListener {
            navController = findNavController()
            navController.navigate(R.id.action_welcomeScreen_to_sectionPage)
        }
    }

    private fun setUpNavigation() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

    }
}