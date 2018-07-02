package gwg.ivo.android.bakingapp.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gwg.ivo.android.bakingapp.R;
import gwg.ivo.android.bakingapp.ui.RecipeDetailActivity;

public class StepsListRecyclerViewAdapter extends RecyclerView.Adapter<StepsListRecyclerViewAdapter.StepsListViewHolder> {

    private List<String> mItemsList;


    public StepsListRecyclerViewAdapter(List<String> list) {
        this.mItemsList = list;
    }

    public class StepsListViewHolder extends RecyclerView.ViewHolder {

        private TextView mStepTitleTextView;

        public StepsListViewHolder(View itemView) {
            super(itemView);
            mStepTitleTextView = (TextView) itemView.findViewById(R.id.step_title);
        }
    }

    @Override
    public StepsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_steps_list, null);
        return new StepsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StepsListViewHolder holder, final int position) {
        if (mItemsList == null || position >= mItemsList.size()) {
            return;
        }
        holder.mStepTitleTextView.setText(mItemsList.get(position));
        holder.mStepTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeDetailActivity.setPosition(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mItemsList == null) return -1;
        return mItemsList.size();
    }
}
