package com.example.todolists;

import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ProfileFragment extends Fragment {

    TextView username, email;
    Button btnLogout;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";

    //google login
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        username = rootView.findViewById(R.id.profileUsername);
        email = rootView.findViewById(R.id.profileEmail);
        btnLogout = rootView.findViewById(R.id.buttonLogout);

        //logged in with google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(requireContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
        if (account != null) {
            String googleUsername = account.getDisplayName();
            String googleEmail = account.getEmail();
            username.setText("Username - " + googleUsername);
            email.setText("Email - " + googleEmail);
        }

        //logged in with shared preferences
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, requireContext().MODE_PRIVATE);

        String usernameValue = sharedPreferences.getString(KEY_USERNAME, null);
        String emailValue = sharedPreferences.getString(KEY_EMAIL, null);

        if (usernameValue != null || emailValue != null) {
            username.setText("Username - " + usernameValue);
            email.setText("Email - " + emailValue);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                requireActivity().finish();
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Toast.makeText(requireContext(), "Log out successfully!", Toast.LENGTH_SHORT).show();

                //if i am logged in with google
                if(account!=null){
                    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete( Task<Void> task) {
                            requireActivity().finish();
                            startActivity(new Intent(requireContext(),LoginActivity.class));
                        }
                    });
                }
            }
        });

        return rootView;
    }
}
