package com.example.samrthome0;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tempreture {
    FirebaseDatabase database;
    DatabaseReference reference;

    public tempreture() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Tempreture");

    }

    public void save_temperture(double temp){
        reference.child("temp").setValue(temp);

    }
    public interface TemperatureCallback {
        void onTemperatureRead(double temperature);
    }
    public void read_temperature(Context context, final TemperatureCallback callback) {
        reference.child("temp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    double temperature = dataSnapshot.getValue(Double.class);
                    // Pass the temperature value through the callback
                    callback.onTemperatureRead(temperature);
                } else {
                    // Handle the case where the "temp" node does not exist
                    // For example, you can pass a default value or handle it accordingly
                    callback.onTemperatureRead(0.0); // Passing a default value of 0.0
                }
              //  return null;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors that may occur during the read operation
                // For example, you can log the error message
                System.out.println("Error reading temperature data: " + databaseError.getMessage());
            }
        });
    }}