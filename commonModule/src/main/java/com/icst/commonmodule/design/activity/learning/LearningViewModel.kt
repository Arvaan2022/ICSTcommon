package com.icst.commonmodule.design.activity.learning

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityLearningBinding
import com.icst.commonmodule.model.CategoryVideo
import com.icst.commonmodule.model.EducationContent
import com.icst.commonmodule.model.GetVideoCountModel
import com.icst.commonmodule.repository.videoPlayer.VideoPlayerRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch

class LearningViewModel : ViewModel() {
    private val videoPlayerRepository = VideoPlayerRepository.getInstance()


    lateinit var educationData: EducationContent.Data.EducationData
    lateinit var categoryVideo: CategoryVideo
     val activityBase: ObservableField<ActivityBase> = ObservableField()
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


    fun getVideoCount(
        id: String,
        slug: String,
        context: Context
    ) {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _getVideoCountResponse.value = videoPlayerRepository.getVideoCount(
                id = id,
                slug = slug,
                context = context
            )
            activityBase.get()!!.dismissProgress()
        }
    }

    fun storeVideoCount(
        id: String,
        context: Context
    ) {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _storeVideoCountResponse.value = videoPlayerRepository.storeVideoCount(
                videoId = id,
                context = context
            )
            activityBase.get()!!.dismissProgress()
        }
    }

    fun setVideoCountResponse(binding: ActivityLearningBinding, response: GetVideoCountModel) {
        if (::categoryVideo.isInitialized) {
            if (response.data.single { it.videoId == categoryVideo.id.toString() }.isWatch == "1") {
                binding.tvLearningSeen.visibility = View.VISIBLE
                binding.ivLearningSeen.visibility = View.VISIBLE
                binding.tvLearningSeen.text = buildString { append("Finished Reading") }
                binding.ivLearningSeen.setColorFilter(0)
                binding.ivLearningSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        activityBase.get()!!,
                        R.drawable.ic_green_tick
                    )
                )
                binding.ivLearningSeen.isEnabled = false
            } else {
                binding.tvLearningSeen.visibility = View.VISIBLE
                binding.ivLearningSeen.visibility = View.VISIBLE
                binding.tvLearningSeen.text = buildString { append("Finished reading?") }

                binding.ivLearningSeen.setColorFilter(
                    ContextCompat.getColor(activityBase.get()!!, R.color.Grey_400),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.ivLearningSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        activityBase.get()!!,
                        R.drawable.ic_circle_tick
                    )
                )
                binding.ivLearningSeen.isEnabled = true
            }

        } else {
            if (response.data.single { it.videoId == educationData.id.toString() }.isWatch == "1") {
                binding.tvLearningSeen.visibility = View.VISIBLE
                binding.ivLearningSeen.visibility = View.VISIBLE
                binding.tvLearningSeen.text = buildString { append("Finished Reading") }
                binding.ivLearningSeen.setColorFilter(0)
                binding.ivLearningSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        activityBase.get()!!,
                        R.drawable.ic_green_tick
                    )
                )
                binding.ivLearningSeen.isEnabled = false
            } else {
                binding.tvLearningSeen.visibility = View.VISIBLE
                binding.ivLearningSeen.visibility = View.VISIBLE
                binding.tvLearningSeen.text = buildString { append("Finished reading?") }
                binding.ivLearningSeen.setColorFilter(
                    ContextCompat.getColor(activityBase.get()!!, R.color.Grey_400),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.ivLearningSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        activityBase.get()!!,
                        R.drawable.ic_circle_tick
                    )
                )
                binding.ivLearningSeen.isEnabled = true
            }

        }

    }


}