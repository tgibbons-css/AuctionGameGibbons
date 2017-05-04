package com.example.donovan.auctiongame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvRound, tvRandom;
    EditText etYourBid;
    Double randomValue;// = 0.0;
    Double rangeMin = 0.0;
    Double rangeMax = 100.0;



    DecimalFormat VALUE_FORMAT = new DecimalFormat("0.##");
    Random r = new Random();

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
    }
}
