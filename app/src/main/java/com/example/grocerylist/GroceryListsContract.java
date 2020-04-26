package com.example.grocerylist;

 class GroceryListsContract {

    private GroceryListsContract() {
    }

    static final class GroceryListEntry {
        static final String TABLE_NAME = "groceryList";
        static final String COLUMN_NAME = "name";
        public static final int _ID = 0;
        static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
