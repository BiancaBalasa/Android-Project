package com.example.todolists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editText_username,editText_email;
    Button btnLogin;
    SharedPreferences sharedPreferences;

    //create a name for shared preferences and key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME="username";
    private static final String KEY_EMAIL="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_username = findViewById(R.id.editTextUsername);
        editText_email=findViewById(R.id.editTextEmail);
        btnLogin=findViewById(R.id.buttonLogin);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String username =sharedPreferences.getString(KEY_USERNAME,null);
        String email= sharedPreferences.getString(KEY_EMAIL,null);

        if (username != null && email != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editText_username.getText().toString()) && TextUtils.isEmpty(editText_email.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Please Enter Username and Email", Toast.LENGTH_SHORT).show();
                }else{
                    //when we click the btnLogin, put data in Shared Preferences
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(KEY_USERNAME,editText_username.getText().toString());
                    editor.putString(KEY_EMAIL,editText_email.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }


            }
        });
    }
}





























