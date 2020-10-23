package com.skunk.kibandastore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skunk.kibandastore.R
import com.skunk.kibandastore.model.CategoryOffer

class CategoryAdapter(private val categoryList: List<CategoryOffer>, private var listener: OnItemClick) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    interface OnItemClick {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClick) {
        this.listener = listener
    }

    class CategoryViewHolder(itemView: View,listener: OnItemClick) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)

        fun bind(offer: CategoryOffer) {
            name.text = offer.name
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount() = categoryList.size
}