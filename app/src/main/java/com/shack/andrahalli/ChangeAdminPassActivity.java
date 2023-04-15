package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ChangeAdminPassActivity extends AppCompatActivity {
    EditText newpassword;
    Button updatepass;
    TextView currentpassword;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_admin_pass);

        newpassword = findViewById(R.id.newpassword);
        updatepass = findViewById(R.id.updatepassword);
        currentpassword = findViewById(R.id.currentpassword);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("adminlogin");
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

        updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatepassword();
            }
        });
    }

    private void updatepassword() {

        String newpassstr = newpassword.getText().toString();
        if (newpassstr.isEmpty()){
            Toast.makeText(this, "Please Enter the new Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("adminlogin");
            databaseReference.child("Password").setValue(newpassstr);
            Toast.makeText(this, "New Password has set", Toast.LENGTH_SHORT).show();
        }
    }
}