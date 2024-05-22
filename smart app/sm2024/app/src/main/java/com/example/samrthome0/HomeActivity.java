package com.example.samrthome0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {
    String logged_User ;
    Toolbar toolbar1;
    private TextView textsec;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        Intent intent = getIntent();
        logged_User = intent.getStringExtra("User Name");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        //send the function and the position
        getMenuInflater().inflate(R.menu.menu2,menu2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if( itemId == R.id.Temperature ){
            startActivity(new Intent(HomeActivity.this, tempView.class));
        }
        else if(itemId == R.id.Password){
            Intent I = new Intent(HomeActivity.this, Setpassword.class);
            I.putExtra("User Name", logged_User);
            startActivity(I);
        }
        else if(itemId == R.id.Light){
            Intent I = new Intent(HomeActivity.this, SetLight.class);
            I.putExtra("User Name", logged_User);
            startActivity(I);
        }
        else if(itemId == R.id.Fan){
            Intent I = new Intent(HomeActivity.this, SetFan.class);
            I.putExtra("User Name", logged_User);
            startActivity(I);
        }
        else if(itemId == R.id.Entry){
            Intent I = new Intent(HomeActivity.this, Get_attack.class);
            I.putExtra("User Name", logged_User);
            startActivity(I);
        }
        else if(itemId == R.id.Message){
            SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
            SharedPreferences.Editor editor = chrem.edit();
            editor.putBoolean("remember", false);
            editor.apply();

            Intent toLogin = new Intent(getApplicationContext(),Set_message.class);
            startActivity(toLogin);
            HomeActivity.this.finish();
        }

        return true;
    }
}