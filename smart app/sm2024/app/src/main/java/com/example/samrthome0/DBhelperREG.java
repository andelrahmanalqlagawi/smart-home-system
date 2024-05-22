package com.example.samrthome0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBhelperREG extends SQLiteOpenHelper {

    SQLiteDatabase REGDatabase;
    private static String databaseName="REGDatabase";
    private Context context;

    public DBhelperREG(@Nullable Context context){
        super(context,databaseName,null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users " +
                "(name TEXT  NOT NULL," +
                "username TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "con_password TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "brth_edit TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    public void CreatNewUser(String name, String username, String password, String con_password, String email, String brth_edit)
    {
        REGDatabase = getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put("name",name);
        row.put("username",username);
        row.put("password",password);
        row.put("con_password",con_password);
        row.put("email",email);
        row.put("brth_edit", brth_edit);


        long result = REGDatabase.insert("Users",null,row);
        if(result==-1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();}
        else
        {
            Toast.makeText(context,"Added sucessfully!",Toast.LENGTH_SHORT).show();
        }
        //REGDatabase.close();

    }
       public Cursor FetchAllUsers(){

        String query = "SELECT * FROM Users";
        REGDatabase =getReadableDatabase();
        //String[] rowDetails = {"name","username","password","con_password","email","brth_edit"};
        //Cursor cursor = REGDatabase.query("Users",rowDetails,null,null,null,null,null);
           Cursor cursor = null;
        if(REGDatabase != null) {
            cursor = REGDatabase.rawQuery(query, null);
        }

        REGDatabase.close();
        return cursor;
    }

}
