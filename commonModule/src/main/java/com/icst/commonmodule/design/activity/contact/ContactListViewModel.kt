package com.icst.commonmodule.design.activity.contact

import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityContactBinding
import com.icst.commonmodule.model.ContactList
import com.icst.commonmodule.repository.contactList.ContactListRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch


class ContactListViewModel : ViewModel() {
    private var contactRepository = ContactListRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()
    var listContact: MutableList<ContactList.ContactData> = ArrayList()
    var selectedPosition = -1
    var currentPos: Int = 0

    private val _contactListResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val contactListResponse: LiveData<Resource<Any?>>
        get() {
            return _contactListResponse
        }

    private val _deleteContactResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val deleteContactResponse: LiveData<Resource<Any?>>
        get() {
            return _deleteContactResponse
        }

    fun contactListApiCall() {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _contactListResponse.value = contactRepository.contactListApiCall(activityBase.get()!!)
            activityBase.get()!!.dismissProgress()
        }
    }

    private fun deleteContactApiCall(id: Int) {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _deleteContactResponse.value = contactRepository.deleteContactApiCall(id,activityBase.get()!!)
            activityBase.get()!!.dismissProgress()
        }
    }

    private fun alertDialog(pos: Int) {
        val customBuilder = AlertDialog.Builder(
            ContextThemeWrapper(
                activityBase.get()!!,
                R.style.AlertDialogDanger
            )
        )
        customBuilder.setMessage("Are you sure you want to delete this contact?")
        customBuilder.setPositiveButton("Ok") { _, _ ->
            deleteContactApiCall(listContact[pos].id)
        }
        customBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }.show()
    }

    val rowViewClickInterface = fun(view: Int, pos: Int) {
        when (view) {
/*            0 -> {
                currentPos = pos
                if (ContextCompat.checkSelfPermission(
                        activityBase.get()!!, Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    if (listContact[currentPos].phone.isNotEmpty()) {
                        activityBase.get()!!.startActivity(
                            Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:".plus(listContact[currentPos].phone)))
                        )
                    } else {
                        activityBase.get()!!
                            .showMessage(activityBase.get()!!.resources.getString(R.string.txt_phne_alert))
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        activityBase.get()!!,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        1001
                    )
                }
            }

            1 -> {
                if (listContact[pos].email.isNotEmpty()) {
                    activityBase.get()!!.sendEmail()
                } else {
                    activityBase.get()!!
                        .showMessage(activityBase.get()!!.resources.getString(R.string.txt_alert_email))
                }
            }*/

            2 -> {
                Log.e("TAG", "pppppppppppp: $pos=====${listContact}")
                selectedPosition = pos
                alertDialog(pos)
            }
        }
    }

     fun removeContact(position: Int, binding: ActivityContactBinding) {
        listContact.removeAt(position)
        if (listContact.size == 0) {
            showNoData(true, binding)
        }
        binding.contactRcvList.adapter?.notifyItemRemoved(position)
    }
     fun showNoData(available_data: Boolean, binding: ActivityContactBinding) {
        if (available_data) {
            binding.tvNoData.text = activityBase.get()!!.resources.getString(R.string.txt_no_data)
            binding.layoutNoData.visibility = View.VISIBLE
        } else {
            binding.contactRcvList.visibility = View.VISIBLE
            binding.layoutNoData.visibility = View.GONE
        }
    }


}