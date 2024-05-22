package com.example.samrthome0;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FanMotor {
    FirebaseDatabase database;
    DatabaseReference reference;

    public FanMotor() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("FanMotor");

    }

    public void turnOnFanMotor(){
        reference.child("state").setValue("on");

    }

    public void turnOffFanMotor(){
        reference.child("state").setValue("off");

    }

    public interface FanMotorStateCallback {
        void onFanMotorStateChange(String state);
    }

    public void readFanMotorState(final FanMotorStateCallback callback) {
        reference.child("state").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String state = dataSnapshot.getValue(String.class);
                    // Pass the fan motor state value through the callback
                    callback.onFanMotorStateChange(state);
                } else {
                    // Handle the case where the "state" node does not exist
                    // For example, you can pass a default value or handle it accordingly
                    callback.onFanMotorStateChange("off"); // Passing "off" as default state
                }
                //return null;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors that may occur during the read operation
                // For example, you can log the error message
                System.out.println("Error reading fan motor state: " + databaseError.getMessage());
            }
        });
    }
}
