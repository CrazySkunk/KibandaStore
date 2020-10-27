package com.skunk.kibandastore

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.skunk.kibandastore.adapters.CatAdapter
import com.skunk.kibandastore.model.CatItem
import com.skunk.kibandastore.model.User
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class CategoryActivity : AppCompatActivity() {
    private lateinit var categoryRecycler: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var adView: AdView
    private lateinit var adapter: CatAdapter
    private lateinit var catList: ArrayList<CatItem>
    var name: String? = null
    var imageUrl: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Select Category"
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        adView = findViewById(R.id.adView2)
        categoryRecycler = findViewById(R.id.cat_recycler)
        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        getUser()
        categoryRecycler.setHasFixedSize(true)
        categoryRecycler.layoutManager = GridLayoutManager(this, 3)
        categoryRecycler.addItemDecoration(GridItemDecorator(3, dpToPx(10), true))
        categoryRecycler.itemAnimator = DefaultItemAnimator()
        adapter = CatAdapter(getCatList(), null)
        categoryRecycler.adapter = adapter
        adapter.setOnItemClickListener(object : CatAdapter.OnItemClick {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@CategoryActivity, OffersActivity::class.java)
                intent.putExtra("Offer", catList[position])
                startActivity(intent)
            }
        })
    }

    private fun getCatList(): ArrayList<CatItem> {
        catList = ArrayList()
        catList.add(CatItem(R.drawable.offer, "Vegetables"))
        catList.add(CatItem(R.drawable.offer, "Fishes"))
        catList.add(CatItem(R.drawable.offer, "Tubers"))
        catList.add(CatItem(R.drawable.offer, "Cookies"))
        catList.add(CatItem(R.drawable.offer, "Dairy"))
        catList.add(CatItem(R.drawable.offer, "Fruits"))
        catList.add(CatItem(R.drawable.offer, "Salad"))
        return catList
    }

    private fun getUser() {
        val ref = FirebaseDatabase.getInstance().getReference("users")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val user = i.value as User
                    if (user.uid == FirebaseAuth.getInstance().currentUser!!.uid) {
                        name = user.names
                        imageUrl = user.imageUrl
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cat_menu, menu)
        val target: ImageView = menu!!.getItem(R.id.account) as ImageView
        Picasso.get().load(imageUrl).placeholder(R.drawable.account).resize(50, 50).into(target)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cart -> startActivity(Intent(this@CategoryActivity, CartActivity::class.java))
            R.id.sell -> startActivity(Intent(this@CategoryActivity, AddItemActivity::class.java))
        }
//        return super.onOptionsItemSelected(item)
        return true
    }

    class GridItemDecorator(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.left = (spacing + column) * spacing / spanCount
                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        val r = resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics).roundToInt()
    }
}

