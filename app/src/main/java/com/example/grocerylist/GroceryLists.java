package com.example.grocerylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.grocerylist.ApplicationClass.buildAlertDialog;
import static com.example.grocerylist.ApplicationClass.switchViews;

public class GroceryLists extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private EditText edtNewGroceryList;
    private GroceryListsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_lists);

        GroceryListsDBHelper dbHelper = new GroceryListsDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        final RecyclerView recyclerView = findViewById(R.id.rcvList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroceryListsAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
        edtNewGroceryList = findViewById(R.id.edtNewGroceryList);
        Button btnAddList = findViewById(R.id.btnAddList);


        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
          removeItem((int) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);
        mAdapter.setOnItemClickListener(new GroceryListsAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(int position) {
           // removeItem();

                startActivity(new Intent(getApplicationContext(),GroceryItems.class));
    //showToast(position+" ");

            }
        });

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroceryList();
            }
        });
    }


    private void addGroceryList() {

        if (!edtNewGroceryList.getText().toString().trim().isEmpty()) {

            String name = edtNewGroceryList.getText().toString();
            ContentValues cv = new ContentValues();
            cv.put(GroceryListsContract.GroceryListEntry.COLUMN_NAME, name);
            mDatabase.insert(GroceryListsContract.GroceryListEntry.TABLE_NAME, null, cv);
            mAdapter.swapCursor(getAllItems());
            edtNewGroceryList.getText().clear();
            showToast("Grocery list added successfully");
        }else {
            showToast("Unable to add  grocery list.");
        }

    }

    private void removeItem(long id)
    {
        mDatabase.delete(GroceryListsContract.GroceryListEntry.TABLE_NAME,
                GroceryListsContract.GroceryListEntry._ID + "==" + id,null);
        mAdapter.swapCursor(getAllItems());
        showToast(GroceryListsContract.GroceryListEntry._ID +" ");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                GroceryListsContract.GroceryListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryListsContract.GroceryListEntry.COLUMN_TIMESTAMP + " ASC"
        );
    }

}
