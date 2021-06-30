package com.vivekcorp.fooddonation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    ImageView imageView;
    Button takePhoto;

    TextView latitude;
    TextView longitude;

    EditText address;

    StorageReference mStorage;

    static Double newLocationlat;
    static Double newLocationlong;

    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        address = findViewById(R.id.address);

        mProgress = new ProgressDialog(this);

        Intent intent = getIntent();

        if(intent != null){
            newLocationlat = intent.getDoubleExtra("locationlat",0);
            newLocationlong = intent.getDoubleExtra("locationlong",0);

            latitude.setText(Double.toString(newLocationlat));
            longitude.setText(Double.toString(newLocationlong));
        }

        imageView = findViewById(R.id.imageView2);
        takePhoto = findViewById(R.id.button3);
    }

    public void AddLocation(View view) {
        Intent intent = new Intent(HomeActivity.this , MapActivity.class);
        startActivity(intent);
    }

    public void AddImage(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");

            imageView.setImageBitmap(imageBitmap);

            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("firebase_child")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }

    public void submit(View view) {
        
        if (address.getText().toString().equals("") && (latitude.getText().toString().equals("0.0")
                || longitude.getText().toString().equals("0.0"))){

            Toast.makeText(this,"Enter Any Address",Toast.LENGTH_SHORT).show();

        }else if(!address.getText().toString().equals("")){
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("firebase_child")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .child("address");
            ref.setValue(address.getText().toString());
        }else {
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("firebase_child")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .child("latitude");
            ref.setValue(latitude.getText().toString());

            DatabaseReference ref2 = FirebaseDatabase.getInstance()
                    .getReference("firebase_child")
                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                    .child("longitude");
            ref2.setValue(longitude.getText().toString());
        }

        DatabaseReference ref2 = FirebaseDatabase.getInstance()
                .getReference("firebase_child")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("collected");
        ref2.setValue("false");

        Intent intent = new Intent(this,SuccessActivity.class);
        startActivity(intent);
    }
}