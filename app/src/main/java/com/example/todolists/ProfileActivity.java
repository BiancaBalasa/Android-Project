package com.example.todolists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    TextView username,email;
    Button btnLogout;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME="username";
    private static final String KEY_EMAIL="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username=findViewById(R.id.profileUsername);
        email=findViewById(R.id.profileEmail);
        btnLogout=findViewById(R.id.buttonLogout);

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
                editor.commit();
                Toast.makeText(ProfileActivity.this, "Log out successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}