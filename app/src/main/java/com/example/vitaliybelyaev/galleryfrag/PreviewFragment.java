package com.example.vitaliybelyaev.galleryfrag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.vitaliybelyaev.galleryfrag.domain.Collection;

public class PreviewFragment extends Fragment {

    public static final String ARG_COLLECTION_ID = "collectionId";
    private Collection collection;
    private PhotosAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView closeImageView;
    private Button openButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int collectionId = getArguments().getInt(ARG_COLLECTION_ID);
        collection = CollectionsRepository.getInstance().getById(collectionId);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        closeImageView = view.findViewById(R.id.close_icon);
        openButton = view.findViewById(R.id.view_button);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new PhotosAdapter(collection.getPreviewPhotos());
        recyclerView.setAdapter(adapter);

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(collection.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static PreviewFragment newInstance(int collectionId){
        PreviewFragment fragment = new PreviewFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLLECTION_ID, collectionId);
        fragment.setArguments(args);
        return fragment;
    }
}
