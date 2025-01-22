package com.icst.commonmodule.design.activity.edaucationresource

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.repository.education.EducationResourcesRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch


class EducationResourceViewModel : ViewModel() {

    private val educationResourcesRepository = EducationResourcesRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()

    private val _educationResourceResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()

    val educationResourceResponse: LiveData<Resource<Any?>>
        get() {
            return _educationResourceResponse
        }

    fun getEducationResourceApiCall(
        id: String, context: Context
    ) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _educationResourceResponse.value =
                educationResourcesRepository.getEducationResourceApiCall(
                    id=id,
                    context = context
                )
//            activityBase.get()!!.dismissProgress()
        }
    }
}