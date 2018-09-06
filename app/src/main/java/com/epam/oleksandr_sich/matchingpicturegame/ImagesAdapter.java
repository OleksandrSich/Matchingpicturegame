package com.epam.oleksandr_sich.matchingpicturegame;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.epam.oleksandr_sich.matchingpicturegame.data.ImageState;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private List<PhotoItem> data;
    private Context context;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    ImagesAdapter(Context context, List<PhotoItem> data) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.image_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.showImage(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateItems(List<PhotoItem> photos) {
        data = photos;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }

        void showImage(final int position) {
            switch (getItem(position).getState()) {
                case DEFAULT:
                    RequestOptions requestOptions = new RequestOptions().priority(Priority.IMMEDIATE);
                    Glide.with(context)
                            .download(getItem(position).getUrl())
                            .apply(requestOptions)
                            .submit();
                    image.setImageResource(R.drawable.ic_launcher_background);
                    break;
                case SELECTED:
                    Glide.with(context)
                            .load(getItem(position).getUrl())
                            .into(image);
                    animationObject.animate(image);
                    break;
                case CLOSED:
                    Glide.with(context)
                            .load(getItem(position).getUrl())
                      .into(image);
                    if (getItem(position).getPreviousState() == ImageState.DEFAULT)
                        animationObject.animate(image);
                case DONE:
                case OPENED:
                    Glide.with(context)
                            .load(getItem(position).getUrl())
                            .into(image);
                    break;
            }

        }
    }

    public PhotoItem getItem(int id) {
        return data.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private ViewPropertyTransition.Animator animationObject = new ViewPropertyTransition.Animator() {
        @Override
        public void animate(View view) {
            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "rotationY", 180f, 0f);
            fadeAnim.setDuration(500);
            fadeAnim.setAutoCancel(true);
            fadeAnim.start();
        }
    };


}
