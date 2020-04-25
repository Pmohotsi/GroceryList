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
    public GroceryListsAdapter(Context context, Cursor cursor)
    {
mContext = context ;
mCursor = cursor;
    }

public class GroceryListsViewHolder extends RecyclerView.ViewHolder
{
 public TextView nametext;

    public GroceryListsViewHolder(@NonNull View itemView) {
        super(itemView);
        nametext = itemView.findViewById(R.id.tvGroceryListName);
    }

}

    @NonNull
    @Override
    public GroceryListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.customlist,parent,false);
        return  new GroceryListsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListsViewHolder holder, int position) {
if(!mCursor.moveToPosition(position))
{
    return;
}
String name = mCursor.getString(mCursor.getColumnIndex(GroceryListsContract.GroceryListEntry.COLUMN_NAME));
    holder.nametext.setText(name);

    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor)
    {
        if(mCursor != null)
        {
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor !=null)
        {
            notifyDataSetChanged();
        }
    }
}

