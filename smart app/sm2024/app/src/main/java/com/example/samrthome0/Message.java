package com.example.samrthome0;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Message {

    private FirebaseDatabase database;
    private DatabaseReference messageRef;

    public Message() {
        database = FirebaseDatabase.getInstance();
        messageRef = database.getReference("messages");
    }

    // Method to save a message to the cloud
    public void saveMessage(String message) {
        // Generate a unique key for each message using push()
        DatabaseReference newMessageRef = messageRef.push();
        newMessageRef.setValue(message);
    }

    // Method to listen for new messages on the cloud
    public interface MessageListener {
        void onMessageReceived(String message);
    }

    public void listenForMessages(final MessageListener listener) {
        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String message = dataSnapshot.getValue(String.class);
                listener.onMessageReceived(message);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle child changed event if needed
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Handle child removed event if needed
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle child moved event if needed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
