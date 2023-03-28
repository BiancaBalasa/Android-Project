package com.example.todolists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileActivity extends AppCompatActivity {

    TextView username,email;
    Button btnLogout;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME="username";
    private static final String KEY_EMAIL="email";

    //google login
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username=findViewById(R.id.profileUsername);
        email=findViewById(R.id.profileEmail);
        btnLogout=findViewById(R.id.buttonLogout);

        //logged in with google
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            String googleUsername= account.getDisplayName();
            String googleEmail=account.getEmail();
            username.setText("Username - "+googleUsername);
            email.setText("Email - "+googleEmail);
        }

        //logged in with shared preferences
        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String usernameValue =sharedPreferences.getString(KEY_USERNAME,null);
        String emailValue= sharedPreferences.getString(KEY_EMAIL,null);

        if(usernameValue!=null || emailValue!=null ){
            username.setText("Username - "+usernameValue);
            email.setText("Email - "+emailValue);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  SharedPreferences.Editor editor = sharedPreferences.edit();
                  editor.clear();
                  editor.apply();
                  finish();
                  Intent intent =new Intent(ProfileActivity.this,LoginActivity.class);
                  startActivity(intent);
                  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                  Toast.makeText(ProfileActivity.this, "Log out successfully!", Toast.LENGTH_SHORT).show();

                  //if i am logged in with google
                  if(account!=null){
                    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete( Task<Void> task) {
                            finish();
                            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                        }
                    });
               }
            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}