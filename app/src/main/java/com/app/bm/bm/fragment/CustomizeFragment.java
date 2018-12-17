package com.app.bm.bm.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.bm.bm.R;

public class CustomizeFragment extends Fragment {

    private Button btnStartCustomize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.fragment_customize,null);

        btnStartCustomize  = (Button)rootView.findViewById(R.id.start_my_customize);
        btnStartCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return rootView;
    }
}