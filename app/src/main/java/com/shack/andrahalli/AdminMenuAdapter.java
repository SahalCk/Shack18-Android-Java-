package com.shack.andrahalli;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminMenuAdapter extends RecyclerView.Adapter<AdminMenuAdapter.ViewHolder> {

    Context context;
    List<AdminFoodMenuModel> foodMenuModellist;

    public AdminMenuAdapter(Context context, List<AdminFoodMenuModel> foodMenuModellist) {
        this.context = context;
        this.foodMenuModellist = foodMenuModellist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_foodmenu_layout,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AdminFoodMenuModel adminFoodMenuModel = foodMenuModellist.get(position);
        holder.foodname.setText(""+adminFoodMenuModel.getItemname());
        holder.foodprize.setText("₹ "+adminFoodMenuModel.getItemprize());
        holder.foodtype.setText(""+adminFoodMenuModel.getFoodtype());
        holder.vegornonvegtxt.setText(""+adminFoodMenuModel.getVegornonveg());

        String imageuri = null;
        imageuri = adminFoodMenuModel.getUrl();
        Picasso.get().load(imageuri).into(holder.foodimage);

        if("Veg".equals(adminFoodMenuModel.getVegornonveg())){
            holder.vegornonvegimg.setImageResource(R.drawable.vegsymbol);
        }else {
            holder.vegornonvegimg.setImageResource(R.drawable.nonvegsymbol);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Food Menu");
               databaseReference.child(adminFoodMenuModel.getItemname()).removeValue();
                notifyDataSetChanged();
                Toast.makeText(context, adminFoodMenuModel.getItemname()+" is Removed From Menu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodMenuModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodimage,vegornonvegimg;
        TextView foodname,foodprize,foodtype,vegornonvegtxt;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemview) {
            super(itemview);

            foodimage = itemview.findViewById(R.id.foodimage);
            vegornonvegimg = itemview.findViewById(R.id.vegornonvegimg);
            foodname = itemview.findViewById(R.id.foodname);
            foodprize = itemview.findViewById(R.id.foodprize);
            foodtype = itemview.findViewById(R.id.foodtype);
            vegornonvegtxt = itemview.findViewById(R.id.vegornonvegtxt);
            relativeLayout = itemview.findViewById(R.id.relativelayout);
        }
    }

    public void filterlist(List<AdminFoodMenuModel> filteredlist){
        foodMenuModellist = filteredlist;
        notifyDataSetChanged();
    }
}
