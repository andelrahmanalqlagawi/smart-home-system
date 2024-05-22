package com.example.samrthome0;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HelperReg {
    String name,username,password,ConfirmPasss,email,birth_DAte,imgeurl;

    public HelperReg() {

    }

    public HelperReg(String name, String username, String password, String confirmPasss, String email, String birth_DAte, String imgeurl) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.ConfirmPasss = confirmPasss;
        this.email = email;
        this.birth_DAte = birth_DAte;
        this.imgeurl = imgeurl;
    }


    public interface GetImageUrlCallback {
        void onImageUrlRetrieved(String imageUrl);
        void onImageUrlError(String errorMessage);
    }

    public String geturl(final String username, final GetImageUrlCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        ValueEventListener listener = usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    if (userSnapshot.child("username").getValue(String.class).equals(username)) {
                        String imageUrl = userSnapshot.child("imgeurl").getValue(String.class);

                        callback.onImageUrlRetrieved(imageUrl);
                        usersRef.removeEventListener(this);
                        return;
                    }
                }
                callback.onImageUrlRetrieved(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onImageUrlError(error.getMessage());
            }
        });

        return "Fetching..."; // Optional return value to indicate ongoing operation
    }


    public String getImgeurl() {
        return imgeurl;
    }

    public void setImgeurl(String imgeurl) {
        this.imgeurl = imgeurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPasss() {
        return ConfirmPasss;
    }

    public void setConfirmPasss(String confirmPasss) {
        ConfirmPasss = confirmPasss;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth_DAte() {
        return birth_DAte;
    }

    public void setBirth_DAte(String birth_DAte) {
        this.birth_DAte = birth_DAte;
    }
}
