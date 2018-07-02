package gwg.ivo.android.popularmovies.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gwg.ivo.android.popularmovies.MovieActivity;
import gwg.ivo.android.popularmovies.R;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private List<String[]> reviewsList;

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        public TextView authortTV;
        public TextView contentTV;

        public ReviewsViewHolder(View view) {
            super(view);
            authortTV = (TextView) view.findViewById(R.id.author_tv);
            contentTV = (TextView) view.findViewById(R.id.content_tv);
        }
    }



    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_reviews,parent,false);

        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        if(reviewsList == null || reviewsList.size() <1 ) {
            return;
        }

        holder.authortTV.setText(reviewsList.get(position)[MovieActivity.INDEX_AUTHOR]);
        holder.contentTV.setText(reviewsList.get(position)[MovieActivity.INDEX_CONTENT]);
    }

    public ReviewsAdapter (List<String[]> _list) {
        reviewsList = _list;
    }


    @Override
    public int getItemCount() {
        return reviewsList.size();
    }
}
