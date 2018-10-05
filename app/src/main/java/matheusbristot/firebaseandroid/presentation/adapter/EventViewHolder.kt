package matheusbristot.firebaseandroid.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import matheusbristot.firebaseandroid.data.entity.Event
import matheusbristot.firebaseandroid.presentation.databinding.ItemEventBinding

class EventViewHolder(private val itemEventBinding: ItemEventBinding) : RecyclerView.ViewHolder(itemEventBinding.root) {

    fun format(event: Event, paymentCallback: ((Event) -> Unit)?) {
        itemEventBinding.root.setOnClickListener { paymentCallback?.invoke(event) }
        itemEventBinding.title.text = event.title
        itemEventBinding.description.text = event.description
    }

    companion object {
        fun inflate(parent: ViewGroup?) = EventViewHolder(ItemEventBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
    }
}