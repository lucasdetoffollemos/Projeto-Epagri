package com.example.projetoEpagri.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetoEpagri.R;

public class FragmentDemandaProposta extends Fragment {

    public FragmentDemandaProposta() {}

    public static FragmentDemandaProposta newInstance() {
        FragmentDemandaProposta fragment = new FragmentDemandaProposta();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_demanda_proposta, container, false);
    }
}