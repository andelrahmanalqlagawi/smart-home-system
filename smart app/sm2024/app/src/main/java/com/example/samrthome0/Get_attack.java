package com.example.samrthome0;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseError;

public class Get_attack extends AppCompatActivity {
    private static final String TAG = "Get_attack";
    boolean IsUnderattack;
    EditText attk;
    Button checkButton;
    Attack attack = new Attack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_attack);

        // Initialize views
        attk = findViewById(R.id.AttackEdittxt);
        checkButton = findViewById(R.id.buttonAttack);

        if (attk == null || checkButton == null) {
            Log.e(TAG, "Failed to initialize views. Make sure your layout file has the correct IDs.");
            Toast.makeText(this, "Initialization error. Please check log for details.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set onClickListener for the button
        checkButton.setOnClickListener(v -> {
            Log.d(TAG, "Button clicked. Checking attack status.");
            if (IsUnderattack) {
                attk.setText("We Are Under Attack!");
            } else {
                attk.setText("System is Secure");
            }
        });

        // Check alert state
        checkAlertState();

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void checkAlertState() {
        attack.listenForAlertChanges(new Attack.AlertListener() {
            @Override
            public void onAlertChanged(boolean isAlert) {
                IsUnderattack = isAlert;
                Log.d(TAG, "Alert state changed: " + isAlert);
                // Update EditText based on the alert state
                runOnUiThread(() -> {
                    if (IsUnderattack) {
                        attk.setText("We Are Under Attack!");
                        Toast.makeText(Get_attack.this, "We Are Under Attack saved ", Toast.LENGTH_SHORT).show();
                    } else {
                        attk.setText("System is Secure");
                        Toast.makeText(Get_attack.this, "We Are Secure ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read alert state: " + databaseError.getMessage());
                Toast.makeText(Get_attack.this, "Failed to read alert state. Please check log for details.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
