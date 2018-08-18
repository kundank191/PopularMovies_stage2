package com.example.wahdat.popularmovies2.Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wahdat.popularmovies2.Model.Result;
import com.example.wahdat.popularmovies2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.MoviesViewHolder>{

    private List<Result> movies;

    private OnItemClickListener mListener;
    private Context context;

    //add an interface for itemclicklistener
    public interface OnItemClickListener{
        void onListItemClick(int position);
    }

    public void setonItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }


    public Adapter(List<Result> movies,Context context) {

this.movies=movies;
this.context=context;
    }





    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, int position) {
     final Result result= movies.get(position);


     String BASE_URL="http://image.tmdb.org/t/p/";
     String size= "w780/";
     Picasso.get().load(BASE_URL+size+result.getPosterPath())
             .placeholder(R.drawable.imageload).into(holder.posterImage);
              setAnimation(holder.itemView);

    }
    private void setAnimation(View holder) {
        ObjectAnimator.ofFloat(holder, "translationY", -20.0f, 0)

                .setDuration(2000)
                .start();

    }

    @Override
    public int getItemCount() {
      if (movies==null){
          return 0;
      }

        return movies.size();
    }



    public class MoviesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.poster_image)
        ImageView posterImage;
        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        int position =getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            mListener.onListItemClick(position);
                        }
                    }

                }
            });
        }

    }

}

