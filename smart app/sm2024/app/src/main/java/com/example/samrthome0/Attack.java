package com.example.samrthome0;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Attack {
    private FirebaseDatabase database;
    private DatabaseReference alertRef;

    public Attack() {
        database = FirebaseDatabase.getInstance();
        alertRef = database.getReference("alert");
    }

    // Method to set the alert variable to true in the cloud
    public void setAlert(boolean isAlert) {
        alertRef.child("state").setValue(isAlert);
    }

    // Method to listen for changes in the alert variable on the Arduino side
    public interface AlertListener {
        void onAlertChanged(boolean isAlert);
        void onCancelled(DatabaseError databaseError);
    }

    public void listenForAlertChanges(final AlertListener listener) {
        alertRef.child("state").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Boolean isAlert = dataSnapshot.getValue(Boolean.class);
                    if (isAlert != null) {
                        listener.onAlertChanged(isAlert);
                    } else {
                        listener.onAlertChanged(false); // Default to false if null
                    }
                } else {
                    listener.onAlertChanged(false); // Default to false if the node doesn't exist
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancelled(databaseError);
            }
        });
    }
}
