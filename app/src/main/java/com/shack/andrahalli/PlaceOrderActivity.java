package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {
    TextView tablenumber,total;
    RecyclerView recyclerView;
    AppCompatButton placeorder;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    PlaceOrderMenuModel placeOrderMenuModel;
    PlaceOrderMenuAdapter placeOrderMenuAdapter;
    List<PlaceOrderMenuModel> placeOrderMenuModelList;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        tablenumber = findViewById(R.id.tablenumber);
        total = findViewById(R.id.totalamount);
        placeorder = findViewById(R.id.placeorder);
        String tablenum = "Tbl No";
        Bundle extras = getIntent().getExtras();
        tablenum = extras.getString("tablenumber");


        tablenumber.setText("Place Order for "+tablenum);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Food Menu");
        storageReference = FirebaseStorage.getInstance().getReference().child("Food Menu");
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        placeOrderMenuModelList = new ArrayList<PlaceOrderMenuModel>();
        placeOrderMenuAdapter = new PlaceOrderMenuAdapter(PlaceOrderActivity.this,placeOrderMenuModelList);
        recyclerView.setAdapter(placeOrderMenuAdapter);

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
                PlaceOrderMenuModel placeOrderMenuModel = datasnapshot.getValue(PlaceOrderMenuModel.class);
                placeOrderMenuModelList.add(placeOrderMenuModel);
                placeOrderMenuAdapter.notifyDataSetChanged();
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

        List<PlaceOrderMenuModel> filteredlist = new ArrayList<PlaceOrderMenuModel>();
        for (PlaceOrderMenuModel item : placeOrderMenuModelList){
            if (item.getItemname().toLowerCase().contains(text.toLowerCase())){
                filteredlist.add(item);
            }
        }

        placeOrderMenuAdapter.filterlist(filteredlist);

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("firsrorder");
            }
        });
    }
}
