package com.example.wahdat.popularmovies2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wahdat.popularmovies2.Model.ResultTrailer;

import com.example.wahdat.popularmovies2.Model.Trailer;
import com.example.wahdat.popularmovies2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Callback;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewholder> {

    private List<ResultTrailer> trailers;
   private Context context;

    public TrailerAdapter(List<ResultTrailer> trailers, Context context) {
        this.trailers = trailers;
        this.context = context;
    }

    @NonNull
    @Override
    public TrailerViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list,parent,false);
        return new TrailerViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewholder holder, int position) {


        ResultTrailer resultTrailer=trailers.get(position);
       String BASE_URL="https://www.youtube.com/watch?v=";
        Picasso.get().load(BASE_URL+resultTrailer.getKey()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (trailers==null){
            return 0;
        }
        return trailers.size();
    }

    public class TrailerViewholder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TrailerViewholder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.trailerImage);
        }
    }
}
