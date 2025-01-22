package  com.icst.commonmodule.design.fragment.hospital


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.icst.commonmodule.databinding.FragmentGpAndHospitalCommonBinding
import com.icst.commonmodule.design.fragment.hospital.adapter.GpHospitalAdapter
import com.icst.commonmodule.model.GetGPHospitalCategoryModel
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant


class GpHospitalFragment : Fragment() {

    private lateinit var binding: FragmentGpAndHospitalCommonBinding

    private val viewModel =GpHospitalViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGpAndHospitalCommonBinding.inflate(inflater, container, false)
        observer()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
        setOnClickListeners()
    }

    private fun initViews() {
        if (arguments !=null){
            val url = requireArguments().getString("Url")
            val lan = requireArguments().getString("Language")
            val token = requireArguments().getString("token")
            if (lan != null && url !=null && token !=null) {
                viewModel.getGpHospitalCategoryApiCall(language = lan,url=url, token = token, context = requireContext())
            }
        }
    }

    private fun setOnClickListeners() {
        binding.tvEducationMySummary.setOnClickListener {
//            startActivity(Intent(requireContext(), MySummaryActivity::class.java))
        }
    }

    private val gpHospitalClickListener = fun(data: GetGPHospitalCategoryModel.Data) {
//        val intent = Intent(requireContext(), GpAndHospitalVisitActivity::class.java)
//        intent.putExtra(GP_HOSPITAL_CATEGORY_EXTRA, data)
//        startActivity(intent)
    }

    private fun observer() {
        viewModel.getGpHospitalCategoryResponse.observe(requireActivity()) {
            when (it) {
                is Resource.Success -> {
                    val response = it.value as GetGPHospitalCategoryModel

                    binding.rvGpHospital.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.rvGpHospital.setHasFixedSize(true)
                    binding.rvGpHospital.adapter =
                        GpHospitalAdapter(requireContext(), response.data, gpHospitalClickListener)
                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, requireContext(), binding.root)
                }

                else -> {
                }
            }
        }
    }
}