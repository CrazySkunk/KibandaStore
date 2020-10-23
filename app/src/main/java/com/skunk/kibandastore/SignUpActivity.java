package com.skunk.kibandastore;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText name, email, phone;
    private String mName, mEmail, mPhone;
    private ImageView pic;
    private TextView warn;
    private Button submit;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        submit = findViewById(R.id.submit);
        pic = findViewById(R.id.pic);
        warn = findViewById(R.id.warn_text);

        pic.setOnClickListener(view -> {
            checkReadStoragePermission(this);
        });
        submit.setOnClickListener(view -> {
            mName = name.getText().toString().trim();
            mEmail = email.getText().toString().trim();
            mPhone = phone.getText().toString().trim();
            if (TextUtils.isEmpty(mName)) {
                name.setError("Can't be empty...");
                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Can't be empty...");
                    if (TextUtils.isEmpty(mPhone)) {
                        phone.setError("Can't be empty...");
                    }
                }
            } else {
                performRegister();
            }
        });
    }

    private void checkReadStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialogue(context, Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        }
    }

    private void showDialogue(Context context, String permission) {
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(context);
        alertDialogue.setTitle("This permission is necessary");
        alertDialogue.setMessage("External storage permission is required");
        alertDialogue.setPositiveButton("Yes", (dialogInterface, i) -> {
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 1);
        });
        AlertDialog alert = alertDialogue.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 0);
        }
    }

    private void performRegister() {

    }
}