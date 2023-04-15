package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ViewFoodMenuAdminActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    AdminMenuAdapter adminMenuAdapter;
    List<AdminFoodMenuModel> adminFoodMenuModelList;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_menu_admin);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Food Menu");
        storageReference = FirebaseStorage.getInstance().getReference().child("Food Menu");
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adminFoodMenuModelList = new ArrayList<AdminFoodMenuModel>();
        adminMenuAdapter = new AdminMenuAdapter(ViewFoodMenuAdminActivity.this,adminFoodMenuModelList);
        recyclerView.setAdapter(adminMenuAdapter);

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
                AdminFoodMenuModel adminFoodMenuModel = datasnapshot.getValue(AdminFoodMenuModel.class);
                adminFoodMenuModelList.add(adminFoodMenuModel);
                adminMenuAdapter.notifyDataSetChanged();
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

    private void filter(String text) {

        List<AdminFoodMenuModel> filteredlist = new ArrayList<AdminFoodMenuModel>();
        for (AdminFoodMenuModel item : adminFoodMenuModelList){
            if (item.getItemname().toLowerCase().contains(text.toLowerCase())){
               filteredlist.add(item);
            }
        }

        adminMenuAdapter.filterlist(filteredlist);
    }
}