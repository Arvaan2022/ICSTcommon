package com.icst.commonmodule.common

import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.appcompat.widget.AppCompatSpinner
import com.icst.commonmodule.R
import java.util.LinkedList

class DayMultiSelectionSpinner : AppCompatSpinner, OnMultiChoiceClickListener,
    DialogInterface.OnCancelListener {
    private var items: List<String>? = null
    private var itemsId: ArrayList<String>? = null
    private lateinit var selected: BooleanArray
    private lateinit var disabledItems: BooleanArray
    private var defaultText: String? = null
    private var listener: MultiSpinnerListener? = null
    private var listView: ListView? = null

    constructor(context: Context?) : super(context!!) {}
    constructor(arg0: Context?, arg1: AttributeSet?) : super(arg0!!, arg1) {}
    constructor(arg0: Context?, arg1: AttributeSet?, arg2: Int) : super(
        arg0!!, arg1, arg2
    ) {
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {
        selected[which] = isChecked == true
    }

    override fun onCancel(dialog: DialogInterface) {
        // refresh text on spinner
        val spinnerBuffer = StringBuffer()

        var someSelected = false
        for (i in items!!.indices) {
            if (selected[i]) {
                spinnerBuffer.append(items!![i])
                spinnerBuffer.append(", ")
                someSelected = true
            }
        }
        var spinnerText: String?
        if (someSelected) {
            spinnerText = spinnerBuffer.toString()
            if (spinnerText.length > 2) spinnerText =
                spinnerText.substring(0, spinnerText.length - 2)
        } else {
            spinnerText = defaultText
        }
        val adapter = ArrayAdapter(
            context,
            R.layout.item_spinner_dropdown, arrayOf(spinnerText)
        )
        /*{
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }
        };*/setAdapter(adapter)
        listener!!.onItemsSelected(selectedStringDays)
        listener!!.onItemsSelectedIDIS(selectedStringIds)
    }

    val selectedStringIds: List<String?>
        get() {
            val selection: MutableList<String?> = LinkedList()
            for (i in itemsId!!.indices) {
                if (selected[i]) {
                    Log.e("TAG", "ID IS" + itemsId!![i])
                    selection.add(itemsId!![i])
                }
            }
            return selection
        }

    val selectedStringDays: List<String?>
        get() {
            val selection: MutableList<String?> = LinkedList()
            for (i in items!!.indices) {
                if (selected[i]) {
                    if (items!![i] == context.getString(R.string.Monday)) {
                        selection.add("Monday")
                    } else if (items!![i] == context.getString(R.string.Tuesday)) {
                        selection.add("Tuesday")
                    } else if (items!![i] == context.getString(R.string.Wednesday)) {
                        selection.add("Wednesday")
                    } else if (items!![i] == context.getString(R.string.Thursday)) {
                        selection.add("Thursday")
                    } else if (items!![i] == context.getString(R.string.Friday)) {
                        selection.add("Friday")
                    } else if (items!![i] == context.getString(R.string.Saturday)) {
                        selection.add("Saturday")
                    } else if (items!![i] == context.getString(R.string.Sunday)) {
                        selection.add("Sunday")
                    }
                    Log.e("TAG", "ID IS" + items!![i])
                }
            }
            return selection
        }

    override fun performClick(): Boolean {
        val dialog: AlertDialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.please_select_days))
        builder.setMultiChoiceItems(items!!.toTypedArray<CharSequence>(), selected, this)
        builder.setPositiveButton(
            "OK"
        ) { dial, _ ->
            listener!!.onItemsSelectedIDIS(selectedStringIds)
            dial.cancel()
        }
        builder.setOnCancelListener(this)
        dialog = builder.create()
        dialog.show()
        listView = dialog.listView
        listView?.setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
            override fun onChildViewAdded(view: View, child: View) {
                val text = (child as AppCompatCheckedTextView).text
                val itemIndex = items!!.indexOf(text)
                val itemIndexID = itemsId!!.indexOf(text)
                Log.e("Log 1", "Text === $text")
                Log.e("Log 2", "Index === $itemIndex")
                Log.e("Log 2", "Index === $itemIndexID")

            }

            override fun onChildViewRemoved(view: View, view1: View) {}
        })
        return true
    }

    fun setItems(
        items: List<String>, selectedList: String, itemsId: ArrayList<String>, allText: String,
        listener: MultiSpinnerListener?
    ) {
        this.items = items
        this.itemsId = itemsId
        Log.e("TAG", "ITEM ID IS$items")
        Log.e("TAG", "ITEM ID IS$itemsId")
        defaultText = allText
        this.listener = listener
        var spinnerText = allText
        selected = BooleanArray(items.size)
        disabledItems = BooleanArray(items.size)
        if (selectedList == "") {
            spinnerText = allText
        } else {
            spinnerText = ""
            val selectedItems =
                selectedList.trim { it <= ' ' }.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()

            for (i in selectedItems.indices)  //disabledItems[i] = l != 0;
                for (j in items.indices) if (selectedItems[i].trim { it <= ' ' } == items[j]) {
                    selected[j] = true
                    spinnerText += (if (spinnerText == "") "" else ", ") + items[j]
                    break
                }
        }

        val adapter = ArrayAdapter(
            context,
            R.layout.item_spinner_dropdown, arrayOf(spinnerText)
        )
   setAdapter(adapter)
        this.listener!!.onItemsSelectedIDIS(selectedStringIds)
    }

    interface MultiSpinnerListener {
        fun onItemsSelected(strings: List<String?>?)
        fun onItemsSelectedIDIS(strings: List<String?>?)
    }
}