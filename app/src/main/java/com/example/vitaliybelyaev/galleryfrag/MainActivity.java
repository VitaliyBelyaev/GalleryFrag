package com.example.vitaliybelyaev.galleryfrag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements CollectionFragment.CollectionFragmentListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            openFragment(MainFragment.newInstance(),false);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                boolean showAsUp = getSupportFragmentManager().getBackStackEntryCount()>0;
                getSupportActionBar().setDisplayHomeAsUpEnabled(showAsUp);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }



    @Override
    public void onPreviewClick(int collectionId) {
        openFragment(PreviewFragment.newInstance(collectionId),true);
    }

    private void openFragment(Fragment fragment,Boolean addToBackStack){
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        if(addToBackStack){
            transaction
                    .addToBackStack(null);
        }

        transaction
                .replace(R.id.frame_for_fragments, fragment)
                .commit();
    }
}
