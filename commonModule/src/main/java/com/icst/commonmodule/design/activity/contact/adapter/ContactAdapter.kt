package com.icst.commonmodule.design.activity.contact.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.RowContactBinding
import com.icst.commonmodule.model.ContactList
import com.icst.commonmodule.utils.generateCircleTag

class ContactAdapter(
    var context: ActivityBase,
    var contactList: MutableList<ContactList.ContactData>,
    val rowViewClickInterface: (Int, Int) -> Unit,

    ) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    lateinit var binding: RowContactBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        binding = RowContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder()
    }

    override fun getItemCount(): Int {
        return contactList.size
    }



    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.setValue(position)
    }

    inner class ContactViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun setValue(position: Int) {
            val mContactObj = contactList[position]

            binding.rowTvContactDesignation.text = mContactObj.with

            binding.rowTvContactWorkPlace.text = mContactObj.place_work

            if (position % 2 == 0) {
                binding.rowTvContactTag.background = generateCircleTag(context, true)
            } else {
                binding.rowTvContactTag.background = generateCircleTag(context, false)
            }

            binding.rowTvContactTag.text =
                getUserNameTag(mContactObj.first_name, mContactObj.last_name)

            binding.rowTvContactName.text =
                mContactObj.first_name.plus(" ").plus(mContactObj.last_name)

            binding.rowIbContactCall.setOnClickListener {
//                rowViewClickInterface.invoke(0, position)
                if (contactList[position].phone.isNotEmpty()){
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.data = Uri.parse("tel: ${contactList[position].phone}")
                    context.startActivity(callIntent)
                }else{
                    context.showSnackBar(
                        binding.rowContactParent,"Phone number not available", ActivityBase.ACTIONSNACKBAR.DISMISS
                    )
                }

            }

            binding.rowIbContactMsg.setOnClickListener {
//                rowViewClickInterface.invoke(1, position)
                if (contactList[position].email.isNotEmpty()){
                    val intent = Intent(
                        Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", contactList[position].email, null
                        )
                    )
                    context.startActivity(intent)
                }else{
                    context.showSnackBar(
                        binding.rowContactParent,"Email address not available", ActivityBase.ACTIONSNACKBAR.DISMISS
                    )
                }

            }

            binding.rowIbContactRemove.setOnClickListener {
                rowViewClickInterface.invoke(2, position)
            }

        }

    }

    private fun getUserNameTag(userFName: String, userLName: String): String {
        var returnNameTag: String? = null

        if (!TextUtils.isEmpty(userFName) && !TextUtils.isEmpty(userLName)) {
            returnNameTag = userFName[0].toString().plus(userLName[0].toString())
        } else if (!TextUtils.isEmpty(userFName) && TextUtils.isEmpty(userLName)) {
            returnNameTag = userFName[0].toString()
        } else if (TextUtils.isEmpty(userFName) && !TextUtils.isEmpty(userLName))
            returnNameTag = userLName[0].toString()

        return returnNameTag!!
    }
}