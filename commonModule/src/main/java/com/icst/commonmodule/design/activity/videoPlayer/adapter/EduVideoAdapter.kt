package com.icst.commonmodule.design.activity.videoPlayer.adapter

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icst.commonmodule.R
import com.icst.commonmodule.databinding.RowVideoBinding
import com.icst.commonmodule.model.EducationVideoTaskModel
import com.icst.commonmodule.utils.setMinuteText



class EduVideoAdapter(
    val videoList: ArrayList<EducationVideoTaskModel.Data>,
    val adapterItemClick: (Int) -> Unit,
    var playPosition: Int
) : RecyclerView.Adapter<EduVideoAdapter.EducationDetailVideoHolder>() {


    private val TAG = javaClass.simpleName
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationDetailVideoHolder {
        context = parent.context
        val binding = RowVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EducationDetailVideoHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }


    override fun onBindViewHolder(holder: EducationDetailVideoHolder, position: Int) {
        holder.setValue(position)
    }

    inner class EducationDetailVideoHolder(val binding: RowVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setValue(position: Int) {
            try {
                val currentVideoModel = videoList[position]

                if (playPosition == position) {
                    binding.parentVideoThumbContainer.setBackgroundResource(R.drawable.btn_blue_radius_3)
                    binding.tvRowVideoTitle.setTextColor(Color.WHITE)
                    binding.tvRowVideoDesc.setTextColor(Color.WHITE)
                    binding.rowTvVideoDescription.setTextColor(Color.WHITE)
                } else {
                    binding.parentVideoThumbContainer.setBackgroundColor(Color.WHITE)
                    binding.tvRowVideoTitle.setTextColor(Color.BLACK)
                    binding.tvRowVideoDesc.setTextColor(Color.BLACK)
                    binding.rowTvVideoDescription.setTextColor(Color.BLACK)
                }

                binding.parentVideoThumbContainer.setOnClickListener {
                    adapterItemClick.invoke(position)
                    playPosition = position
                    notifyDataSetChanged()
                }

                if (videoList.isNotEmpty()) {
                    Log.e(TAG, "videoList is not empty.......")

                    binding.tvRowVideoTitle.text = currentVideoModel.name
                    binding.tvRowVideoDesc.text = Html.fromHtml(currentVideoModel.descriptionWelsh, Html.FROM_HTML_MODE_COMPACT);

                    /*binding.tvRowVideoDesc.text =
                        HtmlCompat.fromHtml(
                            currentVideoModel.descriptionWelsh,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )*/

                    Glide.with(context).load(currentVideoModel.videoThumb).into( binding.rowVideoThumb)

                    binding.rowTvVideoDescription.text = setMinuteText(
                        currentVideoModel.hour.toString(),
                        currentVideoModel.min.toString(),
                        currentVideoModel.sec.toString()
                    )
                    if (currentVideoModel.type == "video") {
                        binding.imgVideoPlay.visibility = View.VISIBLE
                        binding.rowVideoContent.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_play_blue_bg
                            )
                        )
                    } else {
                        binding.imgVideoPlay.visibility = View.GONE
                        binding.rowVideoContent.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_note_hamburger
                            )
                        )
                    }
                    // ic_note_hamburger

                    if (currentVideoModel.isWatch) {
                        binding.ivRowVideoSeen.visibility = View.VISIBLE
                        Log.e(TAG, "onBindViewHolder: ${currentVideoModel.name}")
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}