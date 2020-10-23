package com.skunk.kibandastore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skunk.kibandastore.R
import com.skunk.kibandastore.model.HotOffer

class HotOffersAdapter(private val hotOfferList:List<HotOffer>):RecyclerView.Adapter<HotOffersAdapter.HotOfferViewHolder>() {
    class HotOfferViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val title:TextView = itemView.findViewById(R.id.title_hot)
        private val description:TextView = itemView.findViewById(R.id.description_hot)
        val image:ImageView = itemView.findViewById(R.id.image_hot)

        fun bind(offer:HotOffer){
            title.text = offer.title
            description.text = offer.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotOfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hot_deal,parent,false)
        return HotOfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotOfferViewHolder, position: Int) {
        holder.bind(hotOfferList[position])
    }

    override fun getItemCount()=hotOfferList.size
}