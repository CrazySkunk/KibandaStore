package com.skunk.kibandastore;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skunk.kibandastore.adapters.OffersAdapter;
import com.skunk.kibandastore.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class OffersActivity extends AppCompatActivity {
    private ArrayList<Offer> offerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        EditText search = findViewById(R.id.searchView);
        RecyclerView offerRecycler = findViewById(R.id.offer_recycler);
        AdView adView = findViewById(R.id.adView);
        MobileAds.initialize(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        offerRecycler.setHasFixedSize(true);
        offerRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        OffersAdapter offersAdapter = new OffersAdapter(getOffers(), null);
        offerRecycler.setAdapter(offersAdapter);
        offersAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra("offer",offerArrayList.get(position));
            startActivity(intent);
        });

    }

    private List<Offer> getOffers() {
        offerArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("offers");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()) {
                    Offer offer = ds.getValue(Offer.class);
                    offerArrayList.add(offer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  offerArrayList;
    }
}