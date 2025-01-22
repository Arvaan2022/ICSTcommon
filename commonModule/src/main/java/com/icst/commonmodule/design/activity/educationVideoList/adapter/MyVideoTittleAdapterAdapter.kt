package com.icst.commonmodule.design.activity.educationVideoList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.icst.commonmodule.R
import com.icst.commonmodule.databinding.ItemListVideoTittleBinding


class MyVideoTittleAdapterAdapter(
    var context: Context,
    var listTittle: ArrayList<String>,
    val onCallClick: (Int) -> Unit,
) : RecyclerView.Adapter<MyVideoTittleAdapterAdapter.MyTriggerViewHolder>() {

    // lateinit var binding: ItemListVideoTittleBinding


    private var testPosition: Int = 0
    private val TAG = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTriggerViewHolder {
        val binding =
            ItemListVideoTittleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyTriggerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTittle.size
    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }


    override fun onBindViewHolder(holder: MyTriggerViewHolder, position: Int) {
        holder.setValue(position)

    }

    inner class MyTriggerViewHolder(val binding: ItemListVideoTittleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setValue(position: Int) {
            binding.tvListItem.text = listTittle[position]
            binding.tvListItem.setOnClickListener {
                testPosition = position
                onCallClick.invoke(position)
                notifyDataSetChanged()
            }

            if (testPosition == position) {
                binding.tvListItem.setBackgroundResource(R.drawable.btn_blue_radius_2)
                binding.tvListItem.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.tvListItem.setBackgroundResource(R.drawable.item_row_education_list)
                binding.tvListItem.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }

    }
}