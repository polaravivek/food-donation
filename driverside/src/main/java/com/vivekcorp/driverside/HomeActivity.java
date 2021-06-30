package com.vivekcorp.driverside;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vivekcorp.driverside.model.DonatorInfo;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<DonatorInfo> myListData = new ArrayList<DonatorInfo>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ListAdapter adapter = new ListAdapter(myListData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(myListData != null){
                    myListData.clear();
                }

                for(DataSnapshot datasnapshot : snapshot.getChildren()){
                    String user;
                    String phone;
                    String key;
                    System.out.println(datasnapshot);

                    key = datasnapshot.getKey();
                    assert key != null;

                    user = Objects.requireNonNull(datasnapshot.child("name").getValue()).toString();
                    phone = Objects.requireNonNull(datasnapshot.child("phoneNo").getValue()).toString();

                    DatabaseReference reference = FirebaseDatabase
                            .getInstance().getReference().child("firebase_child");
                    reference.addValueEventListener(new ValueEventListener(){

                        DonatorInfo mydata;
                        String address;
                        String url;
                        Bitmap imageBitmap;
                        String latitude;
                        String longitude;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot datasnap : snapshot.getChildren()){
                                System.out.println(datasnap);
                                if(datasnap.hasChild("address")){
                                    address = Objects.requireNonNull(datasnap.child("address").getValue()).toString();
                                }else{
                                    address = "";
                                }
                                url = Objects.requireNonNull(datasnap.child("imageUrl").getValue()).toString();
                                try {
                                    imageBitmap = decodeFromFirebaseBase64(url);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if(datasnap.hasChild("latitude") && datasnap.hasChild("longitude")){
                                    latitude = Objects.requireNonNull(datasnap.child("latitude").getValue()).toString();
                                    longitude = Objects.requireNonNull(datasnap.child("longitude").getValue()).toString();
                                }else{
                                    latitude = "";
                                    longitude = "";
                                }

                                System.out.println("user"+user);
                                System.out.println("phone"+phone);
                                System.out.println("address"+address);
                                System.out.println(latitude);
                                System.out.println(longitude);

                                mydata = new DonatorInfo(user,phone,address,imageBitmap,latitude,longitude);

                                myListData.add(mydata);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                System.out.println("dataset is equal to"+myListData);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

}