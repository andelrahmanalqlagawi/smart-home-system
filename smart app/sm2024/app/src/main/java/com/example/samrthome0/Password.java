package com.example.samrthome0;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Password {
    FirebaseDatabase database;
    DatabaseReference reference;

    public Password() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Password");

    }

    public void save_password(String password){
        reference.child("password").setValue(password);

    }
    public interface PasswordCallback {
        void onPasswordRead(String password);
    }
    public void read_password(final PasswordCallback callback) {
        reference.child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = dataSnapshot.getValue(String.class);
                    // Pass the password value through the callback
                    callback.onPasswordRead(password);
                } else {
                    // Handle the case where the "password" node does not exist
                    // For example, you can pass a default value or handle it accordingly
                    callback.onPasswordRead(""); // Passing an empty string as default value
                }
               // return null;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors that may occur during the read operation
                // For example, you can log the error message
                System.out.println("Error reading password data: " + databaseError.getMessage());
            }
        });
    }
}
