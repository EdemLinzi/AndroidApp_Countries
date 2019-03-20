package com.example.countries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<CountryData> mDataset;
    private Context myContext;

    public MyAdapter(ArrayList<CountryData> myDataset,Context myContext) {
        mDataset = myDataset;
        this.myContext = myContext;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView1);
            imageView = v.findViewById(R.id.imageView);
        }
    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.textView.setText(mDataset.get(position).getName());

        new DownloadImageTask(holder.imageView).execute(mDataset.get(position).getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,MapsActivity.class);
                intent.putExtra("Name",mDataset.get(position).getName());
                intent.putExtra("Lat",mDataset.get(position).getLat());
                intent.putExtra("Lng",mDataset.get(position).getLng());
                intent.putExtra("Population",mDataset.get(position).getPopulation());
                intent.putExtra("Area",mDataset.get(position).getArea());
                ((Activity) myContext).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}