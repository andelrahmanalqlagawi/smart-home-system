package com.example.samrthome0;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {
    public EditText UserNameProfile;
    public Button LogoutProFileButton;
    public ImageView UserPIC;

    private ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profie);
        UserNameProfile = findViewById(R.id.UserNameEditprofile);
        LogoutProFileButton = findViewById(R.id.logoutButtonprofile);
        LogoutProFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
                SharedPreferences.Editor editor = chrem.edit();
                editor.putBoolean("remember", false);
                editor.apply();

                Intent toLogin = new Intent(getApplicationContext(),Login.class);
                startActivity(toLogin);
                ProfileActivity.this.finish();
            }
        });


        UserPIC = findViewById(R.id.UserImageprofile);
        Intent intent = getIntent();
        String logged_User = intent.getStringExtra("User Name");
        HelperReg h = new HelperReg();
        UserNameProfile.setText(logged_User);
        h.geturl(logged_User, new HelperReg.GetImageUrlCallback() {

            @Override
            public void onImageUrlRetrieved(String imageUrl) {
                if (imageUrl != null) {
                    Toast.makeText(ProfileActivity.this, imageUrl, Toast.LENGTH_SHORT).show();
                    ImageLoaderTask task = new ImageLoaderTask(UserPIC);
                    task.execute(imageUrl);
                } else {
                    Toast.makeText(ProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onImageUrlError(String errorMessage) {
                Toast.makeText(ProfileActivity.this, "Error retrieving image URL: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public ImageLoaderTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(ProfileActivity.this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}