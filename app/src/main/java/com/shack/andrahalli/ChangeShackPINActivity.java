package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeShackPINActivity extends AppCompatActivity {
    EditText newpin;
    Button updatepin;
    TextView currentpassword;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_shack_pinactivity);

        newpin = findViewById(R.id.newpin);
        updatepin = findViewById(R.id.updatepin);
        currentpassword = findViewById(R.id.currentpassword);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("shack18login");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentpassword.setText("Current Password is : "+dataSnapshot.child("Password").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                currentpassword.setText("Error");
            }
        });
        updatepin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatepin();
            }
        });
    }

    private void updatepin() {
        String newpinstr = newpin.getText().toString();
        if (newpinstr.isEmpty()){
            Toast.makeText(this, "Please Enter the new PIN", Toast.LENGTH_SHORT).show();
        }
        else
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("shack18login");
            databaseReference.child("Password").setValue(newpinstr);
            Toast.makeText(this, "New PIN has set", Toast.LENGTH_SHORT).show();
        }
    }
}