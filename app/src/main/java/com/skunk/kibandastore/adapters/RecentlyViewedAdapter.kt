package com.skunk.kibandastore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skunk.kibandastore.R
import com.skunk.kibandastore.model.RecentlyViewed

class RecentlyViewedAdapter(private val offerList:List<RecentlyViewed>, private var listener:OnItemClick) : RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedViewHolder>() {
    interface OnItemClick{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClick){
        this.listener = listener
    }
    class RecentlyViewedViewHolder(itemView: View,listener: OnItemClick) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title_recent)
        private val description: TextView = itemView.findViewById(R.id.description_recent)
        val image: ImageView = itemView.findViewById(R.id.image_recent)

        fun bind(offer:RecentlyViewed){
            title.text = offer.title
            description.text = offer.description
        }
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recently_viewed_item,parent,false)
        return RecentlyViewedViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: RecentlyViewedViewHolder, position: Int) {
        holder.bind(offerList[position])
    }

    override fun getItemCount()=offerList.size
}