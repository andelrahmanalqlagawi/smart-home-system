package com.example.samrthome0;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberAdp extends RecyclerView.Adapter<MemberAdp.ViewHolder> implements RecycleViewInterface{

    private final RecycleViewInterface recycleViewInterface;
    ArrayList<ChildItem> childarrayList;
    ItemClickListener mItemListener;
    public MemberAdp(ArrayList<ChildItem> childarrayList,RecycleViewInterface recycleViewInterface,ItemClickListener ItemListener) {

        this.childarrayList = childarrayList;
        this.mItemListener = ItemListener;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChildItem childItem = childarrayList.get(position);
        holder.tvItemName.setText(childItem.itemName);
        holder.ivChild.setImageResource(childItem.imageID);

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(childarrayList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return childarrayList.size();
    }

    @Override
    public void onItemClick(int position) {}

    public interface ItemClickListener{

        void onItemClick(ChildItem details);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItemName;
        ImageView ivChild;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            ivChild = itemView.findViewById(R.id.ivChild);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });


        }
    }





}
