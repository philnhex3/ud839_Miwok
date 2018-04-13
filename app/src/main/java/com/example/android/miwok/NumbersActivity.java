package com.example.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers2);

        //Created the ArrayList of numbers
        ArrayList<String> numbers = new ArrayList<String>();

        //Assign the values
        numbers.add("one");
        numbers.add("two");
        numbers.add("three");
        numbers.add("four");
        numbers.add("five");
        numbers.add("six");
        numbers.add("seven");
        numbers.add("eight");
        numbers.add("nine");
        numbers.add("ten");

        Log.v("NumbersActivity", "Word at index 0: " + numbers.get(1));
        Log.v("NumbersActivity", "Word at index 1: " + numbers.get(2));
        Log.v("NumbersActivity", "Word at index 2: " + numbers.get(3));
    }
}
