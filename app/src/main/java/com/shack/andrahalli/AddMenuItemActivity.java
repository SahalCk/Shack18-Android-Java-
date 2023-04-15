package com.shack.andrahalli;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

public class AddMenuItemActivity extends AppCompatActivity {
    private RadioGroup radiogruop;
    private RadioGroup radiogruop2;
    private RadioButton radiovegornonveg,radiofoodtype;
    private RadioButton radioveg,radiosouthindian;
    private String vegornonveg, fooditemtype;
    private ImageView imageView;
    private CardView uploadimage;
    private EditText itemname, itemprize;
    private Button additembutton;
    private Bitmap bitmap;
    private int selectedid1,selectedid2;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    String downloadurl = "";
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);

        radiogruop = findViewById(R.id.Radiogruop);
        radiogruop2 = findViewById(R.id.Radiogruop2);
        radioveg = findViewById(R.id.radioveg);
        radiosouthindian = findViewById(R.id.radiosouthindian);
        imageView = findViewById(R.id.imageview);
        uploadimage = findViewById(R.id.uploadimage);
        itemname = findViewById(R.id.itemname);
        itemprize = findViewById(R.id.itemprize);
        additembutton = findViewById(R.id.additembutton);
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        if (ContextCompat.checkSelfPermission(AddMenuItemActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddMenuItemActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    101);
        }


        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(AddMenuItemActivity.this)
                        .cropSquare()
                        .compress(512)
                        .maxResultSize(1080, 1080)
                        .start(10);
            }
        });


        additembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Adding "+itemname.getText().toString()+" to Food Menu");
                progressDialog.setProgressStyle(R.style.alertdialogue);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (itemname.getText().toString().isEmpty() || itemprize.getText().toString().isEmpty()) {
                    Toast.makeText(AddMenuItemActivity.this, "Please Enter item Name and item Prize", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (bitmap == null) {
                    Toast.makeText(AddMenuItemActivity.this, "Please add item Image", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if (selectedid1 == -1){
                    Toast.makeText(AddMenuItemActivity.this, "Please Select Veg or None-veg", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if (selectedid2 == -1){
                    Toast.makeText(AddMenuItemActivity.this, "Please Select food Type", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {

                    getvegornoneveg();
                    getfooditemtype();
                    additemtodatabase();
                }

            }
        });

    }

    private void getvegornoneveg() {
        selectedid1 = radiogruop.getCheckedRadioButtonId();
        radiovegornonveg = (RadioButton) findViewById(selectedid1);
        vegornonveg = radiovegornonveg.getText().toString();
    }

    private void getfooditemtype() {
        selectedid2 = radiogruop2.getCheckedRadioButtonId();
        radiofoodtype = (RadioButton) findViewById(selectedid2);
        fooditemtype = radiofoodtype.getText().toString();
    }


    private void additemtodatabase() {
        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("Food Menu").child(finalImage+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(AddMenuItemActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(AddMenuItemActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void uploadData() {
        String  itemnamestr = itemname.getText().toString();
        String itemprizestr = itemprize.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("shack18").child("Food Menu").child(itemnamestr);
        databaseReference.child("itemname").setValue(itemnamestr);
        databaseReference.child("itemprize").setValue(itemprizestr);
        databaseReference.child("vegornonveg").setValue(vegornonveg);
        databaseReference.child("foodtype").setValue(fooditemtype);
        databaseReference.child("url").setValue(downloadurl);
        Toast.makeText(this, itemnamestr+" Added Successfully", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        clearall();
    }

    private void clearall() {
        itemname.setText("");
        itemprize.setText("");
        radioveg.setChecked(true);
        radiosouthindian.setChecked(true);
        imageView.setImageResource(R.drawable.addimage);
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