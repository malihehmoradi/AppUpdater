package ir.malihehmoradi.appupdater.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.malihehmoradi.appupdater.R;
import ir.malihehmoradi.appupdater.model.AppConfig;

/**
 * A fragment representing a list of Items.
 */
public class RecentChangesFragment extends Fragment {


    private static List<AppConfig.Change> changes = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     *
     * @param changes
     */
    public RecentChangesFragment(List<AppConfig.Change> changes) {
        this.changes = changes;
    }

    @SuppressWarnings("unused")
    public static RecentChangesFragment newInstance(int columnCount) {
        RecentChangesFragment fragment = new RecentChangesFragment(changes);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(changes));
        }

        return view;
    }

}