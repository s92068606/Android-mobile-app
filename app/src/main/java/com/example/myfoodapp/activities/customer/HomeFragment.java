package com.example.myfoodapp.activities.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.myfoodapp.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button goToActivityButton = rootView.findViewById(R.id.go_to_activity_button);
        goToActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Dashboard the activity
                Intent intent = new Intent(getActivity(), Dashboard.class);
                startActivity(intent);
            }
        });

        return rootView;

    }

}
