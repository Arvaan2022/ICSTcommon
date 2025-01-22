package com.icst.commonmodule.design.activity.gpsurgery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icst.commonmodule.R
import com.icst.commonmodule.databinding.ItemRowGpSurgeryBinding
import com.icst.commonmodule.model.GpSurgeryModel



class GpSurgeryAdapter(var context: Context, private var subCategory: List<GpSurgeryModel.Data>) :
    RecyclerView.Adapter<GpSurgeryAdapter.SubViewHolder>() {

    lateinit var binding: ItemRowGpSurgeryBinding

    override fun onBindViewHolder(p0: SubViewHolder, p1: Int) {
        p0.bind(p1, subCategory)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubViewHolder {
        binding = ItemRowGpSurgeryBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return SubViewHolder()
    }

    override fun getItemCount(): Int = subCategory.size


    inner class SubViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, subCategoryList: List<GpSurgeryModel.Data>) {
            binding.tvSurgeryType.text = subCategoryList[position].surgeryName
            binding.tvGpSurgeryTime.text = subCategoryList[position].date
            if (subCategoryList[position].type == "past"
                || subCategoryList[position].type == "Past"
            ) {
                binding.tvGpSurgeryTag.visibility = View.VISIBLE
                binding.tvGpSurgeryTag.text = context.resources.getString(R.string.txt_Past)
            } else {
                binding.tvGpSurgeryTag.visibility = View.VISIBLE
                binding.tvGpSurgeryTag.text = context.resources.getString(R.string.txt_current)
            }
        }
    }
}