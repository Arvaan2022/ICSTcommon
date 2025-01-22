package com.icst.commonmodule.design.activity.normalVideoPlayer

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.icst.commonmodule.R
import com.icst.commonmodule.app.DataBindingActivity
import com.icst.commonmodule.databinding.ActivityNormalValueVideoPlayerCommonBinding
import com.icst.commonmodule.design.activity.normalVideoPlayer.adapter.NormalValueVideoAdapter
import com.icst.commonmodule.model.NormalValueDataModel
import com.icst.commonmodule.utils.Constant.ASTHMA_VIDEO_LIST
import com.icst.commonmodule.utils.Constant.ASTHMA_VIDEO_POSITION
import com.icst.commonmodule.utils.Constant.FROM_EDUCATION
import com.icst.commonmodule.utils.Constant.FROM_INTENT
import com.icst.commonmodule.utils.ExoPlayerUtils
import com.icst.commonmodule.utils.setMinuteText
import com.potyvideo.library.globalEnums.EnumResizeMode


class NormalValueVideoPlayerActivity : DataBindingActivity() {

    val binding: ActivityNormalValueVideoPlayerCommonBinding by binding(R.layout.activity_normal_value_video_player_common)

    companion object {
        val TAG: String = NormalValueVideoPlayerActivity::class.java.simpleName
    }

    private var videoList = ArrayList<NormalValueDataModel.Data.VideoData>()
    private var mPosition = 0
    lateinit var fromIntent: String
    private lateinit var exoPlayerUtils: ExoPlayerUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        intent.apply {
            videoList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                getSerializableExtra(
                    ASTHMA_VIDEO_LIST,
                    ArrayList<NormalValueDataModel.Data.VideoData>()::class.java
                ) as ArrayList<NormalValueDataModel.Data.VideoData>
            } else {
                getSerializableExtra(ASTHMA_VIDEO_LIST) as ArrayList<NormalValueDataModel.Data.VideoData>
            }


            mPosition = getIntExtra(ASTHMA_VIDEO_POSITION, 0)
            fromIntent = getStringExtra(FROM_INTENT).toString()

        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        }


    override fun onResume() {
        super.onResume()
        initViews()
        setOnClickListener()
    }

    private fun initViews() {
        exoPlayerUtils = ExoPlayerUtils(this)
        binding.include5.toolbarBack.isVisible = true
        binding.include5.toolbarProfile.isVisible = false
        binding.include5.toolbarLogo.isVisible = false
        binding.include5.toolbarTitle.isVisible = true
        binding.include5.toolbarTitle.text = buildString {
            append("Education")
        }

        val currentVideo = videoList[mPosition]
        if (fromIntent == FROM_EDUCATION) {
            setUpCurrentVideo(currentVideo, false)
        } else {
            setUpCurrentVideo(currentVideo, true)
        }
    }

    private fun setOnClickListener() {
        binding.include5.toolbarBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpCurrentVideo(
        currentVideo: NormalValueDataModel.Data.VideoData,
        isNormalValue: Boolean
    ) {
        if (!currentVideo.videoUrl.isNullOrEmpty()){
            binding.ivThumbnail.isVisible =false
            binding.videoView.isVisible=true
            initializeVideoPlayer(currentVideo.videoUrl)
        }else{
            binding.ivThumbnail.isVisible=true
            binding.videoView.isVisible=false
            Glide.with(this).load(currentVideo.thmubUrl).into(binding.ivThumbnail)
        }

        binding.tvVideoPlayerTitle.text = currentVideo.title
        binding.tvVideoPlayerDuration.text =
            setMinuteText(currentVideo.hour ?: "", currentVideo.min ?: "", currentVideo.sec ?: "")
        loadUrl(currentVideo.des ?: "")

        if (isNormalValue) {
            Log.e(TAG, "setUpCurrentVideo: ${videoList.size}")

            binding.rcvEduVideoList.setHasFixedSize(true)
            binding.rcvEduVideoList.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
            )
            binding.rcvEduVideoList.adapter = NormalValueVideoAdapter(
                this, videoList, mPosition, onVideoClick
            )
        } else {
            binding.rcvEduVideoList.visibility = View.GONE
            binding.viewNormalValueMiddleLine.visibility = View.GONE
            binding.tvNormalValuesRelatedVideos.visibility = View.GONE
        }


    }

    private fun initializeVideoPlayer(videoUrl: String) {
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

    override fun onPause() {
        super.onPause()
        binding.videoView.stopPlayer()
        exoPlayerUtils.releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        exoPlayerUtils.releasePlayer()
    }





    private val onVideoClick = fun(position: Int) {
        mPosition = position
        exoPlayerUtils.destroyExoPlayer()
        val currentVideo = videoList[mPosition]
        if (fromIntent == FROM_EDUCATION) {
            setUpCurrentVideo(currentVideo, false)
        } else {
            setUpCurrentVideo(currentVideo, true)
        }
    }

    private fun loadUrl(url: String) {
//        binding.tvVideoPlayerDesc.text = HtmlCompat.fromHtml(url, HtmlCompat.FROM_HTML_MODE_COMPACT)
        binding.tvVideoPlayerDesc.text = Html.fromHtml(url, 0)
    }

}