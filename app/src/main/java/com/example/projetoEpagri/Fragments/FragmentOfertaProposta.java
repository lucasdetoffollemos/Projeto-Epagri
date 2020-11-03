package com.example.projetoEpagri.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetoEpagri.R;

public class FragmentOfertaProposta extends Fragment {
    public FragmentOfertaProposta() {}

    public static FragmentOfertaProposta newInstance() {
        FragmentOfertaProposta fragment = new FragmentOfertaProposta();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_oferta_proposta, container, false);
    }
}