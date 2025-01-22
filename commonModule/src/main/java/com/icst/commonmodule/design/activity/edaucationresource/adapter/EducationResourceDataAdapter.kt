package com.icst.commonmodule.design.activity.edaucationresource.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icst.commonmodule.databinding.ItemViewEduResDataItemBinding
import com.icst.commonmodule.design.activity.educationResourceView.EducationResourceViewerActivity
import com.icst.commonmodule.model.EducationResourceModel
import com.icst.commonmodule.utils.Constant.RESOURCE_PDF_LINK
import com.icst.commonmodule.utils.Constant.RESOURCE_URL_LINK

class EducationResourceDataAdapter(
    private val educationResList: List<EducationResourceModel.Data.ResourceData>
) : RecyclerView.Adapter<EducationResourceDataAdapter.EducationResourceDataViewHolder>() {
    lateinit var context: Context

    lateinit var binding: ItemViewEduResDataItemBinding


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EducationResourceDataViewHolder {
        context = parent.context
        binding = ItemViewEduResDataItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EducationResourceDataViewHolder()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    override fun onBindViewHolder(holder: EducationResourceDataViewHolder, position: Int) {
        holder.setValue(position)
    }

    override fun getItemCount(): Int {
        return educationResList.size
    }

    inner class EducationResourceDataViewHolder() : RecyclerView.ViewHolder(binding.root) {

        fun setValue(position: Int) {
            val educationDataModel = educationResList[position]
            binding.apply {
                Glide.with(context).load(educationDataModel.iconUrl)
                    .into(ivItemEduResDataThumb)
                tvItemEduResDataTitle.text = educationDataModel.title
                tvItemEduResDataDesc.text = educationDataModel.des

                llItemEduResDataContainer.setOnClickListener {
                    val intent = Intent(context, EducationResourceViewerActivity::class.java)

                    when (getExtention(educationDataModel.link)) {
                        "pdf" -> {
                            if (educationDataModel.link.isEmpty())
                                intent.putExtra(RESOURCE_PDF_LINK, educationDataModel.pdfUrl)
                            else
                                intent.putExtra(RESOURCE_PDF_LINK, educationDataModel.link)
                        }
                        else -> {
                            if (educationDataModel.pdfUrl.isEmpty())
                                intent.putExtra(RESOURCE_URL_LINK, educationDataModel.link)
                            else
                                intent.putExtra(RESOURCE_URL_LINK, educationDataModel.pdfUrl)
                        }

                    }

                    context.startActivity(intent)
                }
            }
        }

        fun getExtention(url: String): String {
            val filenameArray = url.split("\\.".toRegex()).toTypedArray()
            return filenameArray[filenameArray.size - 1]
        }

    }
}