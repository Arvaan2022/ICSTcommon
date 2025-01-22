package com.icst.commonmodule.design.activity.videoPlayer

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityVideoPlayerBinding
import com.icst.commonmodule.design.activity.learning.LearningActivity
import com.icst.commonmodule.design.activity.videoPlayer.adapter.EduVideoAdapter
import com.icst.commonmodule.design.activity.videoPlayer.adapter.EducationDetailVideoAdapter
import com.icst.commonmodule.model.CategoryVideo
import com.icst.commonmodule.model.EducationVideoTaskModel
import com.icst.commonmodule.model.GetVideoCountModel
import com.icst.commonmodule.repository.videoPlayer.VideoPlayerRepository
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.ExoPlayerUtils
import com.icst.commonmodule.utils.setMinuteText
import com.potyvideo.library.globalEnums.EnumResizeMode
import kotlinx.coroutines.launch

class VideoPlayerViewModel : ViewModel() {
    private val videoPlayerRepository = VideoPlayerRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()

    var videoCountList = ArrayList<GetVideoCountModel.Data>()
    var educationVideo = arrayListOf<EducationVideoTaskModel.Data>()
    var categoryVideos = ArrayList<CategoryVideo>()
    var currentPosition = -1
    var videoID = 0
    var categoryID = 0
    lateinit var exoPlayerUtils: ExoPlayerUtils

    lateinit var binding: ActivityVideoPlayerBinding

    private val _categoryVideosResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val categoryVideosResponse: LiveData<Resource<Any?>>
        get() {
            return _categoryVideosResponse
        }

    private val _getVideoCountResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val getVideoCountResponse: LiveData<Resource<Any?>>
        get() {
            return _getVideoCountResponse
        }

    private val _storeVideoCountResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val storeVideoCountResponse: LiveData<Resource<Any?>>
        get() {
            return _storeVideoCountResponse
        }

    fun getCategoryVideos(id: Int, context: Context) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _categoryVideosResponse.value = videoPlayerRepository.getCategoryVideos(
                id = id,
                context = context
            )
//            activityBase.get()!!.dismissProgress()
        }
    }

    fun getVideoCount(
        id: String,
        slug: String,
        context: Context
    ) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _getVideoCountResponse.value = videoPlayerRepository.getVideoCount(
                id = id,
                slug = slug,
                context = context
            )
//            activityBase.get()!!.dismissProgress()
        }
    }

    fun storeVideoCount(
        id: String,
        context: Context
    ) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _storeVideoCountResponse.value = videoPlayerRepository.storeVideoCount(
                videoId = id,
                context = context
            )
//            activityBase.get()!!.dismissProgress()
        }
    }

    //--------------------
    private val _getTaskEducationVideoResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val getTaskEducationVideoResponse: LiveData<Resource<Any?>>
        get() {
            return _getTaskEducationVideoResponse
        }

    fun getTaskEducationVideoApi(
        id: String,
        context: Context
    ) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _getTaskEducationVideoResponse.value = videoPlayerRepository.getTaskEducationVideoApi(
                id = id,
                context = context
            )
//            activityBase.get()!!.dismissProgress()
        }
    }

    //----------------

    fun setVideoCountResponse(response: GetVideoCountModel, binding: ActivityVideoPlayerBinding) {
        videoCountList.addAll(response.data)
        if (activityBase.get()!!.intent.getStringExtra("EDUCATION").toString() == "EDUCATION") {
            val catId = ArrayList<String>()
            Log.e("TAG", "observer: ")
            for (cat in educationVideo) {
                if (videoCountList.single { it.videoId.toInt() == cat.id }.isWatch == "1") {
                    cat.isWatch = true
                    catId.add(cat.id.toString())
                }
            }

            Log.e("TAG", "===> categoryVideos $catId")
            val countId = ArrayList<String>()
            for (count in videoCountList) {
                countId.add(count.videoId)
            }
            Log.e("TAG", "===> educationVideo $countId _$currentPosition")
            this.binding.rcvEduVideoList.adapter =
                EduVideoAdapter(
                    educationVideo,
                    adapterItemClick,
                    currentPosition
                )
            if (videoCountList.single { it.videoId == educationVideo[currentPosition].id.toString() }.isWatch == "1") {
                //  tvVideoPlayerSeen.visibility = View.VISIBLE
                this.binding.ivVideoPlayerSeen.visibility = View.VISIBLE
                // tvVideoPlayerSeen.text = getString(R.string.video_is_watched)
                this.binding.ivVideoPlayerSeen.setColorFilter(0)
                this.binding.ivVideoPlayerSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        activityBase.get()!!,
                        R.drawable.ic_green_tick
                    )
                )
                this.binding.ivVideoPlayerSeen.isEnabled = false
            } else {

                this.binding.ivVideoPlayerSeen.visibility = View.VISIBLE

                this.binding.ivVideoPlayerSeen.setColorFilter(
                    ContextCompat.getColor(activityBase.get()!!, R.color.Grey_400),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                this.binding.ivVideoPlayerSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        activityBase.get()!!,
                        R.drawable.ic_circle_tick
                    )
                )
                this.binding.ivVideoPlayerSeen.isEnabled = true
            }

        } else {

            val catId = ArrayList<String>()
            for (cat in categoryVideos) {
                if (videoCountList.first { it.videoId.toInt() == cat.id }.isWatch == "1") {
                    cat.isWatch = true
                    catId.add(cat.id.toString())
                }
            }

            val countId = ArrayList<String>()
            for (count in videoCountList) {
                countId.add(count.videoId)
            }

            this.binding.rcvEduVideoList.adapter =
                EducationDetailVideoAdapter(
                    categoryVideos,
                    adapterItemClick,
                    currentPosition
                )
            if (videoCountList.first { it.videoId == categoryVideos[currentPosition].id.toString() }.isWatch == "1") {
                //  tvVideoPlayerSeen.visibility = View.VISIBLE
                this.binding.ivVideoPlayerSeen.visibility = View.VISIBLE
                // tvVideoPlayerSeen.text = getString(R.string.video_is_watched)
                this.binding.ivVideoPlayerSeen.setColorFilter(0)
                this.binding.ivVideoPlayerSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        activityBase.get()!!,
                        R.drawable.ic_green_tick
                    )
                )
                this.binding.ivVideoPlayerSeen.isEnabled = false
            } else {

                this.binding.ivVideoPlayerSeen.visibility = View.VISIBLE

                this.binding.ivVideoPlayerSeen.setColorFilter(
                    ContextCompat.getColor(activityBase.get()!!, R.color.Grey_400),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                this.binding.ivVideoPlayerSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        activityBase.get()!!,
                        R.drawable.ic_circle_tick
                    )
                )
                this.binding.ivVideoPlayerSeen.isEnabled = true
            }
        }
    }

    private val adapterItemClick = fun(pos: Int) {
        if (categoryVideos.isNotEmpty())


            if (categoryVideos[pos].type == "video") {
                currentPosition = pos
                videoID = categoryVideos[currentPosition].id
                categoryID = categoryVideos[currentPosition].categoryId
                binding.nsvVideoPlayer.fullScroll(View.FOCUS_UP)

                if (categoryVideos[currentPosition].videoType == "video" || categoryVideos[currentPosition].videoType == "wistia") {
                    exoPlayerUtils.destroyExoPlayer()
                    initializeVideoPlayer(categoryVideos[currentPosition].videoUrl)

                }

                if (videoCountList.first { it.videoId.toInt() == videoID }.isWatch == "1") {
                    // tvVideoPlayerSeen.visibility = View.VISIBLE
                    binding.ivVideoPlayerSeen.visibility = View.VISIBLE
                    // tvVideoPlayerSeen.text = getString(R.string.video_is_watched)
                    binding.ivVideoPlayerSeen.setColorFilter(0)
                    binding.ivVideoPlayerSeen.setImageDrawable(
                        ContextCompat.getDrawable(
                            activityBase.get()!!,
                            R.drawable.ic_green_tick
                        )
                    )
                    binding.ivVideoPlayerSeen.isEnabled = false
                } else {
                    //  tvVideoPlayerSeen.visibility = View.VISIBLE
                    binding.ivVideoPlayerSeen.visibility = View.VISIBLE
                    // tvVideoPlayerSeen.text = getString(R.string.video_not_watched)
                    binding.ivVideoPlayerSeen.setColorFilter(
                        ContextCompat.getColor(activityBase.get()!!, R.color.Grey_400),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                    binding.ivVideoPlayerSeen.setImageDrawable(
                        ContextCompat.getDrawable(
                            activityBase.get()!!,
                            R.drawable.ic_circle_tick
                        )
                    )
                    binding.ivVideoPlayerSeen.isEnabled = true
                }


                setData()

            } else {
                activityBase.get()!!.startActivity(
                    Intent(activityBase.get()!!, LearningActivity::class.java)
                        .putExtra(
                            activityBase.get()!!.getString(R.string.key_is_full_article),
                            false
                        )
                        .putExtra(
                            activityBase.get()!!.getString(R.string.key_from_sub_adapter),
                            true
                        )
                        .putExtra(
                            activityBase.get()!!.getString(R.string.key_pdf_data),
                            categoryVideos[pos]
                        )
                        .putExtra("video_id", categoryVideos[pos].id)
                        .putExtra(
                            "is_watch_completed",
                            videoCountList.first { it.videoId.toInt() == categoryVideos[pos].id }.isWatch == "1"
                        )
                )
            }
    }

    fun setData() {

        if (activityBase.get()!!.intent.getStringExtra("EDUCATION").toString() == "EDUCATION") {
            if (currentPosition != -1) {
                if (educationVideo[currentPosition].type == "video") {

                    binding.layoutVideo.visibility = View.VISIBLE
                    binding.tvVideoPlayerTitle.text = educationVideo[currentPosition].name
                    binding.tvVideoPlayerDuration.text = setMinuteText(
                        educationVideo[currentPosition].hour.toString(),
                        educationVideo[currentPosition].min.toString(),
                        educationVideo[currentPosition].sec.toString()
                    )

                    binding.tvVideoPlayerDesc.text =
                        educationVideo[currentPosition].descriptionWelsh.let {
                            HtmlCompat.fromHtml(
                                it,
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            )
                        }
                } else {
                    binding.layoutVideo.visibility = View.GONE
                }
            } else {
                binding.layoutVideo.visibility = View.GONE
            }

        } else {
            if (currentPosition != -1) {
                if (categoryVideos[currentPosition].type == "video") {

                    binding.layoutVideo.visibility = View.VISIBLE
                    binding.tvVideoPlayerTitle.text = categoryVideos[currentPosition].name
                    binding.tvVideoPlayerDuration.text = setMinuteText(
                        categoryVideos[currentPosition].hour.toString(),
                        categoryVideos[currentPosition].minute.toString(),
                        categoryVideos[currentPosition].second.toString()
                    )

                    binding.tvVideoPlayerDesc.text = HtmlCompat.fromHtml(
                        categoryVideos[currentPosition].description,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                } else {
                    binding.layoutVideo.visibility = View.GONE
                }
            } else {
                binding.layoutVideo.visibility = View.GONE
            }
        }
    }

    fun setDataTest(position: Int) {
        if (activityBase.get()!!.intent.getStringExtra("EDUCATION").toString() == "EDUCATION") {
            if (position != -1) {
                if (educationVideo[position].type == "video") {

                    binding.layoutVideo.visibility = View.VISIBLE
                    binding.tvVideoPlayerTitle.text = educationVideo[position].name
                    binding.tvVideoPlayerDuration.text = String.format(
                        activityBase.get()!!.getString(
                            R.string.time_format, educationVideo[position].hour,
                            educationVideo[position].min, educationVideo[position].sec
                        )
                    )

                    binding.tvVideoPlayerDesc.text = HtmlCompat.fromHtml(
                        educationVideo[position].descriptionWelsh,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                } else {
                    binding.layoutVideo.visibility = View.GONE
                }
            } else {
                binding.layoutVideo.visibility = View.GONE
            }
        } else {
            if (position != -1) {
                if (categoryVideos[position].type == "video") {

                    binding.layoutVideo.visibility = View.VISIBLE
                    binding.tvVideoPlayerTitle.text = categoryVideos[position].name
                    binding.tvVideoPlayerDuration.text = String.format(
                        activityBase.get()!!.getString(
                            R.string.time_format
                        ), categoryVideos[position].hour,
                        categoryVideos[position].minute, categoryVideos[position].second

                    )

                    binding.tvVideoPlayerDesc.text = HtmlCompat.fromHtml(
                        categoryVideos[position].description,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )
                } else {
                    binding.layoutVideo.visibility = View.GONE
                }
            } else {
                binding.layoutVideo.visibility = View.GONE
            }
        }

    }

    fun initializeVideoPlayer(videoUrl: String) {
        binding.videoView.visibility = View.VISIBLE

        binding.videoView.apply {
            setResizeMode(EnumResizeMode.FILL) // sync with attrs
            playerView.player = exoPlayerUtils.getSimpleExoPlayer(videoUrl)
            mute.setOnClickListener {
                exoPlayerUtils.unMutePlayer(this@apply)
            }
            unMute.setOnClickListener {
                exoPlayerUtils.mutePlayer(this@apply)
            }
            backwardView.setOnClickListener {
                exoPlayerUtils.seekBackward()
            }
            forwardView.setOnClickListener {
                exoPlayerUtils.seekForward()
            }
        }
    }

    fun initializePlayers() {
        if (activityBase.get()!!.intent.getStringExtra("EDUCATION").toString() == "EDUCATION") {
//             Log.e("TAG", "aaaaaaaaaaaaaa:$currentPosi/tion ", )
//             Log.e("TAG", "aaaaaaaaaaaaaa:${educationVideo.size} ", )
            if (currentPosition > -1) {
                setData()
                Log.e("TAG", "aaaaaaaaaaaaaa:${educationVideo[currentPosition].videoType} ")
                Log.e("TAG", "aaaaaaaaaaaaaa:${educationVideo[currentPosition].videoType} ")
                if (educationVideo[currentPosition].videoType == "video" || educationVideo[currentPosition].videoType == "wistia") {
                    initializeVideoPlayer(educationVideo[currentPosition].videoUrl) // ToDo: For normal video player initialization
                }
            }
        } else {
            if (currentPosition > -1) {
                setData()
                if (categoryVideos[currentPosition].videoType == "video" || categoryVideos[currentPosition].videoType == "wistia") {
                    initializeVideoPlayer(categoryVideos[currentPosition].videoUrl) // ToDo: For normal video player initialization
                }
            }
        }
    }
}