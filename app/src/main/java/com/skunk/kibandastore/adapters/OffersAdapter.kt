package com.skunk.kibandastore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skunk.kibandastore.R
import com.skunk.kibandastore.model.Offer
import com.squareup.picasso.Picasso

class OffersAdapter(private val categoryList: List<Offer>, private var listener: OnItemClick?) : RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {
    interface OnItemClick {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClick) {
        this.listener = listener
    }

    class OfferViewHolder(itemView: View,listener: OnItemClick?) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_offer)
        private val image: ImageView = itemView.findViewById(R.id.offerImage)
        val description: TextView = itemView.findViewById(R.id.description_offer)
        private val price: TextView = itemView.findViewById(R.id.price_offer)

        fun bind(offer: Offer) {
            title.text = offer.title
            description.text = offer.description
            price.text = offer.price
            Picasso.get().load(offer.imageUrl).placeholder(R.drawable.image).fit().centerInside().resize(300,200).into(image)
        }
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    listener!!.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_list_item, parent, false)
        return OfferViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size
}