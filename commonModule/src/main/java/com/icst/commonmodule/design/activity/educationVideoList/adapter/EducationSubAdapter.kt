package com.icst.commonmodule.design.activity.educationVideoList.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ItemRowNewVideoCntainerBinding
import com.icst.commonmodule.design.activity.learning.LearningActivity
import com.icst.commonmodule.design.activity.videoPlayer.VideoPlayerActivity
import com.icst.commonmodule.model.CategoryVideo
import com.icst.commonmodule.utils.setMinuteText


class EducationSubAdapter(
    private val activity: ActivityBase,
    val categoryVideo: List<CategoryVideo>,
) : RecyclerView.Adapter<EducationSubAdapter.EducationViewHolder>() {

    private val TAG = javaClass.simpleName
    var minute: String = "00"
    var hour: String = "00"
    var second: String = "00"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val binding = ItemRowNewVideoCntainerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EducationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoryVideo.size
    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }


    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        holder.setValue(position)
    }

    inner class EducationViewHolder(val binding: ItemRowNewVideoCntainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setValue(position: Int) {
            try {
                binding.rowTvVideoTitle.text = categoryVideo[position].name

                hour = categoryVideo[position].hour.toString()
                minute = categoryVideo[position].minute.toString()
                second = categoryVideo[position].second.toString()

                if (categoryVideo[position].type == "video") {
                    binding.rowEducationVideoPlay.visibility = View.VISIBLE
                } else {
                    binding.rowEducationVideoPlay.visibility = View.GONE
                }
                if (categoryVideo[position].type == "video") {
                    binding.ivRowNewVideoContentType.setImageDrawable(
                        ContextCompat.getDrawable(activity, R.drawable.ic_play_blue_bg)
                    )
//                    if (categoryVideo[position].hour > 0) {
//                        val minuteText = "$minute " + activity.getString(R.string.minutes)
//                        binding.rowTvVideoDescription.text =
//                            minuteText /*"$hour:$minute:$second min"*/

//                    } else {
//                        val minuteText = "$minute " + activity.getString(R.string.minutes)
                    binding.rowTvVideoDescription.text = setMinuteText(
                        categoryVideo[position].hour.toString(),
                        categoryVideo[position].minute.toString(),
                        categoryVideo[position].second.toString()
                    )
//                            minuteText /*"$minute:$second min"*/

//                    }
                } else {
                    binding.ivRowNewVideoContentType.setImageDrawable(
                        ContextCompat.getDrawable(activity, R.drawable.ic_note_hamburger)
                    )
//                    if (categoryVideo[position].hour > 0) {
//                        val minuteText = "$minute " + activity.getString(R.string.minutes)
//                        binding.rowTvVideoDescription.text =
//                            minuteText /* "$hour:$minute:$second min"*/

//                    } else {
//                        val minuteText = "$minute " + activity.getString(R.string.minutes)
                    binding.rowTvVideoDescription.text = setMinuteText(
                        categoryVideo[position].hour.toString(),
                        categoryVideo[position].minute.toString(),
                        categoryVideo[position].second.toString()
                    )
//                            minuteText /* "$minute:$second min"*/

//                    }
                }

                if (categoryVideo[position].videoThumb != "") {
                    binding.rowEducationVideoThumb.setImageURI(categoryVideo[position].videoThumb)
                }

                binding.rowRlvEducationContainer.setOnClickListener {
                    if (categoryVideo[position].type == "video") {
                        val intent = Intent(activity, VideoPlayerActivity::class.java)
                        intent.putExtra(
                            activity.resources.getString(R.string.key_from_),
                            com.icst.commonmodule.utils.TAG.MY_INFORMATION_ACTIVITY.myTag
                        )
                        val bundle = Bundle()
                        bundle.putString("postion_value", position.toString())
                        bundle.putSerializable(
                            "VideoList",
                            categoryVideo as ArrayList<CategoryVideo>
                        )
                        intent.putExtras(bundle)

                        activity.startActivity(intent)
                    } else {
                        activity.startActivity(
                            Intent(activity, LearningActivity::class.java)
                                .putExtra(
                                    activity.resources.getString(R.string.key_is_full_article),
                                    false
                                )
                                .putExtra(
                                    activity.resources.getString(R.string.key_from_sub_adapter),
                                    true
                                )
                                .putExtra(
                                    activity.resources.getString(R.string.key_pdf_data),
                                    categoryVideo[position]
                                )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


    }
}