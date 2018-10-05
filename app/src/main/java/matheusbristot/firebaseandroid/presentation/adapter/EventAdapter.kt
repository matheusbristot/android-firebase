package matheusbristot.firebaseandroid.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import matheusbristot.firebaseandroid.data.entity.Event

class EventAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val events = ArrayList<Event>()
    private var eventClickCallback: ((Event) -> Unit)? = null

    private val ITEM_EVENT = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EventViewHolder.inflate(parent)

    override fun getItemCount() = events.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        (viewHolder as? EventViewHolder)?.format(events[position], eventClickCallback)
    }

    override fun getItemViewType(position: Int) = ITEM_EVENT

    fun setEvents(events: List<Event>, callback: (Event) -> Unit) {
        eventClickCallback = callback
        this.events.clear()
        this.events.addAll(events)
        notifyDataSetChanged()
    }
}