package com.example.samrthome0;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Setpassword extends AppCompatActivity {

    EditText Password;
    Button Btn;
    Password passObj = new Password();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setpassword);

        Password = findViewById(R.id.PasswordEdit);
        Btn = findViewById(R.id.buttonsavepass);
        Btn.setOnClickListener(v -> {
                    String password = Password.getText().toString().trim();

                    // Input validation (example)
                    if (TextUtils.isEmpty(password) || password.length() < 4) {
                        Toast.makeText(Setpassword.this, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try
                    {
                        passObj.save_password(password);
                        Toast.makeText(Setpassword.this, "Password saved successfully", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(Setpassword.this, "Error saving password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

        });

                    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

//            savePasswordFromEditText();

            return insets;
        });
//        Btn.setOnClickListener(v -> savePasswordFromEditText());
    }
//    private void savePasswordFromEditText() {
//
//        String pass = Password.getText().toString().trim();
//        passObj.save_password(pass);
//    }
}