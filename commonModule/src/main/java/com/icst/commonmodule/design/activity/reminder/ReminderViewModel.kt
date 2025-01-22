package com.icst.commonmodule.design.activity.reminder

import android.app.AlertDialog
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.model.ListScheduleModel
import com.icst.commonmodule.repository.reminder.ReminderRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch


class ReminderViewModel() : ViewModel() {
    private var reminderRepository = ReminderRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()

      var appoinmentList= ArrayList<ListScheduleModel.DataItem>()
    private val _listScheduleResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val listScheduleResponse: LiveData<Resource<Any?>>
        get() {
            return _listScheduleResponse
        }

    private val _deleteScheduleResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val deleteScheduleResponse: LiveData<Resource<Any?>>
        get() {
            return _deleteScheduleResponse
        }


    fun listScheduleApiCall(month: Int,year:Int) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _listScheduleResponse.value = reminderRepository.listScheduleApiCall(month = month,
                year = year,activityBase.get()!!)
//            activityBase.get()!!.dismissProgress()
        }
    }

    private fun deleteScheduleApiCall(id: Int) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _deleteScheduleResponse.value = reminderRepository.deleteScheduleApiCall(
                id = id,activityBase.get()!!)
//            activityBase.get()!!.dismissProgress()
        }
    }

     fun alertDialog(pos: Int) {

        val customBuilder =
            AlertDialog.Builder(activityBase.get()!!, R.style.AlertDialogDanger)
        customBuilder.setMessage("Are you sure you want to delete this reminder?")
        customBuilder.setPositiveButton("OK") { _, _ ->
            deleteScheduleApiCall(appoinmentList[pos].id)
        }
        customBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }.show()
    }


}