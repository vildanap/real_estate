package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.draos.nekretnine.nekretnineui.Model.Advertise;

import java.util.List;

public class AdvertiseAdapter extends RecyclerView.Adapter<AdvertiseAdapter.MyViewHolder> {
    List<Advertise> advertiseList;
    Context context;
    public AdvertiseAdapter(List<Advertise> advertiseList, Context context) {
        this.advertiseList = advertiseList;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_row, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Advertise movie = advertiseList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvRating.setText(movie.getRating());
        holder.imageView.setImageResource(R.drawable.sale);
        }
    @Override
    public int getItemCount() {
        return advertiseList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvYear;
        public TextView tvRating;
        public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.advertiseTitle);
            tvYear = (TextView) view.findViewById(R.id.advertiseYear);
            tvRating = (TextView) view.findViewById(R.id.tvRating);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}