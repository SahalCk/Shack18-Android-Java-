package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements FeedbackDialogue.feedbackdialoguelister{
    EditText employeeid,password;
    AppCompatButton loginbtn;
    LoadingDialogue loadingDialogue;
    DatabaseReference databaseReference;
    FloatingActionButton adminpanel,writefeedback,contactadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        employeeid = findViewById(R.id.employeeid);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbutton);
        loadingDialogue = new LoadingDialogue(LoginActivity.this);

        adminpanel = findViewById(R.id.adminpanel);
        writefeedback = findViewById(R.id.writefeedback);
        contactadmin = findViewById(R.id.contactadmin);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialogue.startLoadingDialog();
                login();
            }
        });

        contactadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = "918129911688";
                String message = "Hi Sahal";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send/?phone="+number+
                        "&text="+message));
                startActivity(i);
            }
        });

        adminpanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminactivity = new Intent(LoginActivity.this,AdminActivity.class);
                startActivity(adminactivity);
            }
        });
        writefeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendfeedback();
            }
        });

    }

    private void sendfeedback() {
        FeedbackDialogue feedbackDialogue = new FeedbackDialogue();
        feedbackDialogue.show(getSupportFragmentManager(),"Send Feedback");
    }

    @Override
    public void applyTexts(String subject, String feedback) {
        //this is the subject and feedback given by user
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+"sahalck58@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,feedback);
        startActivity(intent);
    }


    private void login() {
        String strid = employeeid.getText().toString();
        String strpass = password.getText().toString();
        if (strpass.isEmpty()){
            loadingDialogue.dismissDialog();
            Toast.makeText(this, "Please Enter the Login PIN", Toast.LENGTH_SHORT).show();
        }
        else
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Employees");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(strid)){
                        final String getpassword = snapshot.child(strid).child("loginpin").getValue(String.class);
                        if (getpassword.equals(strpass)) {
                            final String role = snapshot.child(strid).child("employeerole").getValue(String.class);

                            if (role.equals("South Indian Cheff") || role.equals("North Indian Cheff") ||
                                    role.equals("Chinese Cheff") || role.equals("Juicer")) {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent success = new Intent(LoginActivity.this, CookHomeActivity.class);
                                success.putExtra("employeeid",strid);
                                startActivity(success);
                                finish();
                            } else if (role.equals("Waiter")) {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent success = new Intent(LoginActivity.this, WaiterHomeActivity.class);
                                success.putExtra("employeeid",strid);
                                startActivity(success);
                                finish();
                            } else if (role.equals("Cashier")) {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent success = new Intent(LoginActivity.this, CashierHomeActivity.class);
                                success.putExtra("employeeid",strid);
                                startActivity(success);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Dashboard is not Available for this Role", Toast.LENGTH_SHORT).show();
                                loadingDialogue.dismissDialog();
                                password.setText("");
                            }

                        }else{
                                Toast.makeText(LoginActivity.this, "Wrong PIN", Toast.LENGTH_SHORT).show();
                                loadingDialogue.dismissDialog();
                                password.setText("");
                            }
                        }else{
                        Toast.makeText(LoginActivity.this, "Employee does not Exist", Toast.LENGTH_SHORT).show();
                        loadingDialogue.dismissDialog();
                        employeeid.setText("");
                        password.setText("");
                    }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}