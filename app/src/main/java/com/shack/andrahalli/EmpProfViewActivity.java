package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class EmpProfViewActivity extends AppCompatActivity {
    ImageView imageView;
    TextView employeename,employeeaddress,employeephone,employeeadhar,employeejoining,employeegender,employeerole,
            employeesalary,employeeid,employeepin;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String empid;
    AppCompatButton buttondelete,buttoncall;
    String  number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_prof_view);

        imageView = findViewById(R.id.imageview);
        employeename = findViewById(R.id.employeename);
        employeeaddress = findViewById(R.id.employeeaddress);
        employeephone = findViewById(R.id.employeephone);
        employeeadhar = findViewById(R.id.employeeadhar);
        employeejoining = findViewById(R.id.employeejoining);
        employeegender = findViewById(R.id.employeegender);
        employeerole = findViewById(R.id.employeerole);
        employeesalary = findViewById(R.id.employeesalary);
        employeeid = findViewById(R.id.employeeid);
        employeepin = findViewById(R.id.employeepin);
        buttondelete = findViewById(R.id.buttondelete);
        buttoncall = findViewById(R.id.buttoncall);

        empid = "employeeid";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            empid = extras.getString("employeeid");
        }

        employeeid.setText(empid);

        if (ContextCompat.checkSelfPermission(EmpProfViewActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EmpProfViewActivity.this,
                    new String[]{
                            Manifest.permission.CALL_PHONE
                    },
                    100);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Employees");
        storageReference = FirebaseStorage.getInstance().getReference().child("Employees");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                employeename.setText(datasnapshot.child(empid).child("employeename").getValue().toString());
                employeeaddress.setText(datasnapshot.child(empid).child("employeeaddress").getValue().toString());
                employeephone.setText(datasnapshot.child(empid).child("employeephone").getValue().toString());
                employeeadhar.setText(datasnapshot.child(empid).child("adharnumber").getValue().toString());
                employeejoining.setText(datasnapshot.child(empid).child("joiningdate").getValue().toString());
                employeegender.setText(datasnapshot.child(empid).child("gender").getValue().toString());
                employeerole.setText(datasnapshot.child(empid).child("employeerole").getValue().toString());
                employeesalary.setText("₹ "+datasnapshot.child(empid).child("monthlysalary").getValue().toString());
                employeepin.setText(datasnapshot.child(empid).child("loginpin").getValue().toString());
                String imageuri = null;
                imageuri = datasnapshot.child(empid).child("url").getValue().toString();
                Picasso.get().load(imageuri).into(imageView);
                number = datasnapshot.child(empid).child("employeephone").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttoncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(empid).removeValue();
                EmpProfViewActivity.super.onBackPressed();
                Toast.makeText(EmpProfViewActivity.this, "Removed Successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}