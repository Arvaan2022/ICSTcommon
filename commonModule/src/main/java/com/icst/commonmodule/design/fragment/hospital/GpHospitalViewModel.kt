package  com.icst.commonmodule.design.fragment.hospital

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.repository.gphospital.GpHospitalRepository
import com.icst.commonmodule.retrofit.Resource
import kotlinx.coroutines.launch


class GpHospitalViewModel : ViewModel() {

    private val gpHospitalRepository = GpHospitalRepository.getInstance()


    private val _getGpHospitalCategoryResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()

    val getGpHospitalCategoryResponse: LiveData<Resource<Any?>>
        get() {
            return _getGpHospitalCategoryResponse
        }

    fun getGpHospitalCategoryApiCall(
        language: String,
        token: String,
        url: String,
        context: Context
    ) {
        viewModelScope.launch {
            _getGpHospitalCategoryResponse.value = Resource.Loading
            _getGpHospitalCategoryResponse.value =
                gpHospitalRepository.getGpHospitalCategoryApiCall(
                    language = language,
                    token = token, url = url,context
                )

        }
    }
}