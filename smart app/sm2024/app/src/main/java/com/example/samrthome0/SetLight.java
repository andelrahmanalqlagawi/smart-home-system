package com.example.samrthome0;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SetLight extends AppCompatActivity {
    Button  Btnon,Btnoff;
    Led ObjLed = new Led();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_light);

        Btnon = findViewById(R.id.BtnlightnON);
        Btnoff = findViewById(R.id.BtnlightnOFF);

        Btnon.setOnClickListener(v -> {
            ObjLed.turnOnLed();
        });
        Btnoff.setOnClickListener(v -> {
            ObjLed.turnOffLed();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}