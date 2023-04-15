package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {

    TextView username,password;
    Button loginbutton;
    LoadingDialogue loadingDialogue;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbutton = findViewById(R.id.loginbutton);
        loadingDialogue = new LoadingDialogue(AdminActivity.this);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialogue.startLoadingDialog();
                adminlogin();
            }
        });
    }

    private void adminlogin(){

        String struser = username.getText().toString();
        String strpass = password.getText().toString();

        if (strpass.isEmpty() || struser.isEmpty()){
            Toast.makeText(this, "Please Enter User name and Password", Toast.LENGTH_SHORT).show();
            loadingDialogue.dismissDialog();
        }
        else{
            databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("adminlogin");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (struser.equals(dataSnapshot.child("Username").getValue()) && strpass.equals(dataSnapshot.child("Password").getValue()))
                    {
                        Toast.makeText(AdminActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent success = new Intent(AdminActivity.this, Admin_PanelActivity.class);
                        startActivity(success);
                        loadingDialogue.dismissDialog();
                    }
                    else{
                        Toast.makeText(AdminActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        password.setText("");
                        loadingDialogue.dismissDialog();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(AdminActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            });
        }

    }
}