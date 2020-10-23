package com.skunk.kibandastore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.skunk.kibandastore.model.CheckOutRowItem;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView itemRecyclerView;
    private List<CheckOutRowItem> itemsList;
    private Button checkOut;
    private Toolbar toolbar;
    private ImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }
}