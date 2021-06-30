package com.vivekcorp.fooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vivekcorp.fooddonation.models.UserInfoModel;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText email, name ,phoneNo,password;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        phoneNo = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextTextPassword);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

//        if(fAuth.getCurrentUser() != null){
//            Intent mainIntent = new Intent(this  , HomeActivity.class);
//            startActivity(mainIntent);
//            finish();
//        }
    }

    public void GoLoginPage(View view) {
        Intent mainIntent = new Intent(this  , LoginActivity.class);
        startActivity(mainIntent);
    }

    public void Register(View view) {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String nameText = name.getText().toString();
        String phoneText = phoneNo.getText().toString();

        if(TextUtils.isEmpty(emailText)){
            email.setError("Email is required");
        }
        if(TextUtils.isEmpty(passwordText)){
            password.setError("Password is required");
        }
        if(passwordText.length() < 6){
            password.setError("Password must be 6 character long");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        fAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);

                if(task.isSuccessful()){

                    UserInfoModel userInfoModel = new UserInfoModel(emailText,nameText,phoneText);

                    reference.child(Objects.requireNonNull(fAuth.getUid())).setValue(userInfoModel);
                    Toast.makeText(RegisterActivity.this,"User created.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this, "Error!"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}