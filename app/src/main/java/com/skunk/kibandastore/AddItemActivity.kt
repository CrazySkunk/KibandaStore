package com.skunk.kibandastore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.skunk.kibandastore.model.AddItem
import java.io.IOException

class AddItemActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    lateinit var title: TextView
    lateinit var description: TextView
    private lateinit var price: TextView
    private lateinit var quantity: TextView
    lateinit var offer: ImageView
    private lateinit var add: Button
    private lateinit var toolbar: Toolbar
    private lateinit var category: Spinner
    private lateinit var categoryName:String
    private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        toolbar = findViewById(R.id.toolbarPlus)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        val adapter = ArrayAdapter.createFromResource(this@AddItemActivity,R.array.category,R.layout.category_item)
        title = findViewById(R.id.titlePlus)
        description = findViewById(R.id.descriptionPlus)
        category = findViewById(R.id.category_spinner)
        offer = findViewById(R.id.plusImage)
        price = findViewById(R.id.price_per_unit)
        quantity = findViewById(R.id.quantity)
        add = findViewById(R.id.add)
        category.adapter=adapter

        add.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this@AddItemActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@AddItemActivity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@AddItemActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialogue(this)
                } else {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, 0)
                }
            }
        }
        add.setOnClickListener {
            val mTitle = title.text.toString().trim()
            val mDescription = description.text.toString().trim()
            val mPrice = price.text.toString().trim()
            val mQuantity = quantity.text.toString().trim()
            if (TextUtils.isEmpty(mTitle)) {
                title.error = "Cannot be empty"
                if (TextUtils.isEmpty(mDescription)) {
                    description.error = "Cannot be empty"
                    if (TextUtils.isEmpty(mPrice)) {
                        price.error = "Cannot be empty"
                        if (TextUtils.isEmpty(mQuantity)) {
                            quantity.error = "Cannot be empty"
                        }
                    }
                }
            } else {
                addItem(mTitle, mDescription, mPrice, mQuantity,categoryName)
            }
        }
    }

    private fun addItem(nTitle: String, nDescription: String, nPrice: String, nQuantity: String,categoryName:String) {
        val fileName = FirebaseAuth.getInstance().currentUser!!.uid + imageUri.toString() + nTitle
        val storageRef = FirebaseStorage.getInstance().getReference("OfferImages/$fileName")
        storageRef.putFile(imageUri).addOnCompleteListener {
            storageRef.downloadUrl.addOnSuccessListener { addOfferToDb(it.toString(),nTitle,nDescription,nPrice,nQuantity,categoryName) }
        }
    }

    private fun addOfferToDb(uri: String, nTitle: String, nDescription: String, nPrice: String, nQuantity: String,categoryName: String) {
        val addItem = AddItem(uri,nTitle,nDescription,nPrice,nQuantity,categoryName)
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = FirebaseDatabase.getInstance().getReference("Offers")
        ref.child(categoryName).child(nTitle+uid).setValue(addItem).addOnSuccessListener {
            Toast.makeText(this@AddItemActivity,"Product added",Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                offer.setImageBitmap(bitmap)
                offer.alpha = 0F
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                println("finally")
            }
        }
    }

    private fun showDialogue(context: Context) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Sociorail").setMessage("External storage permission is required").setCancelable(true).setIcon(ActivityCompat.getDrawable(this, R.drawable.ic_launcher_foreground))
        dialog.setPositiveButton(R.string.yes) { _, _ ->
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        val alertDialog = dialog.create()
        alertDialog.show()
    }

    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, l: Long) {
        categoryName = adapterView!!.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}