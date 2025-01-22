package com.icst.commonmodule.design.activity.contact

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.icst.commonmodule.R
import com.icst.commonmodule.app.App
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityContactBinding
import com.icst.commonmodule.design.activity.addContact.AddContactActivity
import com.icst.commonmodule.design.activity.contact.adapter.ContactAdapter
import com.icst.commonmodule.model.ContactList
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant


class ContactListActivity : ActivityBase(),View.OnClickListener {

    private val binding: ActivityContactBinding by binding(R.layout.activity_contact)
    private val viewModel = ContactListViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel.activityBase.set(this)

        viewModel.contactListApiCall()
        initToolbars()
        initListener()
        observer()

    }

    private fun initToolbars() {

        binding.includeContact.toolbarProfile.isVisible = false
        binding.includeContact.toolbarBack.isVisible = true
        binding.includeContact.toolbarLogo.isVisible = false
        binding.includeContact.toolbarTitle.isVisible = true

        binding.includeContact.toolbarTitle.text = buildString { append("Contacts") }
    }

    private fun initListener() {
        binding.contactsBtnAdd.setOnClickListener(this)
        binding.includeContact.toolbarBack.setOnClickListener(this)
    }

    private fun goToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }



    override fun onResume() {
        super.onResume()
        if (App.getAppointmentAdd()) {
            viewModel.contactListApiCall()
        }

    }

    override fun onClick(view: View?) {
        when (view) {
            binding.contactsBtnAdd -> {
                App.setAppointmentAdd(false)
                startActivity(Intent(this,AddContactActivity::class.java))
            }

            binding.includeContact.toolbarBack -> {
                finish()
            }
        }

    }

    private fun observer(){
        viewModel.contactListResponse.observe(this){
            when (it) {
                is Resource.Success -> {
                   val response = it.value as ContactList
                    if (response.data.isEmpty()){
                        viewModel.showNoData(true,binding)
                    }else{
                        viewModel.showNoData(false,binding)
                        viewModel.listContact = response.data
                        binding.contactRcvList.adapter =
                            ContactAdapter(this, response.data,viewModel.rowViewClickInterface)
                    }
                }
                is Resource.Failure -> {
                    viewModel.showNoData(true,binding)
                    Constant.handlerApiError(it, this, binding.root)
                }
                else -> {
                }
            }
        }
        viewModel.deleteContactResponse.observe(this){
            when (it) {
                is Resource.Success -> {
                    viewModel.removeContact(viewModel.selectedPosition,binding)
                }
                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }
                else -> {
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {

            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                var i = 0
                val len = permissions.size
                var showRationale = false
                while (i < len) {
                    val permission = permissions[i]
                    showRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                        this@ContactListActivity,
                        permission
                    )
                    i++
                }
                if (!showRationale) {
                    Log.e(javaClass.simpleName, "Don't ask again checked....")

                    AlertDialog.Builder(this@ContactListActivity)
                        .setMessage("Without this permission you won\\'t able to make a call. Are you sure you want to allow this permission?")
                        .setPositiveButton("Go to settings") { _, _ ->
                            run {
                                goToAppSettings()
                            }
                        }
                        .setNegativeButton("Deny") { _,_ ->
                            run {
                            }
                        }
                        .setCancelable(false)
                        .show()
                } else {
                    AlertDialog.Builder(this@ContactListActivity)
                        .setMessage("This app needs this permission to make a call. Are you sure you want to allow this permission?")
                        .setPositiveButton("Allow") { _, _ ->
                            run {
                                Log.e(javaClass.simpleName, "Permission Allowed...")
                                ActivityCompat.requestPermissions(
                                    this@ContactListActivity,
                                    arrayOf(Manifest.permission.CALL_PHONE),
                                    1001
                                )
                            }
                        }
                        .setNegativeButton("Deny") { _, _ ->
                            run {
                            }
                        }
                        .setCancelable(false)
                        .show()
                }
            } else {
                if (viewModel.listContact[viewModel.currentPos].phone.isNotEmpty()) {
                    startActivity(
                        Intent(Intent.ACTION_CALL)
                            .setData(Uri.parse("tel:".plus(viewModel.listContact[viewModel.currentPos].phone)))
                    )
                } else {
                    Toast.makeText(this, "Phone number not available.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}