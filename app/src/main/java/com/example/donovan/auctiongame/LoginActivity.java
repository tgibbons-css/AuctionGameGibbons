package com.example.donovan.auctiongame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Donovan on 04/05/17.
 */

public class LoginActivity extends AppCompatActivity {

    EditText Email, Password;
    Button ButtonLogin, CreateUser;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (EditText) findViewById(R.id.editTextEmail);
        Password = (EditText) findViewById(R.id.editTextPassword);
        ButtonLogin = (Button) findViewById(R.id.buttonLogin);
        CreateUser = (Button) findViewById(R.id.buttonCreateUser);



        private void signIn(String email, String password){
            //sign in the recurrent user with email and password previously created.
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //add to listener
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) { //when failed
                        Toast.makeText(LoginActivity.this, "SignIn--Authentication failed.",Toast.LENGTH_LONG).show();
                    } else {
                        //return to MainActivity is login works
                        finish();
                    }
                }
            });
        }

        private void createAccount(String email, String password) {
            //create account for new users
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {  //update listener.
                    if (!task.isSuccessful()) { //when failed
                        Toast.makeText(LoginActivity.this, "createAccount--Authentication failed.", Toast.LENGTH_LONG).show();
                    } else {
                        //return to MainActivity is login works
                        finish();
                    }
                }
            });
        }
    }

}
