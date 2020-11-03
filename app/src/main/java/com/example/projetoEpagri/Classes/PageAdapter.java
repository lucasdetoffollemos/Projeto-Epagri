package com.example.projetoEpagri.Classes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.projetoEpagri.Fragments.FragmentDemandaAtual;
import com.example.projetoEpagri.Fragments.FragmentDemandaProposta;
import com.example.projetoEpagri.Fragments.FragmentOfertaAtual;
import com.example.projetoEpagri.Fragments.FragmentOfertaProposta;

public class PageAdapter extends FragmentStateAdapter {
    int numTabs;

    public PageAdapter(FragmentActivity fragmentActivity, int numTabs){
        super(fragmentActivity);
        this.numTabs = numTabs;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragmentOfertaAtual();
            case 1:
                return new FragmentDemandaAtual();
            case 2:
                return new FragmentOfertaProposta();
            case 3:
                return new FragmentDemandaProposta();
            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return this.numTabs;
    }
}
