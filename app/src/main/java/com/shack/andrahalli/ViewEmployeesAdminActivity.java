package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewEmployeesAdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    AdminEmployeeAdapter adminEmployeeAdapter;
    List<AdminViewEmpModel> adminViewEmpModelList;
    EditText search;
    private AdminEmployeeAdapter.EmpProfClicklistner listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employees_admin);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Employees");
        storageReference = FirebaseStorage.getInstance().getReference().child("Employees");
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        setOnClickListner();
        adminViewEmpModelList = new ArrayList<AdminViewEmpModel>();
        adminEmployeeAdapter = new AdminEmployeeAdapter(ViewEmployeesAdminActivity.this,adminViewEmpModelList,listner);
        recyclerView.setAdapter(adminEmployeeAdapter);
        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot datasnapshot, @Nullable String previousChildName) {
                AdminViewEmpModel adminViewEmpModel = datasnapshot.getValue(AdminViewEmpModel.class);
                adminViewEmpModelList.add(adminViewEmpModel);
                adminEmployeeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setOnClickListner() {
        listner = new AdminEmployeeAdapter.EmpProfClicklistner() {
            @Override
            public void Onclick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(),EmpProfViewActivity.class);
                intent.putExtra("employeeid",adminViewEmpModelList.get(position).employeeid);
                startActivity(intent);
            }
        };
    }

    private void filter(String text) {

        List<AdminViewEmpModel> filteredlist = new ArrayList<AdminViewEmpModel>();
        for (AdminViewEmpModel item : adminViewEmpModelList){
            if (item.getEmployeename().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item);
            }
        }

        adminEmployeeAdapter.filterlist(filteredlist);
    }
}