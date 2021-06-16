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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.WordViewHolder>
{
    private final LinkedList<String> foods;
    private final LinkedList<String> count;
    private final LinkedList<String> price;

    private LayoutInflater mInflater;
    public static final String EXTRA_MESSAGE = "extra.MESSAGE";

    public CartAdapter(Context context, LinkedList<String> foods, LinkedList<String> count,LinkedList<String> price) {
        mInflater = LayoutInflater.from(context);
        this.foods = foods;
        this.count = count;
        this.price = price;

    }

    @NonNull
    @Override
    public CartAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = mInflater.inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(CartAdapter.WordViewHolder holder, int position) {
        String mCurrentName = foods.get(position);
        String mCurrentCount = count.get(position);
        String mCurrentPrice = price.get(position);

        holder.wordItemView.setText(mCurrentName);
        holder.wordItemView2.setText(mCurrentCount);
        holder.wordItemView3.setText("$"+mCurrentPrice);

    }
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;
        public final TextView wordItemView2;
        public final TextView wordItemView3;
        final CartAdapter mAdapter;

        public WordViewHolder(View itemView, CartAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.cart_title);
            wordItemView2 = itemView.findViewById(R.id.cart_count);
            wordItemView3 = itemView.findViewById(R.id.cart_price);

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
