package com.icst.commonmodule.design.activity.choosesurgery

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.repository.surgery.ChooseSurgeryRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch


class ChooseSurgeryViewModel: ViewModel() {

    private val chooseSurgeryRepository = ChooseSurgeryRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()

    private val _getRegisterSurgeryListResponse:  MutableLiveData<Resource<Any?>> = MutableLiveData()

    val getRegisterSurgeryListResponse: LiveData<Resource<Any?>>
        get() {
            return _getRegisterSurgeryListResponse
        }

    fun getRegisterSurgeryList() {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _getRegisterSurgeryListResponse.value =
                chooseSurgeryRepository.getRegisterSurgeryList(activityBase.get()!!)
            activityBase.get()!!.dismissProgress()
        }
    }

    // --------------------------------------

    private val _surgeryChangeResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()

    val surgeryChangeResponse: LiveData<Resource<Any?>>
        get() {
            return _surgeryChangeResponse
        }

    fun surgeryChangeApiCall(id:String) {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _surgeryChangeResponse.value =
                chooseSurgeryRepository.surgeryChangeApiCall(
                    id= id,
                    context = activityBase.get()!!
                )
            activityBase.get()!!.dismissProgress()
        }
    }

}