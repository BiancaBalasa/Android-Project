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

    EditText username, password;
    Button btnLogin, btnRegister;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);
        btnLogin=findViewById(R.id.buttonLogin);
        btnRegister=findViewById(R.id.buttonRegister);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String registeredUsername=sharedPreferences.getString(KEY_USERNAME,"");
        String registeredPassword=sharedPreferences.getString(KEY_PASSWORD,"");

        //if data is available we will go directly to mainActivity
        if ((registeredUsername!="" && registeredPassword!="") || (registeredUsername!= null && registeredPassword!= null)){
            System.out.println(registeredUsername);
            System.out.println(registeredPassword);
            Intent intent =new Intent(LoginActivity.this,ProfileActivity.class);
            startActivity(intent);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue=username.getText().toString();
                String passwordValue=password.getText().toString();

                if(usernameValue.equals(registeredUsername) && passwordValue.equals(registeredPassword)){
                    // asta trebuie aici (pe main activity o sa fie to do listul), dar ca sa testez am pus profileActivity
                    // Intent navigateToMainActivity =new Intent(LoginActivity.this,MainActivity.class);

                    Intent navigateToMainActivity =new Intent(LoginActivity.this,ProfileActivity.class);
                    startActivity(navigateToMainActivity);
                    Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigateToRegisterActivity=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(navigateToRegisterActivity);
            }
        });



    }
}





























