package com.skunk.kibandastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.skunk.kibandastore.model.Offer
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    private lateinit var image:ImageView
    lateinit var title:TextView
    lateinit var description:TextView
    private lateinit var weight:Spinner
    private lateinit var mTitle:String
    private lateinit var mDescription:String
    private lateinit var mImageUrl:String
    private lateinit var mQuantity:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getData()
        weight = findViewById(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(this,R.array.quantity,R.layout.quantity_item)
        weight.adapter = adapter
        title.text=mTitle
        description.text = mDescription
        Picasso.get().load(mImageUrl).placeholder(R.drawable.image).resize(300,300).into(image)
    }
    private fun getData(){
        val intent = intent
        if (intent.hasExtra("offer")){
            val offer = intent.getParcelableExtra<Offer>("offer")
            mTitle = offer!!.title.toString()
            mDescription = offer.description.toString()
            mImageUrl = offer.imageUrl.toString()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val quantity = p0!!.getItemAtPosition(p2).toString()
        mQuantity = quantity
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}