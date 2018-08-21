package com.example.wahdat.popularmovies2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wahdat.popularmovies2.Model.ResultTrailer;

import com.example.wahdat.popularmovies2.Model.Trailer;
import com.example.wahdat.popularmovies2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
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


        final ResultTrailer resultTrailer = trailers.get(position);
        final String BASE_URL = "https://www.youtube.com/watch?v=";
        String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/";
        String YOUTUBE_IMAGE_EXT = "/0.jpg";
        final String YOUTUBE_APP_URI = "vnd.youtube:";
        Picasso.get().load(YOUTUBE_THUMBNAIL_URL + resultTrailer.getKey() + YOUTUBE_IMAGE_EXT)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_URI + resultTrailer.getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASE_URL + resultTrailer.getKey()));
                if (appIntent.resolveActivity(context.getPackageManager()) != null) {
                    appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(appIntent);
                } else if (webIntent.resolveActivity(context.getPackageManager()) != null) {
                    webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(webIntent);
                }
                else {
                    Toast.makeText(context, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        if (trailers==null){
            return 0;
        }
        return trailers.size();
    }

    public class TrailerViewholder extends RecyclerView.ViewHolder{

        @BindView(R.id.trailerImage) ImageView imageView;
        public TrailerViewholder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.trailerImage);
        }
    }
}
