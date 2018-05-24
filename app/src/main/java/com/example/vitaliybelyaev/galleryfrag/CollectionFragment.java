package com.example.vitaliybelyaev.galleryfrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vitaliybelyaev.galleryfrag.domain.Collection;
import com.squareup.picasso.Picasso;

public class CollectionFragment extends Fragment {

    public static final String ARG_COLLECTION_ID = "collectionId";

    private Collection collection;
    private CollectionFragmentListener listener;

    private TextView titleTextView;
    private ImageView previewImage;
    private TextView descriptionTextView;

    public interface CollectionFragmentListener{
        void onPreviewClick(int collectionId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int collectionId = getArguments().getInt(ARG_COLLECTION_ID);
        this.collection = CollectionsRepository.getInstance().getById(collectionId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_collection,container,false);

            titleTextView = view.findViewById(R.id.tv_collection_title);
            descriptionTextView = view.findViewById(R.id.tv_description);
            previewImage = view.findViewById(R.id.collection_image);

            return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView.setText(collection.getTitle());
        descriptionTextView.setText(collection.getDescription());
        final String imageUrl = collection.getPreviewPhotos().get(0).getUrlSmall();
        Picasso.get().load(imageUrl).into(previewImage);

        previewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onPreviewClick(collection.getId());
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CollectionFragmentListener){
            listener = (CollectionFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public static CollectionFragment newInstance(int collectionId){
        CollectionFragment fragment = new CollectionFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLLECTION_ID, collectionId);
        fragment.setArguments(args);
        return fragment;
    }
}
