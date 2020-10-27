package com.skunk.kibandastore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skunk.kibandastore.adapters.CartAdapter;
import com.skunk.kibandastore.model.CartItem;
import com.skunk.kibandastore.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private List<CartItem> itemsList;
    private CartAdapter adapter;
    private ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getUserData();
        Toolbar toolbar = findViewById(R.id.toolbar_cart);
        pic = toolbar.findViewById(R.id.pro_pic_cart);
        RecyclerView itemRecyclerView = findViewById(R.id.item_recycler);
        Button checkOut = findViewById(R.id.check_out_button);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new CartAdapter(getList());
        itemRecyclerView.setAdapter(adapter);

        checkOut.setOnClickListener(view -> transactExchange());
    }

    private void transactExchange() {

    }

    private List<CartItem> getList() {
        itemsList = new ArrayList<>();
        return itemsList;
    }

    private void getUserData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.account).into(pic);
                getSupportActionBar().setTitle(user.getNames());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}