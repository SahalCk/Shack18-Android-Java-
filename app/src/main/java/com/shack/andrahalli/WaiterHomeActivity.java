
package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class WaiterHomeActivity extends AppCompatActivity{
    String empid;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ImageView imageview;
    TextView employeename;
    CardView viewprofile,placeneworder,vieworderstatus,viewmycontribution,quit;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Exiting from Shack 18", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_home);

        imageview = findViewById(R.id.imageview);
        employeename = findViewById(R.id.employeename);
        viewprofile = findViewById(R.id.viewprofile);
        placeneworder = findViewById(R.id.placeneworder);
        vieworderstatus = findViewById(R.id.vieworderstatus);
        viewmycontribution = findViewById(R.id.viewmycontribution);
        quit = findViewById(R.id.quit);



        empid = "employeeid";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            empid = extras.getString("employeeid");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Employees");
        storageReference = FirebaseStorage.getInstance().getReference().child("Employees");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                employeename.setText(datasnapshot.child(empid).child("employeename").getValue().toString());
                String imageuri = null;
                imageuri = datasnapshot.child(empid).child("url").getValue().toString();
                Picasso.get().load(imageuri).into(imageview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaiterHomeActivity.this,ProfileViewActivity.class);
                intent.putExtra("employeeid",empid);
                startActivity(intent);
            }
        });
        placeneworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaiterHomeActivity.this,WaiterActivity.class);
                startActivity(intent);
            }
        });

    }
}