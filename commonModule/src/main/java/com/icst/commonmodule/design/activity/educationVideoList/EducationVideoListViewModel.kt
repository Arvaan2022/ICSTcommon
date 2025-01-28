package com.icst.commonmodule.design.activity.educationVideoList

import android.content.Context
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityEducationVideoListBinding
import com.icst.commonmodule.design.activity.educationVideoList.adapter.EducationSubAdapter
import com.icst.commonmodule.design.activity.educationVideoList.adapter.MyVideoTittleAdapterAdapter
import com.icst.commonmodule.model.CategoryVideo
import com.icst.commonmodule.model.EducationList
import com.icst.commonmodule.repository.education.EducationVideoListRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch

class EducationVideoListViewModel : ViewModel() {
    private val educationVideoListRepository = EducationVideoListRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()
    lateinit var binding: ActivityEducationVideoListBinding
    var educationSearchName = ArrayList<String>()
    private var educationList: EducationList? = null
    var educattionHomeAlldata = ArrayList<CategoryVideo>()
    private val _educationVideoListResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val educationVideoListResponse: LiveData<Resource<Any?>>
        get() {
            return _educationVideoListResponse
        }




    fun getEducationVideoList(
        name: String,
        context: Context
    ) {
        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _educationVideoListResponse.value =
                educationVideoListRepository.getEducationVideoList(
                    name = name,
                    context = context
                )
            activityBase.get()!!.dismissProgress()
        }
    }


    fun setData(response: EducationList) {
        educationList = response
        if (educationList!!.data.isNotEmpty()) {
            educationSearchName.clear()

            educationSearchName.add(activityBase.get()!!.getString(R.string.all_videos))

            if (binding.editSearch.text.toString().isNotEmpty()) {
                val educationSearchList: ArrayList<CategoryVideo> = ArrayList()
                for (i in educationList!!.data.indices) {
                    educationSearchName.add(educationList!!.data[i].categoryName)
                    educationSearchList.addAll(educationList!!.data[i].categoryVideo)
                }
                setTittleAdapter(educationSearchName, binding)
                binding.recyclerEducationVideo.layoutManager =
                    GridLayoutManager(activityBase.get()!!, 2)
                binding.recyclerEducationVideo.adapter =
                    EducationSubAdapter(activityBase.get()!!, educationSearchList)
            } else {
                for (i in educationList!!.data.indices) {
                    educationSearchName.add(educationList!!.data[i].categoryName)
                    educattionHomeAlldata.addAll(educationList!!.data[i].categoryVideo)
                }
                setTittleAdapter(educationSearchName, binding)
                binding.recyclerEducationVideo.layoutManager =
                    GridLayoutManager(activityBase.get()!!, 2)
                binding.recyclerEducationVideo.adapter =
                    EducationSubAdapter(
                        activityBase.get()!!,
                        educattionHomeAlldata
                    )
            }
        } else {
            Toast.makeText(activityBase.get()!!, "No data available", Toast.LENGTH_SHORT).show()

        }

    }

    private fun setTittleAdapter(list: ArrayList<String>, binding: ActivityEducationVideoListBinding) {
        binding.rcVideoContent.setHasFixedSize(true)
        binding.rcVideoContent.layoutManager =
            LinearLayoutManager(activityBase.get()!!, LinearLayoutManager.HORIZONTAL, false)
        val myvideoTittleAdapterAdapter =
            MyVideoTittleAdapterAdapter(activityBase.get()!!, list, onCallClick)
        binding.rcVideoContent.adapter = myvideoTittleAdapterAdapter
    }

    private val onCallClick = fun(pos: Int) {
        if (educationSearchName[pos] == "All Videos" || educationSearchName[pos] == "All Videos") {
            binding.recyclerEducationVideo.layoutManager =
                GridLayoutManager(activityBase.get()!!, 2)
            binding.recyclerEducationVideo.adapter =
                EducationSubAdapter(
                    activityBase.get()!!,
                    educattionHomeAlldata,
                )
        } else {
            val categoryVideo = ArrayList<CategoryVideo>()
            categoryVideo.clear()
            categoryVideo.addAll(educationList!!.data[pos - 1].categoryVideo)
            binding.recyclerEducationVideo.layoutManager =
                GridLayoutManager(activityBase.get()!!, 2)
            binding.recyclerEducationVideo.adapter =
                EducationSubAdapter(activityBase.get()!!, categoryVideo,)
        }

    }
}