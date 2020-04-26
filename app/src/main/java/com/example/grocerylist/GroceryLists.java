package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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

        mAdapter.setOnItemClickListener(new GroceryListsAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(int position) {

                startActivity(new Intent(getApplicationContext(),GroceryItems.class));
            }
        });

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroceryList();
            }
        });
    }

    public void changeText1(String text)
    {

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
