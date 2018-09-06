package com.epam.oleksandr_sich.matchingpicturegame.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epam.oleksandr_sich.matchingpicturegame.ImagesAdapter;
import com.epam.oleksandr_sich.matchingpicturegame.R;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;
import com.epam.oleksandr_sich.matchingpicturegame.game.GameControllerImpl;
import com.epam.oleksandr_sich.matchingpicturegame.game.GameResult;
import com.epam.oleksandr_sich.matchingpicturegame.presenter.ImagePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ImagesAdapter.ItemClickListener, ImageView, GameResult {

    private RecyclerView recyclerView;
    private ImagePresenterImpl presenter;
    private ImagesAdapter adapter;
    private RelativeLayout loadingView;
    private GameControllerImpl gameControllerImpl;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.images);
        loadingView = view.findViewById(R.id.loadingView);
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
        gameControllerImpl = new GameControllerImpl(adapter, this);
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
        loadingView.setVisibility(state ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(state ? View.GONE : View.VISIBLE);

    }

    @Override
    public void showErrorMsg() {
        Toast.makeText(getActivity(), R.string.error_msg_loading,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            restart();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void gameFinished(int steps) {
        showFinishAlert(steps);
    }

    private void showFinishAlert(int steps) {
        View layoutView = getLayoutInflater().inflate(R.layout.win_layout, null);
        TextView winLabel = layoutView.findViewById(R.id.winLabel);
        winLabel.setText(getString(R.string.congratulations, steps));
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(layoutView)
                .setPositiveButton(R.string.start_new, (dialogInterface, i) -> restart())
                .create();
        alertDialog.show();
    }

    private void restart() {
        gameControllerImpl.clearGameData();
        adapter.updateItems(new ArrayList<>());
        adapter.notifyDataSetChanged();
        presenter.loadPhotos();
    }

}
