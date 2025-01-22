package com.icst.commonmodule.design.activity.edaucationresource

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityEducationResourcesBinding
import com.icst.commonmodule.design.activity.edaucationresource.adapter.EducationResourceAdapter
import com.icst.commonmodule.model.EducationResourceModel
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.Constant.EDU_RESOURCE_ID
import com.icst.commonmodule.utils.Constant.EDU_RESOURCE_SUBTITLE
import com.icst.commonmodule.utils.Constant.EDU_RESOURCE_TITLE

class EducationResourcesActivity : ActivityBase() {

    val binding: ActivityEducationResourcesBinding by binding(R.layout.activity_education_resources)
    private val viewModel = EducationResourceViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel.activityBase.set(this)

        initViews()
        onClickListeners()
        observer()
    }

    private fun initViews() {
        binding.include.toolbarProfile.isVisible = false
        binding.include.toolbarBack.isVisible = true
        binding.include.toolbarLogo.isVisible = false
        binding.include.toolbarTitle.isVisible = true
        binding.include.toolbarTitle.text = buildString { append("Resources") }

        intent?.apply {
            if (hasExtra(EDU_RESOURCE_TITLE)) {
                val titleText = getStringExtra(EDU_RESOURCE_TITLE)
                binding.tvEducationResourcesTitle.text = titleText
            }
            if (hasExtra(EDU_RESOURCE_SUBTITLE)) {
                val descText = getStringExtra(EDU_RESOURCE_SUBTITLE)
                binding.tvEducationResourcesDesc.text = descText
            }
            if (hasExtra(EDU_RESOURCE_ID)) {
                val resourceId = getStringExtra(EDU_RESOURCE_ID).toString()
                Log.e(EducationResourcesActivity::class.java.simpleName, "initViews: $resourceId")
                requestApiEducationResources(resourceId)
            }
        }
    }

    private fun requestApiEducationResources(resourceId: String) {
        viewModel.getEducationResourceApiCall(
            id = resourceId, context = this
        )
    }

    private fun onClickListeners() {
        binding.include.toolbarBack.setOnClickListener { onBackPress() }
    }

    private fun observer() {
        viewModel.educationResourceResponse.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        val response = it.value as EducationResourceModel
                        if (response.data.isNotEmpty()) {
                            binding.rlEduResources.apply {
                                setHasFixedSize(true)
                                layoutManager = LinearLayoutManager(this@EducationResourcesActivity)
                                adapter = EducationResourceAdapter(
                                    this@EducationResourcesActivity,
                                    response.data
                                )
                                visibility = View.VISIBLE
                            }


                            binding.tvEduResourceNoData.visibility = View.GONE

                        } else {
                            binding.tvEduResourceNoData.visibility = View.VISIBLE
                            binding.rlEduResources.visibility = View.GONE
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