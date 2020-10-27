package com.skunk.kibandastore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skunk.kibandastore.R;
import com.skunk.kibandastore.model.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    List<CartItem> cartItemList;

    public CartAdapter(List<CartItem> cartList){
        this.cartItemList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_out_row_item,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(cartItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder{
        TextView index,name,quantity,price;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.index);
            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
        }
        protected void bind(CartItem item){
            String ind = String.valueOf(item.getIndex());
            String quant = String.valueOf(item.getQuantity());
            String amnt = String.valueOf(item.getPrice());
            index.setText(ind);
            name.setText(item.getName());
            quantity.setText(quant);
            price.setText(amnt);
        }
    }
}
