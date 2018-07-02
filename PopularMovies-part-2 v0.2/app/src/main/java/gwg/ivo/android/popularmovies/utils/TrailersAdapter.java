package gwg.ivo.android.popularmovies.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.List;
import gwg.ivo.android.popularmovies.R;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private List<String> trailersList;

    public class TrailersViewHolder extends RecyclerView.ViewHolder {

        private Button button;

        public TrailersViewHolder(View view) {
            super(view);
            button = (Button) view.findViewById(R.id.trailer_btn);
        }
    }
    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trailers, parent, false);
        return new TrailersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailersViewHolder holder, final int position) {
        if (trailersList == null || trailersList.size() < 1) {
            return;
        }
        final String embedUrl = trailersList.get(position);
        holder.button.setText("Trailer #"+(position+1));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(embedUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    public TrailersAdapter(List<String> _list) {
        trailersList = _list;
    }
}
