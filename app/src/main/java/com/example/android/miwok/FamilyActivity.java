package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.ListIterator;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Create the array list of family members
        ArrayList<Word> familyMembers = new ArrayList<Word>();

        //Assign the values
        familyMembers.add(new Word("father", "әpә"));
        familyMembers.add(new Word("mother", "әṭa"));
        familyMembers.add(new Word("son", "angsi"));
        familyMembers.add(new Word("daughter", "tune"));
        familyMembers.add(new Word("older brother", "taachi"));
        familyMembers.add(new Word("younger brother", "chalitti"));
        familyMembers.add(new Word("older sister", "teṭe"));
        familyMembers.add(new Word("younger sister", "kolliti"));
        familyMembers.add(new Word("grandmother", "ama"));
        familyMembers.add(new Word("grandfather", "paapa"));

        WordAdapter familyAdapter = new WordAdapter(this, familyMembers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(familyAdapter);
    }
}
