package post.pulse.blogs.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import post.pulse.blogs.databinding.SectionPageLayoutBinding

class SectionsPage :Fragment() {

    var _binding: SectionPageLayoutBinding? = null
    val binding get() = _binding!!
    private lateinit var adapter : SectionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SectionPageLayoutBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SectionsAdapter()
        adapter.updateSections(arrayListOf("Home","Trending","Latest","Popular","Top Rated","Most Viewed","Most Liked","Most Commented","Most Shared","Most Saved", "Most Discussed","Most Reviewed"))
        binding.rvSections.adapter = adapter
    }

}