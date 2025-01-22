package com.icst.commonmodule.design.activity.reminder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icst.commonmodule.databinding.ItemCalandarEventBinding
import com.icst.commonmodule.model.ListScheduleModel
import com.icst.commonmodule.utils.convertStringToDate

class CalandarEventAdapter(
    val context: Context,
    val appoinmentList: List<ListScheduleModel.DataItem>,
    private val onEditCLick: OnEditCLick,
    private val onDeleteItemClick: OnDeleteItemClick
) : RecyclerView.Adapter<CalandarEventAdapter.ArticleViewHolder>() {




    init {
        val map: LinkedHashMap<String, List<ListScheduleModel.DataItem>> =
            LinkedHashMap<String, List<ListScheduleModel.DataItem>>()
        val titles: MutableList<String> = ArrayList()
        map["Recommended today"] = appoinmentList
        titles.add("title1")
//        resetGroups(map, titles)
    }



    inner class ArticleViewHolder(val binding: ItemCalandarEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setValue() {
            val item = appoinmentList[bindingAdapterPosition]
            binding.tvmonth.text = convertStringToDate(item.dayDate, "yyyy-MM-dd", "MMM")
            binding.textEventLocation.text = item.location
            binding.tvDay.text = convertStringToDate(item.dayDate, "yyyy-MM-dd", "EEE")
            binding.textDate.text = convertStringToDate(item.dayDate, "yyyy-MM-dd", "dd")
            binding.textEventTitle.text = item.reminderName
            binding.rowIbAppoinmentRemove.setOnClickListener {
                onDeleteItemClick.onDeleteItem(
                    bindingAdapterPosition
                )
            }
            binding.rowIbAppoinmentEdit.setOnClickListener {
                onEditCLick.OnEditCLickitem(
                    bindingAdapterPosition
                )
            }
            binding.llViewData.setOnClickListener {  onDeleteItemClick.onDataView(bindingAdapterPosition) }
        }
    }

    interface OnEditCLick {
        fun OnEditCLickitem(adapterPosition: Int)
    }

    interface OnDeleteItemClick {
        fun onDeleteItem(adapterPosition: Int)

        fun onDataView(adapterPosition: Int)
    }

    companion object {
        private fun create(title: String, content: String, imgUrl: String): CalendarEvent {
            val calendarEvent: CalendarEvent = CalendarEvent()
            calendarEvent.title = title
            calendarEvent.content = content
            calendarEvent.imgUrl = imgUrl
            return calendarEvent
        }

        private fun create(p: Int): List<CalendarEvent> {
            val list: MutableList<CalendarEvent> = ArrayList<CalendarEvent>()
            if (p == 0) {
                list.add(
                    create(
                        "Cardiff W",
                        "12",
                        "http://cms-bucket.nosdn.127.net/catchpic/2/27/27e2ce7fd02e6c096e21b1689a8a3fe9.jpg?imageView&thumbnail=550x0"
                    )
                )
                list.add(
                    create(
                        "England & Walse",
                        "12",
                        "http://cms-bucket.nosdn.127.net/catchpic/2/27/27e2ce7fd02e6c096e21b1689a8a3fe9.jpg?imageView&thumbnail=550x0"
                    )
                )
                list.add(
                    create(
                        "Miami Event",
                        "12",
                        "http://cms-bucket.nosdn.127.net/catchpic/2/27/27e2ce7fd02e6c096e21b1689a8a3fe9.jpg?imageView&thumbnail=550x0"
                    )
                )
                list.add(
                    create(
                        "Badminton Wales",
                        "12",
                        "http://cms-bucket.nosdn.127.net/catchpic/2/27/27e2ce7fd02e6c096e21b1689a8a3fe9.jpg?imageView&thumbnail=550x0"
                    )
                )
                list.add(
                    create(
                        "World Events",
                        "12",
                        "http://cms-bucket.nosdn.127.net/catchpic/2/27/27e2ce7fd02e6c096e21b1689a8a3fe9.jpg?imageView&thumbnail=550x0"
                    )
                )
            }
            return list
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.setValue()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemCalandarEventBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount() = appoinmentList.size
}
