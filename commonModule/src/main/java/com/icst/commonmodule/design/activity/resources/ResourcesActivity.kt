package com.icst.commonmodule.design.activity.resources

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityResourcesBinding
import com.icst.commonmodule.design.activity.edaucationresource.EducationResourcesActivity
import com.icst.commonmodule.design.activity.resources.adapter.EducationResourceCategoryAdapter
import com.icst.commonmodule.model.EducationResourceCategoryModel
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.Constant.EDU_RESOURCE_ID
import com.icst.commonmodule.utils.Constant.EDU_RESOURCE_SUBTITLE
import com.icst.commonmodule.utils.Constant.EDU_RESOURCE_TITLE
import com.icst.commonmodule.utils.isNetWork


class ResourcesActivity : ActivityBase() {

    private var resourceCategoryList: List<EducationResourceCategoryModel.Data> = ArrayList()


    val binding: ActivityResourcesBinding by binding(R.layout.activity_resources)
    private val viewModel = ResourcesViewModel()



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
        requestApiGetResources()
    }

    private fun requestApiGetResources() {
        if (isNetWork(this)) {
            viewModel.getEducationResourceCategoryApiCall(
                context = this
            )
        } else {
            Toast.makeText(this, "Please try again, Network not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickListeners() {
        binding.include.toolbarBack.setOnClickListener { onBackPress() }
    }

    private val resourceOnClick = fun(position: Int) {
        val intentEdu = Intent(this, EducationResourcesActivity::class.java)
        resourceCategoryList[position].apply {
            Log.e("TAG", "resourceOnClick: Category Id --> $catId")
            Log.e("TAG", "resourceOnClick: Category title --> $title")
            Log.e("TAG", "resourceOnClick: Category subTitle --> $subTitle")
            intentEdu.putExtra(EDU_RESOURCE_ID, catId.toString())
            intentEdu.putExtra(EDU_RESOURCE_TITLE, title)
            intentEdu.putExtra(EDU_RESOURCE_SUBTITLE, subTitle)
        }
        startActivity(intentEdu)
    }


    private fun observer() {
        viewModel.resourceResponse.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        val data = it.value as EducationResourceCategoryModel
                        if (!data.equals(null)) {
                            resourceCategoryList = data.data!!
                            if (resourceCategoryList.isNotEmpty()) {
                                binding.rclResourcesDetail.visibility = View.VISIBLE
                                binding.rclResourcesDetail.setHasFixedSize(true)
                                binding.rclResourcesDetail.layoutManager =
                                    GridLayoutManager(this, 2)
                                binding.rclResourcesDetail.adapter =
                                    EducationResourceCategoryAdapter(
                                        this,
                                        resourceCategoryList,
                                        resourceOnClick
                                    )
                            } else {
                                binding.rclResourcesDetail.visibility = View.GONE
                                binding.tvResourcesDetailEmpty.visibility = View.VISIBLE
                            }

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