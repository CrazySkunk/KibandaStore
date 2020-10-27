package com.skunk.kibandastore;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        TextView phone = findViewById(R.id.phone);
        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            String mPhone = phone.getText().toString().trim();
            if (TextUtils.isEmpty(mPhone)){
                phone.setError("Can't be empty");
            }else{
                //TODO : implement OTP
            }
        });
    }
}