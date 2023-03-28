package com.example.todolists;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnLogin, btnRegister;
    SharedPreferences sharedPreferences;

    //google login
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    ImageButton btnGoogle;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //login with shared preferences
        username=findViewById(R.id.editTextUsername);
        password=findViewById(R.id.editTextPassword);
        btnLogin=findViewById(R.id.buttonLogin);
        btnRegister=findViewById(R.id.buttonRegister);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String registeredUsername=sharedPreferences.getString(KEY_USERNAME,"");
        String registeredPassword=sharedPreferences.getString(KEY_PASSWORD,"");

        //google login
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
         gsc = GoogleSignIn.getClient(this, gso);
    //test
         //if you didn't logout than go directly to profile activity(google login)
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            Intent intent =new Intent(LoginActivity.this,ProfileActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        //if data is available we will go directly to mainActivity
        if (!registeredUsername.equals("") && !registeredPassword.equals("")){
            Intent intent =new Intent(LoginActivity.this,ProfileActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameValue=username.getText().toString();
                String passwordValue=password.getText().toString();

                if(usernameValue.equals(registeredUsername) && passwordValue.equals(registeredPassword) && !TextUtils.isEmpty(usernameValue) && !TextUtils.isEmpty(passwordValue)){

                    // asta trebuie aici (pe main activity o sa fie to do listul), dar ca sa testez am pus profileActivity
                    // Intent navigateToMainActivity =new Intent(LoginActivity.this,MainActivity.class);

                    Intent navigateToMainActivity =new Intent(LoginActivity.this,ProfileActivity.class);
                    startActivity(navigateToMainActivity);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });


        //google login
        btnGoogle=findViewById(R.id.buttonGoogle);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent=gsc.getSignInIntent();
                // we can send information from one activity to another and vice-versa ;
                // this method requires a result from the second activity (activity to be invoked).
                //in such case, we need to override the onActivityResult method that is invoked automatically when second activity returns result.
                startActivityForResult(logInIntent,1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Signed in successfully, show authenticated UI.
                task.getResult(ApiException.class);
                finish();
                Intent intent=new Intent(LoginActivity.this,ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }
    }


}





























