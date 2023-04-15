package com.shack.andrahalli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class WaiterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerViewAdapter.TableClickListner listner;


    int []arr ={R.drawable.dinningtable,R.drawable.dinningtable,R.drawable.dinningtable,R.drawable.dinningtable,
            R.drawable.diningtable2people,R.drawable.diningtable2people,R.drawable.diningtable2people,R.drawable.diningtable2people,
            R.drawable.diningtableoutdoor,R.drawable.diningtableoutdoor};
    String[] tablearr ={"Table 1","Table 2","Table 3","Table 4","Table 5","Table 6","Table 7","Table 8","Table 9","Table 10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);

        recyclerView = findViewById(R.id.recyclerview);
        setOnClickListner();
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(arr,tablearr,listner);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

    }

    private void setOnClickListner() {
        listner = new RecyclerViewAdapter.TableClickListner() {
            @Override
            public void onClick(View v, int position) {
               Intent intent = new Intent(getApplicationContext(),PlaceOrderActivity.class);
               intent.putExtra("tablenumber",tablearr[position]);
               startActivity(intent);
            }
        };
    }
}