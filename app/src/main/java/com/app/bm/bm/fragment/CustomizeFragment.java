package com.app.bm.bm.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.bm.bm.MainActivity;
import com.app.bm.bm.R;
import com.app.bm.bm.activity.customzie.Customize1Activity;
import com.app.bm.bm.activity.customzie.Customize2Activity;

public class CustomizeFragment extends Fragment {

    private Button btnStartCustomize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancestate){
        View rootView = inflater.inflate(R.layout.fragment_customize,null);

        btnStartCustomize  = (Button)rootView.findViewById(R.id.start_my_customize);
        btnStartCustomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(getActivity(),Customize1Activity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
                */

                Intent intent = new Intent(getActivity(),Customize1Activity.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        return rootView;
    }
}