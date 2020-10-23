package com.skunk.kibandastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class AddItemActivity : AppCompatActivity() {
    lateinit var title:TextView
    lateinit var description:TextView
    lateinit var  price:TextView
    lateinit var quantity:ImageView
    lateinit var offer:ImageView
    lateinit var add:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        title = findViewById(R.id.titlePlus)
        description = findViewById(R.id.descriptionPlus)
        price = findViewById(R.id.price_per_unit)
        quantity = findViewById(R.id.quantity)
        add = findViewById(R.id.add)
    }
}