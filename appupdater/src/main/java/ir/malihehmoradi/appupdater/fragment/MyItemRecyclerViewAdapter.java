package ir.malihehmoradi.appupdater.fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.malihehmoradi.appupdater.R;
import ir.malihehmoradi.appupdater.model.AppConfig;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ir.malihehmoradi.appupdater.model.AppConfig.Change}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<AppConfig.Change> mValues;

    public MyItemRecyclerViewAdapter(List<AppConfig.Change> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txt_date.setText(mValues.get(position).date);
        holder.txt_description.setText(mValues.get(position).description);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txt_date;
        public final TextView txt_description;
        public AppConfig.Change mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txt_date = (TextView) view.findViewById(R.id.txt_date);
            txt_description = (TextView) view.findViewById(R.id.txt_description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txt_description.getText() + "'";
        }
    }
}