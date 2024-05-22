package com.example.samrthome0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecycleViewInterface {

    MyAdapter myAdapter;
    ArrayList<ParentItem> parentItemArrayList;
    ArrayList<ChildItem> childItemArrayList;
    RecyclerView RVparent;
    RecycleViewInterface recycleViewInterface;

    SearchView searchView;
    //useing appcomapt.widget
    Toolbar toolbar1;
    String logged_User ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        logged_User = intent.getStringExtra("User Name");
        RVparent = findViewById(R.id.RVparent);
        toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        String[] orderID = {"Temperature","Password","Light","Fan","Entry Attack","Message"};
        String[] itemName = {"Action of Calculation "};
       // String[] identifier={"Temperature","Password","Light","Fan","Entry Attack","Message"};

        int[] imageId = {R.drawable.temperature,R.drawable.security,R.drawable.led,R.drawable.fan,R.drawable.alert,R.drawable.sms};
        int[] imageID = {R.drawable.action};
        parentItemArrayList = new ArrayList<>();
        childItemArrayList = new ArrayList<>();

        for (int i=0 ; i<orderID.length; i++ ){

            ParentItem parentItem = new ParentItem(orderID[i],imageId[i]);//String.valueOf(i+14),String.valueOf(i*43),
            parentItemArrayList.add(parentItem);

            //Child Item Object

            if(i<itemName.length){
                ChildItem childItem = new ChildItem(itemName[i], imageID[i]);//String.valueOf(i + 5), String.valueOf(i * 15),
                childItemArrayList.add(childItem);}




        }
        // handeling adapter set constraction
        myAdapter = new MyAdapter(this,parentItemArrayList,childItemArrayList,recycleViewInterface);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RVparent.setLayoutManager(layoutManager);
        RVparent.setAdapter(myAdapter);

    }
    ////// Search bar code
    public void filterList(String Text) {
        List<ParentItem> filterdList = new ArrayList<>();
        for (ParentItem item : parentItemArrayList) {
            if(item.getOrderId().toLowerCase().contains(Text.toLowerCase())){
                filterdList.add(item);
            }
        }

        if(filterdList.isEmpty()){
            Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show();
        }
        else{
            myAdapter.set_FilterdList(filterdList);
        }
    }

    //Option menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //send the function and the position
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if( itemId == R.id.ActivityLog ){
            startActivity(new Intent(MainActivity.this, ActivityLog.class));


        }
        else if(itemId == R.id.Profile){
            Intent I = new Intent(MainActivity.this, ProfileActivity.class);
            I.putExtra("User Name", logged_User);
            startActivity(I);


        }
        else if(itemId == R.id.Logout){
            SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
            SharedPreferences.Editor editor = chrem.edit();
            editor.putBoolean("remember", false);
            editor.apply();

            Intent toLogin = new Intent(getApplicationContext(),Login.class);
            startActivity(toLogin);
            MainActivity.this.finish();
        }

        return true;
    }

    @Override
    public void onItemClick(int position) {
         Intent intent = new Intent(MainActivity.this,HomeActivity.class);
       //  intent.putExtra("textsec",childItemArrayList.get(position).imageID);
         //intent.putExtra("img",childItemArrayList.get(position).itemName);

         startActivity(intent);

    }



}

