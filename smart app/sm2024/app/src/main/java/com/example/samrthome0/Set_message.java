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

public class Set_message extends AppCompatActivity {
    EditText message;
    Button Btn;
    Message mes = new Message();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_message);


        message = findViewById(R.id.MessageEdittxt);
        Btn = findViewById(R.id.buttonsavemessage);
        Btn.setOnClickListener(v -> {
            String messagetxt = message.getText().toString().trim();

            // Input validation (example)
            if (TextUtils.isEmpty(messagetxt) || messagetxt.length() < 4) {
                Toast.makeText(Set_message.this, "Message must be at least 4 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            try
            {
                mes.saveMessage(messagetxt);
                Toast.makeText(Set_message.this, "Message saved successfully", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                Toast.makeText(Set_message.this, "Error saving password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });











        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}