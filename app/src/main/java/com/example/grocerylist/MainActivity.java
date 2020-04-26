package com.example.grocerylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.grocerylist.ApplicationClass.switchViews;

public class MainActivity extends AppCompatActivity {

    private static final String MY_SHARED_PREFERENCES_NAME = "com.example.grocerylist";
    private static final String PASSWORD = "Password";
    public Button btnLogin, btnRegister;
    EditText edtPassword;
    //SharedPreferences used to save User data
    public static SharedPreferences myPrefs;
    public static String currentUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtPassword = findViewById(R.id.edtPassword);

        myPrefs = getSharedPreferences(MY_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        currentUserPassword = myPrefs.getString("Password", "");

        if (currentUserPassword.isEmpty()) {
            switchViews(btnRegister,btnLogin);
        } else {
            switchViews(btnLogin,btnRegister);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPasswordValid(edtPassword.getText().toString()))
                {
                    SharedPreferences.Editor editor = myPrefs.edit();
                    editor.putString(PASSWORD, edtPassword.getText().toString().trim());
                    editor.apply();
                    currentUserPassword = myPrefs.getString(PASSWORD,"");
                    edtPassword.setError(null);
                    edtPassword.setText("");
                    switchViews(btnLogin,btnRegister);
                }else
                {
                    edtPassword.setError("Invalid length / pattern");
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPasswordValid(edtPassword.getText().toString()))
                {
                    if(edtPassword.getText().toString().equals(currentUserPassword))
                    {
                       startActivity(new Intent(getApplicationContext(),GroceryLists.class));
                       // startActivity(new Intent(getApplicationContext(),GroceryItems.class));
                    }
                    else
                    {
                        edtPassword.setError("Incorrect password.");
                    }
                }
            }
        });


        validatePasswordInput(edtPassword, btnLogin);

    }

    public static boolean isPasswordValid(String password) {
        return password.length() == 4;
    }

    public static void validatePasswordInput(final EditText etPassword, final Button btnLogin) {
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPasswordValid(s.toString())) {
                    etPassword.setError("(passcode must be 4 characters)");
                    //  btnLogin.setVisibility(View.GONE);
                } else {
                    // btnLogin.setVisibility(View.VISIBLE);
                    etPassword.setError(null);
                }
            }
        });
    }

}
