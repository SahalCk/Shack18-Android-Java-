package com.shack.andrahalli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminEmployeeAdapter extends RecyclerView.Adapter<AdminEmployeeAdapter.ViewHolder>{

    Context context;
    List<AdminViewEmpModel> adminViewEmpModelList;
    private EmpProfClicklistner listner;

    public AdminEmployeeAdapter(Context context, List<AdminViewEmpModel> adminViewEmpModelList, EmpProfClicklistner listner) {
        this.context = context;
        this.adminViewEmpModelList = adminViewEmpModelList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public AdminEmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_employees_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminEmployeeAdapter.ViewHolder holder, int position) {

        AdminViewEmpModel adminViewEmpModel = adminViewEmpModelList.get(position);
        holder.employeename.setText(""+adminViewEmpModel.getEmployeename());
        holder.employeeid.setText(""+adminViewEmpModel.getEmployeeid());
        holder.employeerole.setText(""+adminViewEmpModel.getEmployeerole());
        holder.employeephone.setText(""+adminViewEmpModel.getEmployeephone());

        String imageuri = null;
        imageuri = adminViewEmpModel.getUrl();
        Picasso.get().load(imageuri).into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return adminViewEmpModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageview;
        TextView employeename,employeeid,employeerole,employeephone;

        public ViewHolder(View itemview) {
            super(itemview);

            imageview = itemview.findViewById(R.id.imageview);
            employeename = itemview.findViewById(R.id.employeename);
            employeerole = itemview.findViewById(R.id.employeerole);
            employeeid = itemview.findViewById(R.id.employeeid);
            employeephone = itemview.findViewById(R.id.employeephone);
            itemview.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemview) {
           listner.Onclick(itemview,getAdapterPosition());
        }
    }

    public void filterlist(List<AdminViewEmpModel> filteredlist){
        adminViewEmpModelList = filteredlist;
        notifyDataSetChanged();
    }

    public interface EmpProfClicklistner{
        void Onclick(View v,int position);
    }
}
