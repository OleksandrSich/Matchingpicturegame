package com.epam.oleksandr_sich.matchingpicturegame;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.epam.oleksandr_sich.matchingpicturegame.data.ImageState;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ImagesAdapter.ItemClickListener, ImageView {

    private ImagesAdapter adapter;
    private RecyclerView recyclerView;
    private ImagePresenterImpl presenter;
    private int selectedPosition = -1;
    private int selectedItems = 0;
    private int steps = 0;
    final Handler handler = new Handler();
    private CatLoadingView loadingView;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.images);
        init();
        return view;
    }

    private void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter = new ImagePresenterImpl(getActivity(), this);
        loadingView = new CatLoadingView();
        loadingView.setCancelable(false);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ImagesAdapter(getActivity(), new ArrayList<PhotoItem>());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        presenter.loadPhotos();
    }

    @Override
    public void onItemClick(View view, final int position) {
        if (adapter.getItem(position).getState() != ImageState.DEFAULT ||
                selectedItems >= 2) return;
        steps++;
        if (selectedPosition == -1) {
            selectedPosition = position;
            adapter.getItem(position).updateState(ImageState.SELECTED);
            selectedItems++;
            adapter.notifyItemChanged(position);
        } else {
            if (adapter.getItem(selectedPosition).equals(adapter.getItem(position))) {
                adapter.getItem(selectedPosition).updateState(ImageState.DONE);
                adapter.getItem(position).updateState(ImageState.DONE);
                if (isWon()) finishGame();
                selectedItems = 0;
                selectedPosition = -1;
            } else {
                adapter.getItem(selectedPosition).updateState(ImageState.CLOSED);
                adapter.getItem(position).updateState(ImageState.CLOSED);
                selectedItems++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getItem(position).updateState(ImageState.DEFAULT);
                        adapter.getItem(selectedPosition).updateState(ImageState.DEFAULT);
                        adapter.notifyItemChanged(selectedPosition);
                        adapter.notifyItemChanged(position);
                        selectedItems = 0;
                        selectedPosition = -1;
                    }
                }, 1000);

            }

            adapter.notifyItemChanged(selectedPosition);
            adapter.notifyItemChanged(position);

        }

    }

    private void finishGame() {

    }


    @Override
    public void showImages(List<PhotoItem> photos) {
        adapter.updateItems(photos);
    }

    @Override
    public void showLoading(boolean state) {
        if (state){
            loadingView.show(getFragmentManager(), "");
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingView.dismiss();
                }
            }, 1500);
        }
    }

    private boolean isWon() {
        boolean res = true;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).getState() != ImageState.DONE) {
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            presenter.loadPhotos();
            clearGameData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearGameData(){
      selectedPosition = -1;
      selectedItems = 0;
      steps = 0;
    }
}
