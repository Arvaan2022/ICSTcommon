package  com.icst.commonmodule.design.fragment.hospital.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.icst.commonmodule.databinding.ListItemGpHospitalCommonBinding
import com.icst.commonmodule.model.GetGPHospitalCategoryModel

class GpHospitalAdapter(
    val context: Context,
    private val gpHospitalList: List<GetGPHospitalCategoryModel.Data>,
    val gpHospitalOnClick: (GetGPHospitalCategoryModel.Data) -> Unit,
) : RecyclerView.Adapter<GpHospitalAdapter.GpHospitalViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GpHospitalViewHolder {
        val binding = ListItemGpHospitalCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GpHospitalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GpHospitalViewHolder, position: Int) {
        holder.setValue(position)
    }

    override fun getItemCount(): Int {
        return gpHospitalList.size
    }

    inner class GpHospitalViewHolder(val binding: ListItemGpHospitalCommonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setValue(position: Int) {
            val gpHospitalObject = gpHospitalList[position]
            binding.apply {
                Glide.with(context).load(gpHospitalObject.imageUrl).into(ivItemGpHospital)
                Log.e("TAG", "setValue: ${gpHospitalObject.imageUrl}")
                tvItemGpHospital.text = gpHospitalObject.name

                llItemGpHospitalContainer.setOnClickListener {
                    gpHospitalOnClick.invoke(gpHospitalObject)
                }
            }
        }
    }
}
