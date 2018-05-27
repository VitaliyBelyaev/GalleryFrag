package com.example.vitaliybelyaev.galleryfrag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int collectionId = getArguments().getInt(ARG_COLLECTION_ID);
        collection = CollectionsRepository.getInstance().getById(collectionId);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.preview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.open_link) {
            Uri webpage = Uri.parse(collection.getLink());
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder
                    .setToolbarColor(ContextCompat.getColor(getContext(),R.color.colorPrimary))
                    .addDefaultShareMenuItem();

            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(getContext(), webpage);
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new PhotosAdapter(collection.getPreviewPhotos());
        recyclerView.setAdapter(adapter);

    }

    public static PreviewFragment newInstance(int collectionId) {
        PreviewFragment fragment = new PreviewFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COLLECTION_ID, collectionId);
        fragment.setArguments(args);
        return fragment;
    }
}
