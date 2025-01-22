package com.icst.commonmodule.design.activity.addContact

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import com.icst.commonmodule.R
import com.icst.commonmodule.app.App
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityNewContactBinding
import com.icst.commonmodule.model.Contact
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.Validator


class AddContactActivity: ActivityBase(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private val binding: ActivityNewContactBinding by binding(R.layout.activity_new_contact)
    private var valueIs: String = ""

     var viewModel = AddContactViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@AddContactActivity

        viewModel.activityBase.set(this)

        setClickListener()
        initToolBar()
        viewModel.setupSpinner(binding)
        binding.spContactWith.onItemSelectedListener = this
        observer()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun initToolBar(){
        binding.newAppInclude.toolbarProfile.isVisible = false
        binding.newAppInclude.toolbarBack.isVisible = true
        binding.newAppInclude.toolbarLogo.isVisible = false
        binding.newAppInclude.toolbarTitle.isVisible = true

        binding.newAppInclude.toolbarTitle.text = buildString { append(getString(R.string.new_contact_title)) }
    }

    private fun setClickListener() {
        binding.newContactBtnSave.setOnClickListener(this)
        binding.newAppInclude.toolbarBack.setOnClickListener(this)
        binding.tvContactWith.setOnClickListener(this)
    }



    override fun onClick(view: View?) {
        when (view) {
            binding.newContactBtnSave -> {
                if (!isValidForm())
                    return

                viewModel.addContactApiCall(
                    Contact(
                        binding.newContactFname.text.toString(),
                        binding.newContactLname.text.toString(),
                        binding.newContactWorkPlace.text.toString(),
                        valueIs,
                        binding.newContactPhone.text.toString(),
                        binding.newContactEmail.text.toString()
                    )
                )


            }

            binding.newAppInclude.toolbarBack -> {
                finish()
            }

            binding.tvContactWith -> {
                binding.spContactWith.performClick()
            }
        }

    }

    private fun isValidForm(): Boolean {


        if (!Validator().validateTextNotNull(binding.newContactLname.text.toString())) {
            showSnackBar(
                binding.newContactParent,
                getString(R.string.error_lname),
                ACTIONSNACKBAR.DISMISS
            )
            return false
        }

        if (!Validator().validateTextNotNull(binding.newContactWorkPlace.text.toString())) {
            showSnackBar(
                binding.newContactParent,
                getString(R.string.error_work_place),
                ACTIONSNACKBAR.DISMISS
            )
            return false
        }

        return true
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        if (viewModel.spWithArray[position] == resources.getString(R.string.txt_doctor)) {
            valueIs = "Doctor (GP)"
            Log.e("TAG", "Value ISSS-->  $valueIs")
        } else if (viewModel.spWithArray[position] == resources.getString(R.string.txt_nurse)) {
            valueIs = "Nurse"
            Log.e("TAG", "Value ISSS-->  $valueIs")
        } else if (viewModel.spWithArray[position] == resources.getString(R.string.txt_hospital)) {
            valueIs = "Hospital consultant"
            Log.e("TAG", "Value ISSS-->  $valueIs")
        } else {
            valueIs = "Doctor (GP)"
            Log.e("TAG", "Value ISSS-->  $valueIs")
        }
        viewModel.isInitialValueSelected = true
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun observer(){
        viewModel.addContactListResponse.observe(this){
            when (it) {
                is Resource.Success -> {
                    App.setAppointmentAdd(true)
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

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!App.getAppointmentAdd())
                    App.setAppointmentAdd(false)

            }
        }
}