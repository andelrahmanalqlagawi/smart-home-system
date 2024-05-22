package com.example.samrthome0;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Led {
    FirebaseDatabase database;
    DatabaseReference reference;

    public Led() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Led");

    }

    public void turnOnLed(){
        reference.child("state").setValue("on");

    }

    public void turnOffLed(){
        reference.child("state").setValue("off");

    }

    public interface LedStateCallback {
        void onLedStateChange(String state);
    }

    public void readLedState(final LedStateCallback callback) {
        reference.child("state").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String state = dataSnapshot.getValue(String.class);
                    // Pass the LED state value through the callback
                    callback.onLedStateChange(state);
                } else {
                    // Handle the case where the "state" node does not exist
                    // For example, you can pass a default value or handle it accordingly
                    callback.onLedStateChange("off"); // Passing "off" as default state
                }
              //  return null;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors that may occur during the read operation
                // For example, you can log the error message
                System.out.println("Error reading LED state: " + databaseError.getMessage());
            }
        });
    }
}
