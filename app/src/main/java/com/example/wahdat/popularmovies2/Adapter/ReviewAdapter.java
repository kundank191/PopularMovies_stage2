package com.example.wahdat.popularmovies2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wahdat.popularmovies2.Model.ResultReview;
import com.example.wahdat.popularmovies2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>{

    private List<ResultReview> reviewList;
    private Context context;

    public ReviewAdapter(List<ResultReview> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        ResultReview resultReview=reviewList.get(position);
        holder.userName.setText(resultReview.getAuthor());

        holder.userReview.setText(resultReview.getContent());


    }

    @Override
    public int getItemCount() {
        if (reviewList==null){
            return 0;

        }
      return reviewList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.username) TextView userName;
       @BindView(R.id.userReview) TextView userReview;
        public ReviewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
