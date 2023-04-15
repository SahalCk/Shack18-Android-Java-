package com.shack.andrahalli;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    public TableClickListner listner;

    public RecyclerViewAdapter(int[] arr, String[]tablearr ,TableClickListner listner) {
        this.arr = arr;
        this.tablearr = tablearr;
        this.listner = listner;
    }

    int[] arr;
    String[] tablearr;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_waiter_table, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tableimage.setImageResource(arr[position]);

        holder.tablenumber.setText(tablearr[position]);

        holder.tablestatus.setText("Free");
    }


    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView tableimage;
        TextView tablenumber;
        TextView tablestatus;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tableimage = itemView.findViewById(R.id.tableimage);
            tablenumber = itemView.findViewById(R.id.tablenumber);
            tablestatus = itemView.findViewById(R.id.tablestatus);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
           listner.onClick(itemView,getAdapterPosition());
        }
    }

    public interface TableClickListner{
        void onClick(View v,int position);
    }
}