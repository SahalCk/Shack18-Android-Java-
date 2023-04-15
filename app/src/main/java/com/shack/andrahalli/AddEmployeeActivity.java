package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddEmployeeActivity extends AppCompatActivity {

    private RadioGroup radiogruop;
    private RadioGroup radiogruop2;
    private RadioButton radiomaleorfemale,radioemprole;
    private RadioButton radiosouthindiancheff,radiomale;
    private String maleorfemale,employeerole;
    private ImageView imageView;
    private CardView uploadimage;
    private EditText employeename,employeeaddress,employeephone,employeeadhar,employeeid,employeepin,employeejoining,employeesalary;
    private Button addemployee;
    private int selectedid1, selectedid2 ;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    String downloadurl = "";
    private Bitmap bitmap;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        radiogruop = findViewById(R.id.Radiogruop);
        radiogruop2 = findViewById(R.id.Radiogruop2);
        radiomale = findViewById(R.id.radiomale);
        radiosouthindiancheff = findViewById(R.id.radiosouthindiancheff);
        imageView = findViewById(R.id.imageview);
        uploadimage = findViewById(R.id.uploadimage);
        employeename = findViewById(R.id.employeename);
        employeeaddress = findViewById(R.id.employeeaddress);
        employeephone = findViewById(R.id.employeephone);
        employeeadhar = findViewById(R.id.employeeadhar);
        employeejoining = findViewById(R.id.employeejoining);
        employeesalary = findViewById(R.id.employeesalary);
        employeeid = findViewById(R.id.employeeid);
        employeepin = findViewById(R.id.employeepin);
        addemployee = findViewById(R.id.addemployee);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        progressDialog = new ProgressDialog(this);

        if (ContextCompat.checkSelfPermission(AddEmployeeActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddEmployeeActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    101);
        }

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(AddEmployeeActivity.this)
                        .cropSquare()
                        .compress(512)
                        .maxResultSize(1080, 1080)
                        .start(10);
            }
        });

        employeejoining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEmployeeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month +1;
                        String date = day+"/"+month+"/"+year;
                        employeejoining.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        addemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Adding "+employeename.getText().toString()+" to Shack 18");
                progressDialog.setProgressStyle(R.style.alertdialogue);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (employeename.getText().toString().isEmpty() || employeeaddress.getText().toString().isEmpty()
                        || employeeadhar.getText().toString().isEmpty() || employeephone.getText().toString().isEmpty()
                        || employeeid.getText().toString().isEmpty() || employeepin.getText().toString().isEmpty()
                        || employeejoining.getText().toString().isEmpty() || employeesalary.getText().toString().isEmpty()) {
                    Toast.makeText(AddEmployeeActivity.this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else if (bitmap == null) {
                    Toast.makeText(AddEmployeeActivity.this, "Please add Employee Image", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if (selectedid1 == -1){
                    Toast.makeText(AddEmployeeActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if (selectedid2 == -1){
                    Toast.makeText(AddEmployeeActivity.this, "Please Select Employee Role", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {

                    getmaleorfemale();
                    getemployeerole();
                    addemployeetodatabase();
                }
            }
        });

    }

    private void clearallfields() {
        radiomale.setChecked(true);
        radiosouthindiancheff.setChecked(true);
        imageView.setImageResource(R.drawable.emppropic);
        employeename.setText("");
        employeeaddress.setText("");
        employeephone.setText("");
        employeeadhar.setText("");
        employeejoining.setText("");
        employeesalary.setText("");
        employeeid.setText("");
        employeepin.setText("");
    }

    private void getmaleorfemale() {
        selectedid1 = radiogruop.getCheckedRadioButtonId();
        radiomaleorfemale = (RadioButton) findViewById(selectedid1);
        maleorfemale = radiomaleorfemale.getText().toString();

    }

    private void getemployeerole() {
        selectedid2 = radiogruop2.getCheckedRadioButtonId();
        radioemprole = (RadioButton) findViewById(selectedid2);
        employeerole = radioemprole.getText().toString();
    }

    private void addemployeetodatabase() {
        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("Employees").child(finalImage+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(AddEmployeeActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(AddEmployeeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadData() {
        String  empname = employeename.getText().toString();
        String empaddress = employeeaddress.getText().toString();
        String empphone = employeephone.getText().toString();
        String empadhar = employeeadhar.getText().toString();
        String empjoining = employeejoining.getText().toString();
        String empsalary = employeesalary.getText().toString();
        String empid = employeeid.getText().toString();
        String emppin = employeepin.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Employees").child(empid);
        databaseReference.child("employeename").setValue(empname);
        databaseReference.child("employeeaddress").setValue(empaddress);
        databaseReference.child("employeephone").setValue(empphone);
        databaseReference.child("adharnumber").setValue(empadhar);
        databaseReference.child("joiningdate").setValue(empjoining);
        databaseReference.child("gender").setValue(maleorfemale);
        databaseReference.child("employeerole").setValue(employeerole);
        databaseReference.child("monthlysalary").setValue(empsalary);
        databaseReference.child("employeeid").setValue(empid);
        databaseReference.child("loginpin").setValue(emppin);
        databaseReference.child("url").setValue(downloadurl);
        Toast.makeText(this, empname+" Successfully Added to Shack18", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        clearallfields();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==10){
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}