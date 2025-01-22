package com.icst.commonmodule.design.activity.choosesurgery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityChooseSurgeryBinding
import com.icst.commonmodule.design.activity.choosesurgery.adapter.SurgerySelectionSearchAdapter
import com.icst.commonmodule.model.RegisterSurgeryList
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant


class ChooseSurgeryActivity : ActivityBase(), View.OnClickListener {

    private var surgeryId: String? = ""
    private var surgeryNameList: ArrayList<String>? = null
    private var surgeryIdList: ArrayList<String>? = null
    private var allSurgeryData: ArrayList<RegisterSurgeryList.Data>? = null
    private var chooseSelectionSurgery: SurgerySelectionSearchAdapter? = null

    private val binding: ActivityChooseSurgeryBinding by binding(R.layout.activity_choose_surgery)
    val viewModel = ChooseSurgeryViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel.activityBase.set(this)

        surgeryNameList = ArrayList()
        surgeryIdList = ArrayList()
        allSurgeryData = ArrayList()
        viewModel.getRegisterSurgeryList()
        initView()
        observer()
    }

    private fun initView() {
        binding.btnGpSurgeryAdd.setOnClickListener(this)
        binding.include2.toolbarBack.setOnClickListener(this)

        binding.include2.toolbarProfile.isVisible = false
        binding.include2.toolbarBack.isVisible = true
        binding.include2.toolbarLogo.isVisible = false
        binding.include2.toolbarTitle.isVisible = true

        binding.include2.toolbarTitle.text = buildString { append("Add Surgery") }

        binding.autoCompleteTextViewAgainSelectLabName.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, arg2, _ ->
                surgeryId = ""
                val adapter =
                    binding.autoCompleteTextViewAgainSelectLabName.adapter as SurgerySelectionSearchAdapter
                val labId = (adapter.getItem(arg2) as RegisterSurgeryList.Data).id
                surgeryId = labId.toString()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    binding.autoCompleteTextViewAgainSelectLabName.windowToken,
                    0
                )
            }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.include2.toolbarBack -> {
                finish()
            }
            binding.btnGpSurgeryAdd -> {
                if (surgeryId == "") {
                    showSnackBar(
                        binding.parentLayout,
                        getString(R.string.txt_error_surgery),
                        ACTIONSNACKBAR.DISMISS
                    )
                } else {
                    viewModel.surgeryChangeApiCall(surgeryId!!)

                }
            }
        }
    }

    private fun observer() {
        viewModel.getRegisterSurgeryListResponse.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        val surgeryData = it.value as RegisterSurgeryList
                        if (surgeryData.data.isNotEmpty()) {
                            surgeryNameList!!.clear()
                            (surgeryData.data.indices).mapTo(surgeryNameList!!) { surgeryData.data[it].name }

                            surgeryIdList!!.clear()
                            (surgeryData.data.indices).mapTo(surgeryIdList!!) { surgeryData.data[it].id.toString() }
                            allSurgeryData!!.addAll(surgeryData.data)

                            chooseSelectionSurgery = SurgerySelectionSearchAdapter(this@ChooseSurgeryActivity,
                                android.R.layout.simple_dropdown_item_1line,
                                allSurgeryData
                            )
                            binding.autoCompleteTextViewAgainSelectLabName.threshold = 1
                            binding.autoCompleteTextViewAgainSelectLabName.setAdapter(
                                chooseSelectionSurgery
                            )//setting the adapter data into the AutoCompleteTextView\
                        } else {
                            Toast.makeText(this,surgeryData.message , Toast.LENGTH_SHORT).show()

                        }

                    }

                    is Resource.Failure -> {
                        Constant.handlerApiError(it, this, binding.root)
                    }

                    else -> {
                    }
                }
            }
        }
        viewModel.surgeryChangeResponse.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
//                        objSharedPref.putString(getString(R.string.key_surgery_id), surgeryId!!)
                        Log.e("TAG", "observerChooseSurActivity:$surgeryId ", )
                        val intent = Intent()
                        intent.putExtra("surgeryId",surgeryId!!)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }

                    is Resource.Failure -> {
                        Constant.handlerApiError(it, this, binding.root)
                    }

                    else -> {
                    }
                }
            }
        }
    }
}