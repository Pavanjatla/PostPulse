package post.pulse.blogs.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import post.pulse.blogs.databinding.SectionItemLayoutBinding

class SectionsAdapter : RecyclerView.Adapter<SectionsAdapter.ViewHolder>() {

    private var sectionsList : ArrayList<String> = ArrayList()

    fun updateSections(sections : ArrayList<String>){
        sectionsList.clear()
        sectionsList.addAll(sections)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SectionItemLayoutBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val section = sectionsList[position]
        holder.binding.sectionTitle.text = section
    }

    override fun getItemCount(): Int {
        return sectionsList.size
    }

    inner class ViewHolder(val binding: SectionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}