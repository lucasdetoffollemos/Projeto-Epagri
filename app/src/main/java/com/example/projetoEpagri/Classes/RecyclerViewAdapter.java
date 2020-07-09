package com.example.projetoEpagri.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoEpagri.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {
    private List<String> list;

    public class MyView extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyView(View view) {
            super(view);
            //Adicionar todos os elementos que fazem parte do cardview (titulo, os menus dropdown e os edittext).
            textView = (TextView) view.findViewById(R.id.tv_cardTeste);
            //menudropdown
            //edittext;
        }
    }

    public RecyclerViewAdapter(List<String> horizontalList) {
        this.list = horizontalList;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_teste, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}