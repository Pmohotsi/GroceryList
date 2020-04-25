package com.example.grocerylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GroceryLists extends AppCompatActivity {
private SQLiteDatabase mDatabase;
    private EditText edtNewGroceryList;
    private GroceryListsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_lists);

        GroceryListsDBHelper  dbHelper = new GroceryListsDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
       RecyclerView recyclerView = findViewById(R.id.rcvList);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
mAdapter = new GroceryListsAdapter(this,getAllItems());


   recyclerView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

       }
   });



recyclerView.setAdapter(mAdapter);
        edtNewGroceryList = findViewById(R.id.edtNewGroceryList);
        Button btnAddList = findViewById(R.id.btnAddList);

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroceryList();
            }
        });
    }
    private void addGroceryList()
    {
        if(!edtNewGroceryList.getText().toString().trim().isEmpty())
        {

            String name = edtNewGroceryList.getText().toString();
            ContentValues cv = new ContentValues();
            cv.put(GroceryListsContract.GroceryListEntry.COLUMN_NAME,name);
            mDatabase.insert(GroceryListsContract.GroceryListEntry.TABLE_NAME,null,cv);
            mAdapter.swapCursor(getAllItems());
            edtNewGroceryList.getText().clear();
        }
        Toast.makeText(this, "Please enter grocery list name", Toast.LENGTH_SHORT).show();


    }

    private Cursor getAllItems()
    {
        return  mDatabase.query(
                GroceryListsContract.GroceryListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                GroceryListsContract.GroceryListEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}
