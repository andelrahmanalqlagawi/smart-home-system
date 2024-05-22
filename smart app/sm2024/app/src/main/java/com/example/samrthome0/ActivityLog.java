package com.example.samrthome0;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityLog extends AppCompatActivity {
   // RecyclerView UsersView;
    // ArrayList<String> user_name,name,password,con_password,email,date;
    RecyclerView UsersView;
    ArrayList<String> name,user_name,password,con_password,email,date;
    DBhelperREG Users;
    CustomerAdapterRecycleView customerAdapterRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            UsersView = findViewById(R.id.userlist);
            Users = new DBhelperREG(ActivityLog.this);

            name = new ArrayList<>();
            user_name = new ArrayList<>();
            password = new ArrayList<>();
            con_password = new ArrayList<>();
            email = new ArrayList<>();
            date = new ArrayList<>();

            Display_Data();

            customerAdapterRecycleView = new CustomerAdapterRecycleView(ActivityLog.this,name,
                    user_name,password,con_password,email,date );
            UsersView.setAdapter(customerAdapterRecycleView);
            UsersView.setLayoutManager(new LinearLayoutManager(ActivityLog.this));
            /*UsersAdapter = new ArrayAdapter<String>((getApplicationContext()),
            android.R.layout.simple_list_item_1);

            UsersList.setAdapter(UsersAdapter);
            Users= new DBhelperREG((getApplicationContext()));
            Cursor cursor = Users.FetchAllUsers();
            while (!cursor.isAfterLast())
            {
                UsersAdapter.add(cursor.getString(0));
                cursor.moveToNext();
            }*/

            return insets;
        });
    }
   void Display_Data(){
        Cursor cursor = Users.FetchAllUsers();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }else
        {
            while(cursor.moveToNext()){
                name.add(cursor.getString(0));
                user_name.add(cursor.getString(1));
                password.add(cursor.getString(2));
                con_password.add(cursor.getString(3));
                email.add(cursor.getString(4));
                date.add(cursor.getString(5));
            }
        }
     }

}