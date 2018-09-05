package com.epam.oleksandr_sich.matchingpicturegame;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ImagesAdapter.ItemClickListener, ImageView {

    private ImagesAdapter adapter;
    private RecyclerView recyclerView;
    private ImagePresenterImpl presenter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.images);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
         presenter = new ImagePresenterImpl(getActivity(), this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};

        adapter = new ImagesAdapter(getActivity(), new ArrayList<PhotoDTO>());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        presenter.loadPhotos(1);
    }

    @Override
    public void onItemClick(View view, int position) {

    }


    @Override
    public void showImages(List<PhotoDTO> photos) {
        adapter.updateItems(photos);
    }
}
