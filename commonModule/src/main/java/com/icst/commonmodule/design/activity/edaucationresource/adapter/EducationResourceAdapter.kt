package com.icst.commonmodule.design.activity.edaucationresource.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icst.commonmodule.databinding.ItemViewEduResourcesBinding
import com.icst.commonmodule.model.EducationResourceModel

class EducationResourceAdapter(
    val context: Context,
    private val eduResourcesList: List<EducationResourceModel.Data>
) : RecyclerView.Adapter<EducationResourceAdapter.EducationResourceViewHolder>() {

    lateinit var binding: ItemViewEduResourcesBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationResourceViewHolder {
        binding = ItemViewEduResourcesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EducationResourceViewHolder()
    }

    override fun onBindViewHolder(holder: EducationResourceViewHolder, position: Int) {
        holder.setValue(position)
    }

    override fun getItemCount(): Int {
        return eduResourcesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }

    inner class EducationResourceViewHolder() : RecyclerView.ViewHolder(binding.root) {
        /*val tvCategoryName: MyCustomTextView =
            itemView.findViewById(R.id.tv_item_edu_res_cat_name)
        val rvEduResources: RecyclerView = itemView.findViewById(R.id.rv_item_edu_res_list)*/

        fun setValue(position: Int) {
            val eduResourcesModel = eduResourcesList[position]
            binding.apply {
                tvItemEduResCatName.text = eduResourcesModel.subCatName
                rvItemEduResList.setHasFixedSize(true)
                rvItemEduResList.layoutManager = LinearLayoutManager(context)
                rvItemEduResList.adapter =
                    EducationResourceDataAdapter(eduResourcesModel.resourceData)
            }
        }

    }
}