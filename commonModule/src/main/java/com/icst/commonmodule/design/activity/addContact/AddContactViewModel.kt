package com.icst.commonmodule.design.activity.addContact

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityNewContactBinding
import com.icst.commonmodule.model.Contact
import com.icst.commonmodule.repository.addContact.AddContactRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch


class AddContactViewModel : ViewModel() {
    private var addContactRepository = AddContactRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()


      val spWithArray= listOf(
       "Doctor (GP)",
       "Nurse",
       "Hospital consultant"
    ) as MutableList

     var isInitialValueSelected = false

    private val _addContactListResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val addContactListResponse: LiveData<Resource<Any?>>
        get() {
            return _addContactListResponse
        }



    fun addContactApiCall(contact: Contact) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _addContactListResponse.value = addContactRepository.addContactApiCall(contact,activityBase.get()!!)
//            activityBase.get()!!.dismissProgress()
        }
    }

     fun setupSpinner(binding: ActivityNewContactBinding) {

        val arrayAdapter = object :
            ArrayAdapter<String>(activityBase.get()!!, R.layout.row_dropdown, spWithArray) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val tvView = super.getView(position, convertView, parent) as AppCompatTextView
                tvView.typeface =
                    ResourcesCompat.getFont( activityBase.get()!!, R.font.opensans_regular)

                Log.e("TAG", "getView method triggered.... ${spWithArray[position]}")

                if (isInitialValueSelected)
                    binding.tvContactWith.text = spWithArray[position]
                return tvView
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val tvDropDown = super.getView(position, convertView, parent) as AppCompatTextView
                tvDropDown.typeface =
                    ResourcesCompat.getFont( activityBase.get()!!, R.font.opensans_regular)
                return tvDropDown
            }

            override fun getCount(): Int {
                return spWithArray.size
            }
        }
        arrayAdapter.setDropDownViewResource(R.layout.row_dropdown)
        binding.spContactWith.adapter = arrayAdapter

    }



}