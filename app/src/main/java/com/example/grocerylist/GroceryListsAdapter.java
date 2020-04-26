package com.example.grocerylist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroceryListsAdapter extends RecyclerView.Adapter<GroceryListsAdapter.GroceryListsViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemCLick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    GroceryListsAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    static class GroceryListsViewHolder extends RecyclerView.ViewHolder {
        TextView nametext;

        GroceryListsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nametext = itemView.findViewById(R.id.tvGroceryListName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                         int position = getAdapterPosition();
                         if(position != RecyclerView.NO_POSITION)
                         {
                              listener.onItemCLick(position);
                         }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public GroceryListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.customlist, parent, false);
        return new GroceryListsViewHolder(view,mListener );
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListsViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(GroceryListsContract.GroceryListEntry.COLUMN_NAME));
        holder.nametext.setText(name);

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

