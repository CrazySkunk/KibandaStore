package com.skunk.kibandastore;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.skunk.kibandastore.model.User;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    private EditText name, email, phone;
    private String mName, mEmail, mLocation, mPhone;
    private ImageView pic;
    private TextView location;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        Button submit = findViewById(R.id.submit);
        location = findViewById(R.id.location);
        pic = findViewById(R.id.pic);
        TextView warn = findViewById(R.id.warn_text);
        warn.setVisibility(View.VISIBLE);

        pic.setOnClickListener(view -> checkReadStoragePermission(this));
        submit.setOnClickListener(view -> {
            mName = name.getText().toString().trim();
            mEmail = email.getText().toString().trim();
            mLocation = location.getText().toString();
            mPhone = phone.getText().toString().trim();
            if (TextUtils.isEmpty(mName)) {
                name.setError("Can't be empty...");
                if (TextUtils.isEmpty(mEmail)) {
                    email.setError("Can't be empty...");
                    if (TextUtils.isEmpty(mLocation)) {
                        location.setError("Can't be empty");
                        if (TextUtils.isEmpty(mPhone)) {
                            phone.setError("Can't be empty...");
                        }
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
                    showDialogue(context);
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

    private void showDialogue(Context context) {
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(context);
        alertDialogue.setTitle("This permission is necessary");
        alertDialogue.setMessage("External storage permission is required");
        alertDialogue.setPositiveButton("Yes", (dialogInterface, i) -> ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1));
        AlertDialog alert = alertDialogue.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            picUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                pic.setImageBitmap(bitmap);
                pic.setAlpha(0F);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void performRegister() {
        String fileName = FirebaseAuth.getInstance().getUid();
        StorageReference storage = FirebaseStorage.getInstance().getReference("ProfilePic/" + fileName);
        storage.putFile(picUri).addOnCompleteListener(task -> storage.getDownloadUrl().addOnSuccessListener(uri -> addDetailsToDB(uri.toString())));
    }

    private void addDetailsToDB(String uri) {
        String uid = FirebaseAuth.getInstance().getUid();
        User user = new User(uid,mName,mEmail,mPhone,uri,mLocation);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        assert uid != null;
        reference.child(uid).setValue(user).addOnSuccessListener(aVoid -> {
            Toast.makeText(SignUpActivity.this, "Details saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
        });
    }
}