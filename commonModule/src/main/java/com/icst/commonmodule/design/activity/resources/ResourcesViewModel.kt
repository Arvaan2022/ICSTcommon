package com.icst.commonmodule.design.activity.resources

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


class ResourcesViewModel : ViewModel() {

    private val educationResourcesRepository = EducationResourcesRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()


    private val _resourceResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()

    val resourceResponse:LiveData<Resource<Any?>>
        get() {
            return _resourceResponse
        }

    fun getEducationResourceCategoryApiCall(
        context: Context
    ) {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _resourceResponse.value = educationResourcesRepository.getEducationResourceCategoryApiCall(
                context = context)
            activityBase.get()!!.dismissProgress()
        }
    }
}