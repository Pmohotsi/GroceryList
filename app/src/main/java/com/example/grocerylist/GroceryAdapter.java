package com.example.grocerylist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private OnListClickListener mListener;

    GroceryAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

    }
    public interface OnListClickListener
    {
        void onItemCLick(int position);
    }
    public void setOnListClickListener(GroceryAdapter.OnListClickListener listener)
    {
        mListener = listener;
    }

    static class GroceryViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView countText;

        GroceryViewHolder(@NonNull View itemView, final OnListClickListener listener) {
            super(itemView);
            nameText = itemView.findViewById(R.id.tvGroceryItemName);
            countText = itemView.findViewById(R.id.tvGroceryItemQuantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemCLick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.customitems, parent, false);


        return new GroceryViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(GroceryContract.GroceryEntry.COLUMN_AMOUNT));
        long id = mCursor.getLong(mCursor.getColumnIndex(GroceryContract.GroceryEntry._ID));

        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
