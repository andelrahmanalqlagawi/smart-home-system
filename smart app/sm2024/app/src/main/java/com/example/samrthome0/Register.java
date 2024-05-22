package com.example.samrthome0;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.UUID;

public class Register extends AppCompatActivity {
    ImageView imgView;
    /*File Resources: Uri can be used to represent file paths or file content that your
     application wants to access or interact with, including images, videos, audio files.*/
     Uri imageUri;
     FirebaseStorage storage;
     StorageReference storageReference;
     FirebaseDatabase  fireDatabase;
     DatabaseReference reference;
     DBhelperREG Addusers ;
     String imgeurl = "no image";
    public  HelperReg Helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);



            imgView = findViewById(R.id.imageViewPU);

            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePicture();
                }
            });

            EditText name = findViewById(R.id.NAme);
            EditText username = findViewById(R.id.USerame);
            EditText password = findViewById(R.id.PAssword);
            EditText con_password = findViewById(R.id.COmfrimpass);
            EditText email = findViewById(R.id.Email);
            EditText brth_edit = findViewById(R.id.BArthdate);
            Button REgister = findViewById(R.id.REgisterr);

            REgister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Addusers = new DBhelperREG(Register.this);
                    fireDatabase = FirebaseDatabase.getInstance();
                    reference = fireDatabase.getReference("users");
                    String Name = name.getText().toString().trim();
                    String Username = username.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    String CON_password = con_password.getText().toString().trim();
                    String Email = email.getText().toString().trim();
                    String Brth = brth_edit.getText().toString().trim();
                    String img = imgeurl;

                    Helper = new HelperReg(Name, Username, Password, CON_password, Email, Brth,img);
                    reference.child(Name).setValue(Helper);
                    Addusers.CreatNewUser(Name,Username,Password,CON_password,Email,Brth);


                    if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Username) || TextUtils.isEmpty(Password)
                            || TextUtils.isEmpty(CON_password) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Brth)) {
                        Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                    }
                    else if (!Password.equals(CON_password)) {
                        Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                    }
                    else startActivity(new Intent(Register.this, Login.class));

                }
            });
            ImageButton brth_button = findViewById(R.id.imageButton);
            brth_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            Register.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month + 1;

                            String date = month + "/" + day + "/" + year;
                            brth_edit.setText(date);
                        }
                    },
                            year, month, day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            TextView have_ac = findViewById(R.id.HAve_acc);
            have_ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Register.this, Login.class));

                }
            });

            return insets;
        });

    }

    private void choosePicture() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
                /*This method is used to start a new activity and expect a
                 result back when the newly started activity finishes.
                 It's commonly used when you want to start a sub-activity and
                 receive some data or feedback from it when it completes.
                 */
        startActivityForResult(i, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {
           /* used to pass data between different components within an Android
           application using Intent objects.*/
            imageUri = data.getData();
            imgView.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        // Create a ProgressDialog to show the progress of the upload
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        // Generate a random key for the image file
        final String randomKey = UUID.randomUUID().toString();

        // Reference to the image file in Firebase Storage
        StorageReference imageRef = storageReference.child("images/" + randomKey);

        // Start the upload process
        imageRef.putFile(imageUri)

                .addOnSuccessListener(taskSnapshot -> {
                    // Dismiss the ProgressDialog when the upload is successful
                    pd.dismiss();
                    Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();

                    // Retrieve the download URL of the uploaded image
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // You can use the download URL as needed
                        imgeurl = uri.toString();
                        // Display or use the URL as needed

                    Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();


                    }).addOnFailureListener(e -> {
                        pd.dismiss();
                        // Handle failure to get the download URL
                        Toast.makeText(getApplicationContext(), "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                })
                .addOnFailureListener(e -> {
                    // Dismiss the ProgressDialog and show an error message if the upload fails
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                })
                .addOnProgressListener(snapshot -> {
                    // Update the ProgressDialog with the current progress of the upload
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("Percentage: " + (int) progressPercent + "%");
                });
    }

    }

























