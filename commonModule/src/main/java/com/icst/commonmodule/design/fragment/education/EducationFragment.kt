package com.icst.commonmodule.design.fragment.education

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.icst.commonmodule.app.App
import com.icst.commonmodule.databinding.FragmentEducationCommonBinding
import com.icst.commonmodule.design.activity.educationVideoList.EducationVideoListActivity
import com.icst.commonmodule.design.activity.resources.ResourcesActivity
import com.icst.commonmodule.model.EducationContent
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant.handlerApiError


class EducationFragment: Fragment(){
    private lateinit var binding: FragmentEducationCommonBinding
    private val viewModel = EducationViewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationCommonBinding.inflate(inflater, container, false)

        binding.educationToolbar.toolbarLogo.setImageResource(App.appLogo)

        observer()
        onClickView()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getEducationDataApiCall(context = requireContext())
    }



    private fun observer(){
        viewModel.educationDataResponse.observe(requireActivity()){
            when (it) {
                is Resource.Success -> {
                    val response = it.value as EducationContent

                    viewModel.setEducationData(binding,response)

                }
                is Resource.Failure -> {
                    handlerApiError(it, requireContext(), binding.root)
                }
                else -> {
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }


    private fun onClickView(){

        binding.includeEducation.tvEducationExploreEdu.setOnClickListener {
            startActivity(Intent(requireContext(), EducationVideoListActivity::class.java))
        }
        binding.includeEducation.tvEduExploreAllResources.setOnClickListener {
            startActivity(Intent(requireContext(), ResourcesActivity::class.java))
        }
    }

}