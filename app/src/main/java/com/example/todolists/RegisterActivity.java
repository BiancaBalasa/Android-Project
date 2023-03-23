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

public class RegisterActivity extends AppCompatActivity {

    EditText username,email,password;
    Button btnRegister;

    SharedPreferences sharedPreferences;

    //create a name for shared preferences and key name
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME="username";
    private static final String KEY_EMAIL="email";
    private static final String KEY_PASSWORD="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.editTextNewUsername);
        email=findViewById(R.id.editTextNewEmail);
        password=findViewById(R.id.editTextNewPassword);
        btnRegister=findViewById(R.id.buttonNewRegister);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue=username.getText().toString();
                String emailValue=email.getText().toString();
                String passwordValue=password.getText().toString();

                if(TextUtils.isEmpty(usernameValue) || TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(passwordValue)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Username, Email and Password!", Toast.LENGTH_SHORT).show();
                }else {
                    //when we click the btnRegister, put data in Shared Preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME, usernameValue);
                    editor.putString(KEY_EMAIL, emailValue);
                    editor.putString(KEY_PASSWORD, passwordValue);
                    editor.apply();

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "Register successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }



            }
        });
    }
}





















