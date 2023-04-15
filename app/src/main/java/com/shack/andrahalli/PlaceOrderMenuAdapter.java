package com.shack.andrahalli;

import android.content.Context;
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

public class PlaceOrderMenuAdapter extends RecyclerView.Adapter<PlaceOrderMenuAdapter.ViewHolder> {

    Context context;
    List<PlaceOrderMenuModel> placeOrderMenuModelList;
    int count =0;

    public PlaceOrderMenuAdapter(Context context, List<PlaceOrderMenuModel> placeOrderMenuModelList) {
        this.context = context;
        this.placeOrderMenuModelList = placeOrderMenuModelList;
    }

    @NonNull
    @Override
    public PlaceOrderMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.placeordermenulayout,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceOrderMenuAdapter.ViewHolder holder, int position) {

        PlaceOrderMenuModel placeOrderMenuModel = placeOrderMenuModelList.get(position);
        holder.foodname.setText(""+placeOrderMenuModel.getItemname());
        holder.foodprize.setText("₹ "+placeOrderMenuModel.getItemprize());
        holder.foodtype.setText(""+placeOrderMenuModel.getFoodtype());
        holder.vegornonvegtxt.setText(""+placeOrderMenuModel.getVegornonveg());

        String imageuri = null;
        imageuri = placeOrderMenuModel.getUrl();
        Picasso.get().load(imageuri).into(holder.foodimage);

        if("Veg".equals(placeOrderMenuModel.getVegornonveg())){
            holder.vegornonvegimg.setImageResource(R.drawable.vegsymbol);
        }else {
            holder.vegornonvegimg.setImageResource(R.drawable.nonvegsymbol);
        }


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count+1;
                holder.counttext.setText(""+count);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count - 1;
                if (count < 0) {
                    count = 0;
                }else {
                    holder.counttext.setText("" + count);
                }
                }
        });

    }

    @Override
    public int getItemCount() {
        return placeOrderMenuModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodimage,vegornonvegimg,plus,minus;
        TextView foodname,foodprize,foodtype,vegornonvegtxt,counttext;
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
            counttext = itemview.findViewById(R.id.count);
            plus = itemview.findViewById(R.id.plus);
            minus = itemview.findViewById(R.id.minus);
        }
    }
    public void filterlist(List<PlaceOrderMenuModel> filteredlist){
        placeOrderMenuModelList = filteredlist;
        notifyDataSetChanged();
    }

}

