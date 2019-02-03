package com.draos.nekretnine.nekretnineui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        holder.adTitle.setText(movie.getTitle());
        holder.adPrice.setText(movie.getPrice());
        holder.adArea.setText(String.valueOf(movie.getArea()) + " squares");
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
        public TextView adTitle;
        public TextView adDescription;
        public TextView adPrice;
        public TextView adArea;
        public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            adTitle = (TextView) view.findViewById(R.id.advertiseTitle);
            adDescription = (TextView) view.findViewById(R.id.advertiseDescription);
            adPrice = (TextView) view.findViewById(R.id.advertisePrice);
            adArea = (TextView) view.findViewById(R.id.advertiseArea);
            imageView = (ImageView) view.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Item Click Position", String.valueOf(getLayoutPosition()));
                    Advertise a = advertiseList.get(getLayoutPosition());

                    Bundle bundle = new Bundle();
                    bundle.putString("title", a.getTitle());
                    bundle.putString("price", a.getPrice());

                    AdvertiseDetailsFragment advertiseDetails = new AdvertiseDetailsFragment();
                    advertiseDetails.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, advertiseDetails,"Tag_Advertises")
                            .addToBackStack("Tag_Advertises")
                            .commitAllowingStateLoss();
                }
            });
        }
    }
}