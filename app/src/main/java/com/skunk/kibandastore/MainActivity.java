package com.skunk.kibandastore;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.skunk.kibandastore.adapters.CategoryAdapter;
import com.skunk.kibandastore.adapters.HotOffersAdapter;
import com.skunk.kibandastore.adapters.RecentlyViewedAdapter;
import com.skunk.kibandastore.model.CategoryOffer;
import com.skunk.kibandastore.model.HotOffer;
import com.skunk.kibandastore.model.RecentlyViewed;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    HotOffersAdapter hotOffersAdapter;
    CategoryAdapter categoryAdapter;
    RecentlyViewedAdapter recentlyViewedAdapter;
    ArrayList<HotOffer> hotOffers;
    AdView adView;
    ArrayList<CategoryOffer> categoryOffers;
    ArrayList<RecentlyViewed> recentlyViewed;
    RecyclerView hotOfferRecycler, categoryRecycler, recentlyViewRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        categoryRecycler = findViewById(R.id.category_recyclerview);
        categoryRecycler.setHasFixedSize(true);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        categoryAdapter = new CategoryAdapter(getCategory(),null);
        categoryRecycler.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
            intent.putExtra("Category",categoryOffers.get(position));
            startActivity(intent);
        });

        recentlyViewRecycler = findViewById(R.id.recently_viewed_recycler);
        recentlyViewRecycler.setHasFixedSize(true);
        recentlyViewRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recentlyViewedAdapter = new RecentlyViewedAdapter(getRecentlyViewedList(),null);
        recentlyViewRecycler.setAdapter(recentlyViewedAdapter);
        recentlyViewedAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this,CategoryActivity.class);
            intent.putExtra("Category",recentlyViewed.get(position));
            startActivity(intent);
        });

        hotOfferRecycler = findViewById(R.id.hot_offer_recyclerView);
        hotOfferRecycler.setHasFixedSize(true);
        hotOfferRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        hotOffersAdapter = new HotOffersAdapter(getHotOffers());
    }
    private ArrayList<RecentlyViewed> getRecentlyViewedList(){
        recentlyViewed = new ArrayList<>();
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        recentlyViewed.add(new RecentlyViewed("Title","imageUrl","Description"));
        return recentlyViewed;
    }

    private ArrayList<HotOffer> getHotOffers() {
        hotOffers = new ArrayList<>();
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        hotOffers.add(new HotOffer("Hotoffer1", "Hotoffer1", "hot offer"));
        return hotOffers;
    }
    private ArrayList<CategoryOffer> getCategory(){
        categoryOffers = new ArrayList<>();
        categoryOffers.add(new CategoryOffer("Vegetable"));
        categoryOffers.add(new CategoryOffer("Fruits"));
        categoryOffers.add(new CategoryOffer("Fish"));
        categoryOffers.add(new CategoryOffer("Tubers"));
        categoryOffers.add(new CategoryOffer("Cookies"));
        categoryOffers.add(new CategoryOffer("Cookies"));
        categoryOffers.add(new CategoryOffer("Cookies"));
        categoryOffers.add(new CategoryOffer("Cookies"));
        return  categoryOffers;
    }
}