package com.example.samrthome0;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tempView extends AppCompatActivity {
    int tep;
    EditText Temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temp_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            Temp=findViewById(R.id.TemperatureEdittxt);
            //String temp = Temp.getText().toString().trim();
            return insets;
        });
        getTemperature(this);
    }
    public void getTemperature(Context context) {
        tempreture tempObj = new tempreture();

        tempObj.read_temperature(context, new tempreture.TemperatureCallback() {
            @Override
            public void onTemperatureRead(double temperature) {
                int tempInt = (int) temperature;
                tep = tempInt;
                // Update the EditText with the temperature value
                runOnUiThread(() -> Temp.setText(String.valueOf(tep)));
            }
        });
    }
}