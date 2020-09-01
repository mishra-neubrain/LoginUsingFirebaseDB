package com.example.loginusingfirebasedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText name, pass;
    private Button verify;

    private DatabaseReference databaseReference;

    String user_name, user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        name = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        verify = findViewById(R.id.login);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = name.getText().toString().trim();
                user_password = pass.getText().toString().trim();

                if (user_name.isEmpty() && user_password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Field Req", Toast.LENGTH_SHORT).show();
                } else {
                    verifyLogin();
                }
            }
        });

    }

    private void verifyLogin() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Uname = (String) dataSnapshot.child(user_name).child("name").getValue();
                String USecretkey = (String) dataSnapshot.child(user_name).child("key").getValue();

                if (user_name.equals(Uname) && user_password.equals(USecretkey)){
                    Toast.makeText(MainActivity.this, "Username: " +Uname+ " SecretKey: " +USecretkey,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Username or Secret key incorrect", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}