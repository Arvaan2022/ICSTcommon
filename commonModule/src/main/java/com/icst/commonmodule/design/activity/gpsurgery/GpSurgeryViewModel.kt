package com.icst.commonmodule.design.activity.gpsurgery

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.repository.surgery.GpSurgeryRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch

class GpSurgeryViewModel : ViewModel() {

    private val gpSurgeryRepository = GpSurgeryRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()


    private val _getSurgeryHistoryResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()

    val getSurgeryHistoryResponse: LiveData<Resource<Any?>>
        get() {
            return _getSurgeryHistoryResponse
        }

    fun getSurgeryHistoryApiCall() {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _getSurgeryHistoryResponse.value =
                gpSurgeryRepository.getSurgeryHistoryApiCall(activityBase.get()!!)
            activityBase.get()!!.dismissProgress()
        }
    }
}