package com.icst.commonmodule.design.activity.gpsurgery

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityGpSurgeryBinding
import com.icst.commonmodule.design.activity.choosesurgery.ChooseSurgeryActivity
import com.icst.commonmodule.design.activity.gpsurgery.adapter.GpSurgeryAdapter
import com.icst.commonmodule.model.GpSurgeryModel
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant


class GpSurgeryActivity : ActivityBase(), View.OnClickListener {

    private var gpSurgeryAdapter: GpSurgeryAdapter? = null

    private val binding: ActivityGpSurgeryBinding by binding(R.layout.activity_gp_surgery)
    val viewModel = GpSurgeryViewModel()

    var surgeryId =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel.activityBase.set(this)

        viewModel.getSurgeryHistoryApiCall()
        initView()
        btnClick()
        observer()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent()
                intent.putExtra("surgeryId", surgeryId)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

    private fun initView() {
        binding.include2.toolbarBack.setOnClickListener(this)
        binding.include2.toobarView.visibility = View.GONE

        binding.include2.toolbarProfile.isVisible = false
        binding.include2.toolbarBack.isVisible = true
        binding.include2.toolbarLogo.isVisible = false
        binding.include2.toolbarTitle.isVisible = true

        binding.include2.toolbarTitle.text = buildString { append("Add Surgery") }

    }


    private fun btnClick() {
        binding.btnGpSurgeryAdd.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.include2.toolbarBack -> {
                finish()
            }

            binding.btnGpSurgeryAdd -> {
                startForResult.launch(Intent(this, ChooseSurgeryActivity::class.java))
            }
        }
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val id=result.data?.getStringExtra("surgeryId")
            if (id != null) {
                surgeryId = id
            }
            viewModel.getSurgeryHistoryApiCall()
        }
    }


    private fun setAdapter(data: ArrayList<GpSurgeryModel.Data>) {
        binding.rvGpSurgery.setHasFixedSize(true)
        gpSurgeryAdapter = GpSurgeryAdapter(this@GpSurgeryActivity, data)
        binding.rvGpSurgery.adapter = gpSurgeryAdapter
    }

    private fun observer() {
        viewModel.getSurgeryHistoryResponse.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        val response = it.value as GpSurgeryModel
                        if (response.data.isNotEmpty()) {
                            binding.tvGpSurgeryNoData.visibility = View.GONE
                            binding.rvGpSurgery.visibility = View.VISIBLE
                            setAdapter(response.data as ArrayList<GpSurgeryModel.Data>)
                        } else {
                            binding.tvGpSurgeryNoData.visibility = View.VISIBLE
                            binding.rvGpSurgery.visibility = View.GONE
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
    }
}
