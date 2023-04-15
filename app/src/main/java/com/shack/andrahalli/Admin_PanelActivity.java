package com.shack.andrahalli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin_PanelActivity extends AppCompatActivity {
    CardView changeadminpass,changeshackpin,viewmenu,additemtomenu,viewemployees,addemployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        changeshackpin = findViewById(R.id.changeshackpin);
        changeadminpass = findViewById(R.id.changeadminpass);
        viewmenu = findViewById(R.id.viewmenu);
        additemtomenu = findViewById(R.id.additemtomenu);
        viewemployees = findViewById(R.id.viewemployees);
        addemployee = findViewById(R.id.addemployee);


        changeshackpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_PanelActivity.this,ChangeShackPINActivity.class);
                startActivity(intent);
            }
        });
        changeadminpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_PanelActivity.this,ChangeAdminPassActivity.class);
                startActivity(intent);
            }
        });
        additemtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_PanelActivity.this,AddMenuItemActivity.class);
                startActivity(intent);
            }
        });
        addemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_PanelActivity.this,AddEmployeeActivity.class);
                startActivity(intent);
            }
        });
        viewmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_PanelActivity.this,ViewFoodMenuAdminActivity.class);
                startActivity(intent);
            }
        });
        viewemployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_PanelActivity.this,ViewEmployeesAdminActivity.class);
                startActivity(intent);
            }
        });
    }
}