package com.example.vitaliybelyaev.galleryfrag;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PageFragment extends Fragment {

    private TextView mockMessageTextView;
    private static final String ARGS_MESSAGE = "message";
    private MeassageFragmentListener listener;
    private Button showNextButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page,
                container,false);

        mockMessageTextView = rootView.findViewById(R.id.mock_message);


        mockMessageTextView.setText("ddddddddddd");
        return rootView;
    }

    public static PageFragment newInstance(String message) {
        PageFragment pageFragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_MESSAGE, message);
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MeassageFragmentListener) {
            listener = (MeassageFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }
}
