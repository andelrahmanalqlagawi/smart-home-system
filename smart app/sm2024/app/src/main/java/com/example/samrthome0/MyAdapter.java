package com.example.samrthome0;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements RecycleViewInterface{

    private Activity activity;
    ArrayList<ParentItem> parentItemArrayList;
    ArrayList<ChildItem> childItemArrayList;
    public void set_FilterdList(List<ParentItem> filterdList){
        this.parentItemArrayList = (ArrayList<ParentItem>) filterdList;
        notifyDataSetChanged();
    }
    public MyAdapter(Activity activity, ArrayList<ParentItem> parentItemArrayList, ArrayList<ChildItem> childItemArrayList, RecycleViewInterface recycleViewInterface) {
        this.activity = activity;
        this.parentItemArrayList = parentItemArrayList;
        this.childItemArrayList = childItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //holder.tvName.setText(arrayList.get(position));
        ParentItem parentItem = parentItemArrayList.get(position);
        holder.tvOrderId.setText(parentItem.orderId);
        holder.ivParent.setImageResource(parentItem.imageId);



        MemberAdp adapterMember = new MemberAdp(childItemArrayList, this,new MemberAdp.ItemClickListener() {
            @Override
            public void onItemClick(ChildItem details) {
                showToast(details.itemName + " Clicked!");

//                for (ChildItem item : childItemArrayList) {
//                    Intent intent;
//                    if ("Temperature".equals(details.getIdentifier())) {
//                        intent = new Intent(activity, HomeActivity.class);
//                    } else if ("Password".equals(details.getIdentifier())) {
//                        intent = new Intent(activity, Login.class);
//                    } else {
//                        intent = new Intent(activity, MainActivity.class);
//                    }
//                    intent.putExtra("itemName", details.getItemName());
//                    intent.putExtra("imageID", details.getImageID());
//                    intent.putExtra("identifier", details.getIdentifier());
//                    activity.startActivity(intent);
//                }
                Intent i = new Intent(activity, HomeActivity.class);
                activity.startActivity(i);

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.nested_rv.setLayoutManager(linearLayoutManager);
        holder.nested_rv.setAdapter(adapterMember);

    }
    public void showToast(String message){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {

        return parentItemArrayList.size();

    }

    @Override
    public void onItemClick(int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvOrderId;
        ImageView ivParent;
        RecyclerView nested_rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            ivParent = itemView.findViewById(R.id.ivparent);
            nested_rv = itemView.findViewById(R.id.nested_rv);


        }
    }

}
