package com.icst.commonmodule.design.activity.videoPlayer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityVideoPlayerBinding
import com.icst.commonmodule.model.CategoryVideo
import com.icst.commonmodule.model.EducationDetails
import com.icst.commonmodule.model.EducationVideoTaskModel
import com.icst.commonmodule.model.GetVideoCountModel
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.ExoPlayerUtils
import com.icst.commonmodule.utils.TAG


class VideoPlayerActivity : ActivityBase() {
    private val viewModel = VideoPlayerViewModel()
    val binding: ActivityVideoPlayerBinding by binding(R.layout.activity_video_player)

    private var slugType = "ALL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        viewModel.activityBase.set(this)
        viewModel.binding = binding
    }

    override fun onResume() {
        super.onResume()
        binding.nsvVideoPlayer.scrollTo(0, 0)
        initToolbars()
        getIntentData()
        observer()
    }

    private fun initToolbars() {
        viewModel.exoPlayerUtils = ExoPlayerUtils(this)

        binding.include5.toolbarProfile.isVisible = false
        binding.include5.toolbarBack.isVisible = true
        binding.include5.toolbarLogo.isVisible = false
        binding.include5.toolbarTitle.isVisible = true
        binding.include5.toolbarTitle.text = buildString { append("Education") }

        viewModel.categoryVideos = ArrayList()
        viewModel.educationVideo = ArrayList()
        viewModel.videoCountList = ArrayList()
        setClickListener()
    }

    private fun getIntentData() {

        try {
            when {
                intent.getStringExtra(getString(R.string.key_from_)) == TAG.EDUCATION_FRAGMENT.myTag -> {
                    viewModel.videoID = intent.getIntExtra(getString(R.string.key_video_id), 0)
                    viewModel.categoryID =
                        intent.getIntExtra(getString(R.string.key_category_id), 0)
                    viewModel.getCategoryVideos(
                        id = viewModel.categoryID,
                        context = this
                    )
                }

                intent.getStringExtra(getString(R.string.key_from_)) == TAG.MY_INFORMATION_ACTIVITY.myTag -> {
                    if (intent.getSerializableExtraData(
                            "VideoList",
                            ArrayList<CategoryVideo>()::class.java
                        ) != null
                    ) {
                        viewModel.categoryVideos = intent.getSerializableExtraData(
                            "VideoList",
                            ArrayList<CategoryVideo>()::class.java
                        ) ?: arrayListOf()
                    }


                    for (i in viewModel.categoryVideos.indices) {
                        if (viewModel.categoryVideos[i].type == "video") {
                            viewModel.currentPosition = i
                            break
                        }
                    }
                    viewModel.setData()

                    if (viewModel.categoryVideos[viewModel.currentPosition].videoType == "video" || viewModel.categoryVideos[viewModel.currentPosition].videoType == "wistia") {
                        viewModel.categoryID =
                            viewModel.categoryVideos[viewModel.currentPosition].categoryId
                        viewModel.videoID = viewModel.categoryVideos[viewModel.currentPosition].id
                        viewModel.initializeVideoPlayer(viewModel.categoryVideos[viewModel.currentPosition].videoUrl)
                    }
                    Log.e("TAG", "VideoPlayerActivity: ===> MY_INFORMATION_ACTIVITY ==> $slugType")
                    viewModel.getVideoCount(
                        id = viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                }
                //TODO -> NOTING PASSED
                intent.getStringExtra(getString(R.string.key_from_)) == TAG.EDUCATION_SUB_ADAPTER.myTag -> {

                    if (intent.getSerializableExtraData(
                            "VideoList",
                            ArrayList<CategoryVideo>()::class.java
                        ) != null
                    ) {
                        viewModel.categoryVideos = intent.getSerializableExtraData(
                            "VideoList",
                            ArrayList<CategoryVideo>()::class.java
                        ) ?: arrayListOf()
                    }

                    for (i in viewModel.categoryVideos.indices) {
                        if (viewModel.categoryVideos[i].type == "video") {
                            viewModel.currentPosition = i
                            break
                        }
                    }
                    viewModel.currentPosition = intent.getStringExtra("postion_value")!!.toInt()

                    Log.e("TAG", "Current Postion isss=> " + viewModel.currentPosition)


                    viewModel.setDataTest(viewModel.currentPosition)

                    if (viewModel.categoryVideos[viewModel.currentPosition].videoType == "video" ||
                        viewModel.categoryVideos[viewModel.currentPosition].videoType == "wistia"
                    ) {
                        viewModel.categoryID =
                            viewModel.categoryVideos[viewModel.currentPosition].categoryId
                        viewModel.videoID = viewModel.categoryVideos[viewModel.currentPosition].id
                        viewModel.initializeVideoPlayer(viewModel.categoryVideos[viewModel.currentPosition.toInt()].videoUrl) // ToDo: For normal video player initialization
                    }
                    viewModel.getVideoCount(
                        id = viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                }

                intent.getStringExtra(getString(R.string.key_from_)) == TAG.MANAGE_ASTHMA_ACTIVITY.myTag -> {

                    if (intent.getSerializableExtraData(
                            "VideoList",
                            ArrayList<CategoryVideo>()::class.java
                        ) != null
                    ) {
                        viewModel.categoryVideos = intent.getSerializableExtraData(
                            "VideoList",
                            ArrayList<CategoryVideo>()::class.java
                        ) ?: arrayListOf()
                    }
                    for (i in viewModel.categoryVideos.indices) {
                        if (viewModel.categoryVideos[i].type == "video") {
                            viewModel.currentPosition = i
                            break
                        }
                    }
                    viewModel.currentPosition = intent.getStringExtra("postion_value")!!.toInt()

                    /*  rcv_edu_video_list.adapter =
                          EducationDetailVideoAdapter(categoryVideos, this, pos!!.toInt())*/

                    viewModel.setDataTest(viewModel.currentPosition)

                    if (viewModel.categoryVideos[viewModel.currentPosition].videoType == "video" ||
                        viewModel.categoryVideos[viewModel.currentPosition].videoType == "wistia"
                    ) {
                        viewModel.categoryID =
                            viewModel.categoryVideos[viewModel.currentPosition].categoryId
                        viewModel.videoID = viewModel.categoryVideos[viewModel.currentPosition].id
                        viewModel.initializeVideoPlayer(viewModel.categoryVideos[viewModel.currentPosition].videoUrl)
                    }
                    viewModel.getVideoCount(
                        id = viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                }

                intent.getStringExtra(getString(R.string.key_from_)) == TAG.MY_MEDICATION_DETAIL_ACTIVITY.myTag -> {
                    viewModel.currentPosition = intent.getIntExtra("postion_value", 0)
                    if (intent.getSerializableExtraData(
                            "VideoList",
                            ArrayList<CategoryVideo>()::class.java
                        ) != null
                    ) {
                        viewModel.categoryVideos = intent.getSerializableExtraData(
                            "VideoList",
                            ArrayList<CategoryVideo>()::class.java
                        ) ?: arrayListOf()
                    }

                    viewModel.setDataTest(viewModel.currentPosition)

                    if (viewModel.categoryVideos[viewModel.currentPosition].videoType == "video" || viewModel.categoryVideos[viewModel.currentPosition].videoType == "wistia") {
                        viewModel.categoryID =
                            viewModel.categoryVideos[viewModel.currentPosition].categoryId
                        viewModel.videoID = viewModel.categoryVideos[viewModel.currentPosition].id
                        viewModel.initializeVideoPlayer(viewModel.categoryVideos[viewModel.currentPosition].videoUrl)
                    }
                    viewModel.getVideoCount(
                        id = viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                }

                intent.getStringExtra("EDUCATION").toString() == "EDUCATION" -> {
                    viewModel.getTaskEducationVideoApi(
                        id = intent.getStringExtra("EDUCATION_ID").toString(),
                        context = this
                    )
                }

                intent.getStringExtra("EDUCATION").toString() == "EDUCATION" -> {

//                    categoryVideos = bundle!!.getSerializable("VideoList") as List<CategoryVideo>

                    Log.e("TAG", "wwwwwwwwwwwwww: ${viewModel.educationVideo}")
                    for (i in viewModel.educationVideo.indices) {
                        if (viewModel.educationVideo[i].type == "video") {
                            viewModel.currentPosition = i
                            break
                        }
                    }


                    viewModel.setData()

                    if (viewModel.educationVideo[viewModel.currentPosition].videoType == "video" || viewModel.educationVideo[viewModel.currentPosition].videoType == "wistia") {
                        viewModel.categoryID =
                            viewModel.educationVideo[viewModel.currentPosition].id!!
                        viewModel.videoID = viewModel.educationVideo[viewModel.currentPosition].id!!
                        viewModel.initializeVideoPlayer(viewModel.educationVideo[viewModel.currentPosition].videoUrl) // ToDo: For normal video player initialization
                    }
                    Log.e("TAG", "VideoPlayerActivity: ===> MY_INFORMATION_ACTIVITY ==> $slugType")
                    viewModel.getVideoCount(
                        id = viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                }

                intent.getStringExtra("EDUCATION").toString() == "EDUCATION" -> {
                    val bundle = intent.extras
                    for (i in viewModel.educationVideo.indices) {
                        if (viewModel.educationVideo[i].type == "video") {
                            viewModel.currentPosition = i
                            break
                        }
                    }
                    viewModel.currentPosition = bundle?.getString("postion_value")!!.toInt()

                    Log.e("TAG", "Current Postion isss=> " + viewModel.currentPosition)


                    viewModel.setDataTest(viewModel.currentPosition)

                    if (viewModel.educationVideo[viewModel.currentPosition].videoType == "video" ||
                        viewModel.educationVideo[viewModel.currentPosition].videoType == "wistia"
                    ) {
                        viewModel.categoryID =
                            viewModel.educationVideo[viewModel.currentPosition].id!!
                        viewModel.videoID = viewModel.educationVideo[viewModel.currentPosition].id!!
                        viewModel.initializeVideoPlayer(viewModel.educationVideo[viewModel.currentPosition].videoUrl)
                        // ToDo: For normal video player initialization
                    }
                    Log.e("TAG", "VideoPlayerActivity: ===> EDUCATION_SUB_ADAPTER ==> $slugType")
                    viewModel.getVideoCount(
                        id = viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                }

                intent.getStringExtra("EDUCATION").toString() == "EDUCATION" -> {
                    val bundle = intent.extras
//                    categoryVideos = bundle!!.getSerializable("VideoList") as List<CategoryVideo>

                    for (i in viewModel.educationVideo.indices) {
                        if (viewModel.educationVideo[i].type == "video") {
                            viewModel.currentPosition = i
                            break
                        }
                    }
                    viewModel.currentPosition = bundle?.getString("postion_value")!!.toInt()

                    /*  rcv_edu_video_list.adapter =
                          EducationDetailVideoAdapter(categoryVideos, this, pos!!.toInt())*/

                    viewModel.setDataTest(viewModel.currentPosition)

                    if (viewModel.educationVideo[viewModel.currentPosition].videoType == "video" ||
                        viewModel.educationVideo[viewModel.currentPosition].videoType == "wistia"
                    ) {
                        viewModel.categoryID =
                            viewModel.educationVideo[viewModel.currentPosition].id!!
                        viewModel.videoID = viewModel.educationVideo[viewModel.currentPosition].id!!
                        viewModel.initializeVideoPlayer(viewModel.educationVideo[viewModel.currentPosition].videoUrl) // ToDo: For normal video player initialization
                    }
                    Log.e("TAG", "VideoPlayerActivity: ===> MANAGE_ASTHMA_ACTIVITY ==> $slugType")
                    viewModel.getVideoCount(
                        id = viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                }

                intent.getStringExtra("EDUCATION").toString() == "EDUCATION" -> {
                    val bundle = intent.extras
//                    categoryVideos = bundle!!.getSerializable("VideoList") as List<CategoryVideo>
                    viewModel.currentPosition = bundle?.getInt("postion_value")!!.toInt()


                    viewModel.setDataTest(viewModel.currentPosition)

                    if (viewModel.educationVideo[viewModel.currentPosition].videoType == "video" || viewModel.educationVideo[viewModel.currentPosition].videoType == "wistia") {
                        viewModel.categoryID =
                            viewModel.educationVideo[viewModel.currentPosition].id!!
                        viewModel.videoID = viewModel.educationVideo[viewModel.currentPosition].id!!
                        viewModel.initializeVideoPlayer(viewModel.educationVideo[viewModel.currentPosition].videoUrl)
                    }
                    Log.e(
                        "TAG",
                        "VideoPlayerActivity: ===> MY_MEDICATION_DETAIL_ACTIVITY ==> $slugType"
                    )
                    viewModel.getVideoCount(
                        id = viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                }


            }
            binding.ivVideoPlayerSeen.setOnClickListener {
                viewModel.storeVideoCount(
                    id = viewModel.videoID.toString(),
                    context = this
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setClickListener() {
        binding.include5.toolbarBack.setOnClickListener {
            finish()
        }
        binding.ivVideoPlayerSeen.setOnClickListener {
            viewModel.storeVideoCount(
                id = viewModel.videoID.toString(),
                context = this
            )
        }
    }


    override fun onPause() {
        super.onPause()
        binding.videoView.stopPlayer()
        viewModel.exoPlayerUtils.releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        viewModel.exoPlayerUtils.releasePlayer()
    }


    private fun observer() {
        viewModel.categoryVideosResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val response = it.value as EducationDetails

                    binding.layoutVideo.visibility = View.VISIBLE
                    viewModel.categoryVideos =
                        response.data[0].categoryVideo as ArrayList<CategoryVideo>

                    viewModel.currentPosition =
                        viewModel.categoryVideos.indexOf(viewModel.categoryVideos.singleOrNull {
                            it.id == viewModel.videoID
                        })
                    viewModel.getVideoCount(
                        id=viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this
                    )
                    viewModel.initializePlayers()


                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }

                else -> {
                }
            }
        }
        viewModel.getTaskEducationVideoResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val response = it.value as EducationVideoTaskModel
                    Log.e("TAG", "EDUCATION CATEGORY VIDEO RESPONSE === $response")
                    binding.layoutVideo.visibility = View.VISIBLE

                    if (response.data?.isNotEmpty() == true) {
                        viewModel.educationVideo = response.data

                        viewModel.videoID = response.data[0].id ?: 0
                    }


                    viewModel.currentPosition = 0
                    Log.e(
                        "TAG",
                        "cccccccc:${viewModel.currentPosition} _${viewModel.categoryID.toString()} _ $slugType",
                    )
                    viewModel.getVideoCount(
                        id=viewModel.categoryID.toString(),
                        slug = slugType,
                        context = this)
                    viewModel.initializePlayers()

                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }

                else -> {
                }
            }

        }
        viewModel.getVideoCountResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val response = it.value as GetVideoCountModel
                    viewModel.setVideoCountResponse(response, binding)
                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }

                else -> {
                }
            }
        }
        viewModel.storeVideoCountResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    onBackPress()
                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }

                else -> {
                }
            }
        }
    }


}