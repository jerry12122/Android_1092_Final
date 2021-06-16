package com.example.android_1092_final;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder>
{
    private final LinkedList<String> foods;
    private final LinkedList<String> desc;
    private LayoutInflater mInflater;
    public static final String EXTRA_MESSAGE = "extra.MESSAGE";

    @NonNull
    @Override
    public WordAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        String mCurrentName = foods.get(position);
        String mCurrentDesc = desc.get(position);
        holder.wordItemView.setText(mCurrentName);
        holder.wordItemView2.setText(mCurrentDesc);
    }

    public WordAdapter(Context context, LinkedList<String> foods, LinkedList<String> desc) {
        mInflater = LayoutInflater.from(context);
        this.foods = foods;
        this.desc = desc;
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;
        public final TextView wordItemView2;
        final WordAdapter mAdapter;

        public WordViewHolder(View itemView, WordAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.title);
            wordItemView2 = itemView.findViewById(R.id.describe);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            Intent intent = new Intent(v.getContext(), recipe.class);
            intent.putExtra(EXTRA_MESSAGE, mPosition);
            v.getContext().startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}