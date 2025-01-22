package com.icst.commonmodule.design.activity.normalVideoPlayer.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icst.commonmodule.R
import com.icst.commonmodule.databinding.ListItemNormalVideoBinding
import com.icst.commonmodule.model.NormalValueDataModel
import com.icst.commonmodule.utils.setMinuteText

class NormalValueVideoAdapter(
    val context: Context,
    private val normalValueVideoList: List<NormalValueDataModel.Data.VideoData>,
    private val mPosition: Int,
    val onVideoClick: (Int) -> Unit,
) : RecyclerView.Adapter<NormalValueVideoAdapter.NormalValueViewHolder>() {


    companion object {
        val TAG: String = NormalValueVideoAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NormalValueViewHolder {
        val binding = ListItemNormalVideoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NormalValueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NormalValueViewHolder, position: Int) {
        holder.setValue(position)

    }

    override fun getItemCount(): Int {
        return normalValueVideoList.size
    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }


    inner class NormalValueViewHolder(val binding: ListItemNormalVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setValue(position: Int) {
            Log.e(TAG, "onBindViewHolder: $normalValueVideoList")
            val normalValueModel = normalValueVideoList[position]
            binding.apply {
                if (position == mPosition) {
                    parentVideoThumbContainer.setBackgroundResource(R.drawable.btn_blue_radius_3)
                    tvRowVideoTitle.setTextColor(Color.WHITE)
                    rowTvVideoTime.setTextColor(Color.WHITE)
                    rowTvVideoDescription.setTextColor(Color.WHITE)
                } else {
                    parentVideoThumbContainer.setBackgroundColor(Color.WHITE)
                    tvRowVideoTitle.setTextColor(Color.BLACK)
                    rowTvVideoTime.setTextColor(Color.BLACK)
                    rowTvVideoDescription.setTextColor(Color.BLACK)
                }

                Glide.with(context).load(normalValueModel.thmubUrl).into(rowVideoThumb)

                rowTvVideoTime.text =
                    setMinuteText(
                        normalValueModel.hour?:"",
                        normalValueModel.min?:"",
                        normalValueModel.sec?:""
                    )
                tvRowVideoTitle.text = normalValueModel.title
//                rowTvVideoDescription.text = HtmlCompat.fromHtml(normalValueModel.des, HtmlCompat.FROM_HTML_MODE_COMPACT)
                rowTvVideoDescription.text = HtmlCompat.fromHtml(normalValueModel.des?:"", 0)

                parentVideoThumbContainer.setOnClickListener {
                    onVideoClick.invoke(position)
                }
            }
        }

    }

}