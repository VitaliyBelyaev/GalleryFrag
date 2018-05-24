package com.example.vitaliybelyaev.galleryfrag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Collections;
import java.util.List;

public class CollectionsPagerAdapter extends FragmentStatePagerAdapter {

    private List<CollectionFragment> fragments = Collections.EMPTY_LIST;


    public CollectionsPagerAdapter(FragmentManager fm, List<CollectionFragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
