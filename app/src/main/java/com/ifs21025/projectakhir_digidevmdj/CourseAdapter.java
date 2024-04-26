package com.ifs21025.projectakhir_digidevmdj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Integer> id;
    private ArrayList<String> title;
    private ItemClickListener mItemClickListener;

    public CourseAdapter(Context context, ArrayList id, ArrayList title) {
        this.context = context;
        this.id = id;
        this.title = title;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.course_item_row, parent, false);

        return new MyViewHolder(v);
    }

    public void addItemClickListener(ItemClickListener listener){
        mItemClickListener = listener;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(String.valueOf(title.get(position)));
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(id.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvItemTitle);
        }
    }

    public interface  ItemClickListener {
        void onItemClick(Integer id);
    }
}
