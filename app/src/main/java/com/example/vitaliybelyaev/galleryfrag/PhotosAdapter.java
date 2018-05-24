package com.example.vitaliybelyaev.galleryfrag;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vitaliybelyaev.galleryfrag.domain.PreviewPhoto;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoVH>{

    private List<PreviewPhoto> previewPhotos = Collections.EMPTY_LIST;

    public PhotosAdapter(List<PreviewPhoto> previewPhotos){
        this.previewPhotos = previewPhotos;
    }


    @NonNull
    @Override
    public PhotoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PhotoVH(inflater.inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoVH holder, int position) {
        String imageUrl = previewPhotos.get(position).getUrlSmall();

        Picasso.get()
                .load(imageUrl)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return previewPhotos.size();
    }

    public class PhotoVH extends RecyclerView.ViewHolder{

        ImageView imageView;

        public PhotoVH(View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
