package com.example.donovan.auctiongame;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvRound, tvRandom;
    EditText etYourBid;
    Double randomValue;
    Double rangeMin = 0.0;
    Double rangeMax = 100.0;
    Button buttonSubmit;


    DecimalFormat VALUE_FORMAT = new DecimalFormat("0.##");
    Random r = new Random();

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //TEG added
    public static final String AuctionGameTag = "Auction Game Data";
    DatabaseReference myFishDbRef;
    private String userId;              // Firebase authentication ID for the current logged int user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRound = (TextView)findViewById(R.id.textViewRound);
        tvRandom = (TextView)findViewById(R.id.textViewYourRandom);
        etYourBid = (EditText)findViewById(R.id.editTextYourBid);

        randomValue =  rangeMin + (rangeMax - rangeMin) * r.nextDouble();

        tvRound.setText("This is a test");
        // tvRandom.setText("This is a test.");
        //VALUE_FORMAT.format(randomValue)

        mAuth = FirebaseAuth.getInstance(); //declare object for Firebase
        mAuthListener = new FirebaseAuth.AuthStateListener() { //initialized mAuthListener
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //track the user when they sign in or out using the firebaseAuth
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed out
                    Log.d("CSS3334","onAuthStateChanged - User NOT is signed in");
                    Intent signInIntent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(signInIntent);
                }
            }
        };

        //TEG added
        setupFirebaseDataChange();
        setupAddButton();

    }

    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener); //adds a listener to the object
    }

    public void onStop(){
        super.onStop();
        if (mAuthListener != null) { //checks for an instance of mAuthListener
            mAuth.removeAuthStateListener(mAuthListener);  //if there is, it will be removed
        }
    }

    // TEG added
    private void setupAddButton() {
        // Set up the button to add a new fish using a seperate activity
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Send the user bid to the Firebase database
                // ---- Get a new database key for the vote
                //String key = myFishDbRef.child(AuctionGameTag).push().getKey();
                // ---- write the vote to Firebase
                //myFishDbRef.child("Round 1").child(userId).child(key).setValue(etYourBid.getText());
                Log.d("CIS3334", "onClick for buttons writng bid to database");        // debugging log
                myFishDbRef.child("Round 1").child(userId).setValue(etYourBid.getText().toString());
            }
        });
    }

    private void setupFirebaseDataChange() {
        Log.d("CIS3334", "setupFirebaseDataChange openning the database");        // debugging log
        openDB();
        myFishDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("CIS3334", "Starting onDataChange()");        // debugging log
                checkAutionData(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("CIS3334", "onCancelled: ");
            }
        });
    }

    public DatabaseReference openDB()  {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myFishDbRef = database.getReference(AuctionGameTag);
        // set the user id for the current logged in user
        userId = getUserId(this);
        return myFishDbRef;
    }

    // get the current logged in user's id from Firebase
    private String getUserId(AppCompatActivity activity) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // User is signed out
            Log.d("CSS3334","onAuthStateChanged - User NOT is signed in");
            Intent signInIntent = new Intent(activity, LoginActivity.class);
            activity.startActivity(signInIntent);
            return null;
        } else {
            return user.getUid();
        }
    }

    private void checkAutionData(DataSnapshot dataSnapshot) {
        Log.d("CIS3334", "=== checkAutionData === ");
        //myFishDbRef.child("Round 1").child(userId).setValue(etYourBid.getText().toString());
        for (DataSnapshot data : dataSnapshot.child("Round 1").getChildren()) {
            String bidAmount = (String )data.getValue();
            Log.d("CIS3334", "=== checkAutionData bid amount = "+ bidAmount);
            String UserIdForBid = data.getKey();
            Log.d("CIS3334", "=== checkAutionData  key = "+ UserIdForBid);
            if (UserIdForBid.compareTo(userId)==0) {
                Log.d("CIS3334", "=== checkAutionData Found OUR team's bid ");
            } else {
                Log.d("CIS3334", "=== checkAutionData Found OTHER team's bid ");
            }
        }




    }


}
