package com.example.mealy.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealy.R;

/**
 * Contains a list of days in a grid to choose from
 * hard coded , use gridview, top level view should be ScrollView/NestedScrollView
 */
public class DayScreen extends Fragment {

    public DayScreen() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_screen, container, false);
    }
}