package com.epam.oleksandr_sich.matchingpicturegame.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.epam.oleksandr_sich.matchingpicturegame.game.GameControllerImpl;
import com.epam.oleksandr_sich.matchingpicturegame.presenter.ImagePresenterImpl;
import com.epam.oleksandr_sich.matchingpicturegame.ImagesAdapter;
import com.epam.oleksandr_sich.matchingpicturegame.R;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ImagesAdapter.ItemClickListener, ImageView {

    private RecyclerView recyclerView;
    private ImagePresenterImpl presenter;
    private ImagesAdapter adapter;
    private CatLoadingView loadingView;
    private GameControllerImpl gameControllerImpl;
    private final Handler handler = new Handler();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.images);
        initLoadingDialog();
        init();
        loadPhotos();
        return view;
    }

    private void loadPhotos() {
        gameControllerImpl.clearGameData();
        presenter.loadPhotos();
    }

    private void init() {
        presenter = new ImagePresenterImpl(getActivity(), this);
        initList();
        initAdapter();
        gameControllerImpl = new GameControllerImpl(adapter);
        setHasOptionsMenu(true);
    }

    private void initAdapter() {
        adapter = new ImagesAdapter(getActivity(), new ArrayList<>());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initList() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                ImagePresenterImpl.SPAN_COUNT));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initLoadingDialog() {
        loadingView = new CatLoadingView();
        loadingView.setCancelable(false);
    }

    @Override
    public void onItemClick(View view, final int position) {
        gameControllerImpl.flipCard(position);
    }


    @Override
    public void showImages(List<PhotoItem> photos) {
        adapter.updateItems(photos);
    }

    @Override
    public void showLoading(boolean state) {
        if (state && loadingView.getDialog() == null) {
            loadingView.show(getFragmentManager(), "");
        } else {
            handler.postDelayed(() -> {
                if (loadingView.getDialog() != null) {
                    loadingView.dismiss();
                }
            }, 1000);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
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
            gameControllerImpl.clearGameData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
