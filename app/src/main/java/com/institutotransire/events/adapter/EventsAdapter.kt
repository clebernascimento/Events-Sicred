package com.institutotransire.events.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.institutotransire.events.R
import com.institutotransire.events.controller.DateTime
import com.institutotransire.events.services.model.Events
import com.institutotransire.events.util.Formatters
import com.institutotransire.events.util.ImgUtil
import lombok.SneakyThrows
import java.util.*

class EventsAdapter(private val context: Context, private val mEventsArrayList: ArrayList<Events>, var listener: OnClick) : RecyclerView.Adapter<EventsAdapter.EventsHolder>() {
    private val nf = Formatters.numFormat()

    interface OnClick {
        fun selectEvents(events: Events?, position: Int)
    }

    inner class EventsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardEvents: ConstraintLayout = itemView.findViewById(R.id.cardLayout)
        var mImageEvents: ImageView = itemView.findViewById(R.id.imgEvents)
        var mNameEvents: TextView = itemView.findViewById(R.id.nameEvents)
        var mDateEvents: TextView = itemView.findViewById(R.id.dateEvents)
        var mPriceEvents: TextView = itemView.findViewById(R.id.priceEvents)
        var mLocation: TextView = itemView.findViewById(R.id.loactionEvents)

        fun bindClick(events: Events?, position: Int, listener: OnClick) {
            itemView.setOnClickListener { view: View? -> listener.selectEvents(events, position) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false)
        return EventsHolder(view)
    }

    @SuppressLint("SetTextI18n", "ShowToast")
    @SneakyThrows
    override fun onBindViewHolder(holder: EventsHolder, position: Int) {
        val events = mEventsArrayList[position]
        ImgUtil.requestImg(context, holder.mImageEvents, events.image)
        holder.mNameEvents.text = events.title
        holder.mDateEvents.text = "Data: " + DateTime.getDate(events.date)
        holder.mPriceEvents.text = "R$ " + nf!!.format(events.price)

        if (events.latitude != null && events.longitude != null) {
            holder.mLocation.setOnClickListener {
                val uri = Uri.parse("http://maps.google.com/maps?daddr=" + events.latitude + "," + events.longitude)
                val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(mapIntent)
                } else {
                    Toast.makeText(context, "Erro ao localizar evento", Toast.LENGTH_SHORT)
                }
            }
        } else {
            Toast.makeText(context, "Erro ao localizar evento", Toast.LENGTH_SHORT)
        }
        holder.bindClick(events, position, listener)
    }

    override fun getItemCount(): Int {
        return mEventsArrayList.size
    }
}