package com.laisd.mycontacts

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val txtName = view.findViewById<TextView>(R.id.txtName)
    private val txtPhone = view.findViewById<TextView>(R.id.txtPhoneNumber)
    private val txtComplement = view.findViewById<TextView>(R.id.txtComplement)

    fun bind(contact: Contact) {
        txtName.text = contact.name
        txtPhone.text = contact.phoneNumber
        txtComplement.text = contact.complement
    }
}
