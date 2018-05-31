package com.example.vitaliybelyaev.galleryfrag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitaliybelyaev.galleryfrag.domain.Collection;
import com.example.vitaliybelyaev.galleryfrag.network.NetworkModule;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = view.findViewById(R.id.pager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NetworkModule.getApi().getCollections().enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if(response.code() == 200){
                    List<Collection> collections = response.body();
                    if(collections != null){
                        for(Collection collection:collections){
                            CollectionsRepository.getInstance().save(collection);
                        }
                    }
                    initViewPagerAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {

            }
        });
    }

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    private void initViewPagerAdapter(){
        final List<Collection> collections = CollectionsRepository.getInstance().getAllCollections();
        final List<CollectionFragment> fragments = new ArrayList<>();
        for (Collection collection:collections){
            fragments.add(CollectionFragment.newInstance(collection.getId()));
        }
        final CollectionsPagerAdapter adapter = new CollectionsPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }


    private App getApp(){
        return ((App) getActivity().getApplication());
    }
}

