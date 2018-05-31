package com.example.vitaliybelyaev.galleryfrag;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.vitaliybelyaev.galleryfrag.auth.AuthActivity;
import com.example.vitaliybelyaev.galleryfrag.storage.PreferencesProvider;

public class MainActivity extends AppCompatActivity
        implements CollectionFragment.CollectionFragmentListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String token = PreferencesProvider.getPreferences().getSavedToken();
        Log.i("MAIN","token in Prefs: "+token);
        if(TextUtils.isEmpty(token)){
            AuthActivity.startForResult(this);
            return;
        }

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == AuthActivity.AUTH_REQUEST_CODE){
            String token = data.getStringExtra(AuthActivity.TOKEN_KEY);
            PreferencesProvider.getPreferences().saveToken(token);
            openFragment(MainFragment.newInstance(),false);
        }
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
