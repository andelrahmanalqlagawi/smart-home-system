package com.example.samrthome0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapterRecycleView extends RecyclerView.Adapter<CustomerAdapterRecycleView.MyViewHolder> {

    private Context context;
    private ArrayList name,user_name,password,con_password,email,date;

    CustomerAdapterRecycleView(Context context,ArrayList name,ArrayList user_name,ArrayList password,ArrayList con_password,
                               ArrayList email,ArrayList date){
        this.context = context;
        this.name=name;
        this.user_name=user_name;
        this.password=password;
        this.con_password=con_password;
        this.email=email;
        this.date=date;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        holder.username.setText(String.valueOf(user_name.get(position)));
        holder.pass.setText(String.valueOf(password.get(position)));
        holder.con_pass.setText(String.valueOf(con_password.get(position)));
        holder.email.setText(String.valueOf(email.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));

    }

    @Override
    public int getItemCount() {
        return user_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,username,pass,con_pass,email,date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            username = itemView.findViewById(R.id.username);
            pass = itemView.findViewById(R.id.password);
            con_pass = itemView.findViewById(R.id.con_password);
            email = itemView.findViewById(R.id.email);
            date = itemView.findViewById(R.id.date);

        }
    }
}
