package com.skunk.kibandastore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skunk.kibandastore.R
import com.skunk.kibandastore.model.CatItem

class CatAdapter(private val catList: List<CatItem>, private var listener: OnItemClick?):RecyclerView.Adapter<CatAdapter.CatViewHolder>() {
    interface OnItemClick{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener:OnItemClick){
        this.listener=listener
    }
    class CatViewHolder(itemView:View,listener: OnItemClick?):RecyclerView.ViewHolder(itemView){
        private var image: ImageView =itemView.findViewById(R.id.image_cat)
        var name: TextView =itemView.findViewById(R.id.name_cat)

        fun bind(catItem:CatItem){
            name.text = catItem.name
            image.setImageResource(catItem.image!!)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_item,parent,false)
        return CatViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(catList[position])
    }

    override fun getItemCount()=catList.size
}