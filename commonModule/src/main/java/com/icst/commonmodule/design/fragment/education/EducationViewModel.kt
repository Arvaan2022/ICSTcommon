package com.icst.commonmodule.design.fragment.education

import android.content.Context
import android.text.Html
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.databinding.FragmentEducationCommonBinding
import com.icst.commonmodule.model.EducationContent
import com.icst.commonmodule.model.NormalValueDataModel
import com.icst.commonmodule.repository.education.EducationRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch

class EducationViewModel:ViewModel() {

    private val educationRepository = EducationRepository.getInstance()


    private val _educationDataResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val educationDataResponse: LiveData<Resource<Any?>>
        get() {
            return _educationDataResponse
        }

    fun getEducationDataApiCall(context: Context) {
        viewModelScope.launch {
            _educationDataResponse.value = educationRepository.getEducationData(
               context=context
            )
        }
    }

    fun setEducationData(binding: FragmentEducationCommonBinding, response: EducationContent) {

        binding.includeEducation.educationResponse = response

        binding.includeEducation.tvEducationDescArticle.text =
            Html.fromHtml(
                response.data.educationfuture.content,
                Html.FROM_HTML_MODE_LEGACY
            )

        when (response.data.educationData.size) {
            0 -> {
                binding.includeEducation.includeVideoContainer.llVideo1.visibility =
                    View.GONE
                binding.includeEducation.includeVideoContainer.llVideo2.visibility =
                    View.GONE
                binding.includeEducation.includeVideoContainer.llVideo3.visibility =
                    View.GONE
                binding.includeEducation.includeVideoContainer.llVideo4.visibility =
                    View.GONE
            }
            1 -> {
                binding.includeEducation.includeVideoContainer.llVideo2.visibility =
                    View.GONE
                binding.includeEducation.includeVideoContainer.llVideo3.visibility =
                    View.GONE
                binding.includeEducation.includeVideoContainer.llVideo4.visibility =
                    View.GONE
            }

            2 -> {
                binding.includeEducation.includeVideoContainer.llVideo3.visibility =
                    View.GONE
                binding.includeEducation.includeVideoContainer.llVideo4.visibility =
                    View.GONE
            }
            3 -> {
                binding.includeEducation.includeVideoContainer.llVideo4.visibility =
                    View.GONE
            }
            else -> {

            }
        }

        when(response.data.educationData.size-1){
            0->{
                binding.includeEducation.includeVideoContainer.tvVideoTimeOne.text =setMinuteText(
                    response.data.educationData[0].hour.toString(),
                    response.data.educationData[0].minute.toString(),
                    response.data.educationData[0].second.toString()
                )

            }
            1->{
                binding.includeEducation.includeVideoContainer.tvVideoTimeOne.text =setMinuteText(
                    response.data.educationData[0].hour.toString(),
                    response.data.educationData[0].minute.toString(),
                    response.data.educationData[0].second.toString()
                )

                binding.includeEducation.includeVideoContainer.tvVideoTimeTwo.text =setMinuteText(
                    response.data.educationData[1].hour.toString(),
                    response.data.educationData[1].minute.toString(),
                    response.data.educationData[1].second.toString()
                )

            }
            2->{
                binding.includeEducation.includeVideoContainer.tvVideoTimeOne.text =setMinuteText(
                    response.data.educationData[0].hour.toString(),
                    response.data.educationData[0].minute.toString(),
                    response.data.educationData[0].second.toString()
                )

                binding.includeEducation.includeVideoContainer.tvVideoTimeTwo.text =setMinuteText(
                    response.data.educationData[1].hour.toString(),
                    response.data.educationData[1].minute.toString(),
                    response.data.educationData[1].second.toString()
                )
                binding.includeEducation.includeVideoContainer.tvVideoTimeThree.text=setMinuteText(
                    response.data.educationData[2].hour.toString(),
                    response.data.educationData[2].minute.toString(),
                    response.data.educationData[2].second.toString()
                )

            }
            3->{
                binding.includeEducation.includeVideoContainer.tvVideoTimeOne.text =setMinuteText(
                    response.data.educationData[0].hour.toString(),
                    response.data.educationData[0].minute.toString(),
                    response.data.educationData[0].second.toString()
                )

                binding.includeEducation.includeVideoContainer.tvVideoTimeTwo.text =setMinuteText(
                    response.data.educationData[1].hour.toString(),
                    response.data.educationData[1].minute.toString(),
                    response.data.educationData[1].second.toString()
                )
                binding.includeEducation.includeVideoContainer.tvVideoTimeThree.text=setMinuteText(
                    response.data.educationData[2].hour.toString(),
                    response.data.educationData[2].minute.toString(),
                    response.data.educationData[2].second.toString()
                )
                binding.includeEducation.includeVideoContainer.tvVideoTimeFour.text=setMinuteText(
                    response.data.educationData[3].hour.toString(),
                    response.data.educationData[3].minute.toString(),
                    response.data.educationData[3].second.toString()
                )
            }
        }

        val videoData = arrayListOf(
            NormalValueDataModel.Data.VideoData(
            hour = response.data.educationfuture.hour.toString(),
            min = response.data.educationfuture.min.toString(),
            sec = response.data.educationfuture.sec.toString(),
            thmubUrl = response.data.educationfuture.imageUrl,
            title = response.data.educationfuture.title,
            videoUrl = response.data.educationfuture.videoUrl,
            des = response.data.educationfuture.content.toString()
        ))
        binding.includeEducation.videoData =  videoData

        binding.includeEducation.includeVideoContainer.response = response


    }

    private fun setMinuteText(hour: String, min: String, sec: String): String {

        val hours = if (hour.isNotEmpty()) {
            if (hour.length == 1) {
                "0$hour"
            } else {
                hour
            }
        } else {
            "00"
        }

        val minutes = if (min.isNotEmpty()) {
            if (min.length == 1) {
                "0$min"
            } else {
                min
            }
        } else {
            "00"
        }

        val seconds = if (sec.isNotEmpty()) {
            if (sec.length == 1) {
                "0$sec"
            } else {
                sec
            }
        } else {
            "00"
        }
        return if (hours.toInt() > 0) {
            "$hours:$minutes:$seconds minutes"
        } else {
            "$minutes:$seconds minutes"
        }
    }
}