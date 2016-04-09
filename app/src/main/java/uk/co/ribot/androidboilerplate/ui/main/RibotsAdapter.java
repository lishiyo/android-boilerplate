package uk.co.ribot.androidboilerplate.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Ribot;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class RibotsAdapter extends RecyclerView.Adapter<RibotsViewHolder> {

	/**
     * List of presentation models used by {@link MainActivity}
     */
    @NonNull
    private List<Ribot> mRibots;

	/**
     * Constructor that Dagger should use to create instances of a class
     */
    @Inject
    public RibotsAdapter() {
        mRibots = new ArrayList<>();
    }

    public void setRibots(List<Ribot> ribots) {
        mRibots = ribots;
    }

    @Override
    public RibotsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ribot, parent, false);
        return new RibotsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RibotsViewHolder holder, int position) {
        holder.bindModel(mRibots.get(position));
    }

    @Override
    public int getItemCount() {
        return mRibots.size();
    }
}
