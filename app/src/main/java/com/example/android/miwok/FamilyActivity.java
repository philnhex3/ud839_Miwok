package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.ListIterator;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int changeFocus) {
            if (changeFocus == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || changeFocus == AudioManager.AUDIOFOCUS_LOSS){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }

            else if (changeFocus == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }

            else if (changeFocus == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener =
            new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Create the array list of family members
        final ArrayList<Word> familyMembers = new ArrayList<Word>();

        //Assign the values
        familyMembers.add(new Word("father", "әpә",
                R.drawable.family_father, R.raw.family_father));
        familyMembers.add(new Word("mother", "әṭa",
                R.drawable.family_mother, R.raw.family_mother));
        familyMembers.add(new Word("son", "angsi",
                R.drawable.family_son, R.raw.family_son));
        familyMembers.add(new Word("daughter", "tune",
                R.drawable.family_daughter, R.raw.family_daughter));
        familyMembers.add(new Word("older brother", "taachi",
                R.drawable.family_older_brother, R.raw.family_older_brother));
        familyMembers.add(new Word("younger brother", "chalitti",
                R.drawable.family_younger_brother, R.raw.family_younger_brother));
        familyMembers.add(new Word("older sister", "teṭe",
                R.drawable.family_older_sister, R.raw.family_older_sister));
        familyMembers.add(new Word("younger sister", "kolliti",
                R.drawable.family_younger_sister, R.raw.family_younger_sister));
        familyMembers.add(new Word("grandmother", "ama",
                R.drawable.family_grandmother, R.raw.family_grandmother));
        familyMembers.add(new Word("grandfather", "paapa",
                R.drawable.family_grandfather, R.raw.family_grandfather));

        //Create the ArrayAdapter
        WordAdapter familyAdapter = new WordAdapter(this, familyMembers,
                R.color.category_family);

        //Find the ListView Object
        ListView listView = (ListView) findViewById(R.id.list);

        //Add the ArrayAdapter to the ListView
        listView.setAdapter(familyAdapter);

        // Create an OnClickListener for each item in the ArrayList
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //Initiate the MediaPlayer on the click of the item in the listView and play
                    //the relevant audio file
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this,
                            familyMembers.get(i).getAudioResourceId());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

                    mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
                }
            }
        });

    }

    @Override
    protected void onStop(){
        super .onStop();

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer (){
        if (mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
