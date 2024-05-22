package com.example.samrthome0;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class forgetpassword extends AppCompatActivity {

    EditText Newpass;
    EditText Reconfirm_NewPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgetpassword);


        Newpass = findViewById(R.id.Newpass);
        Reconfirm_NewPass = findViewById(R.id.Reconfirm_NewPass) ;

    }
}