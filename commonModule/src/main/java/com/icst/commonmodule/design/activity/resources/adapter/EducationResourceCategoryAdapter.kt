package com.icst.commonmodule.design.activity.resources.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icst.commonmodule.databinding.ItemViewEduResourceCategoryBinding
import com.icst.commonmodule.model.EducationResourceCategoryModel

class EducationResourceCategoryAdapter(
    val context: Context,
    private val educationResourceList: List<EducationResourceCategoryModel.Data>,
    val resourceOnClick: (Int) -> Unit,

    ) :
    RecyclerView.Adapter<EducationResourceCategoryAdapter.EducationResourceCategoryViewHolder>() {

    lateinit var binding: ItemViewEduResourceCategoryBinding


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EducationResourceCategoryViewHolder {
        binding = ItemViewEduResourceCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EducationResourceCategoryViewHolder()
    }

    override fun onBindViewHolder(
        holderCategory: EducationResourceCategoryViewHolder,
        position: Int
    ) {
        holderCategory.setValue(position)
    }

    override fun getItemCount(): Int {
        return educationResourceList.size
    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }


    inner class EducationResourceCategoryViewHolder() : RecyclerView.ViewHolder(binding.root) {

        fun setValue(position: Int) {
            val resourceDataModel = educationResourceList[position]
            binding.apply {
                Glide.with(context).load(resourceDataModel.iconUrl).into(ivItemEduResCatThumb)
                tvItemEduResCatTitle.text = resourceDataModel.title
                llItemEduResCatContainer.setOnClickListener {
                    resourceOnClick.invoke(position)
                }
            }
        }
    }
}