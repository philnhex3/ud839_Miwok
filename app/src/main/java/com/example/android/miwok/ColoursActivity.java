package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ColoursActivity extends AppCompatActivity {

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

        //Create the ArrayList for the colors
        final ArrayList<Word> colors = new ArrayList<Word>();

        //Assign the values
        colors.add(new Word("red", "weṭeṭṭi", R.drawable.color_red,
                R.raw.color_red));
        colors.add(new Word("green", "chokokki",
                R.drawable.color_green, R.raw.color_green));
        colors.add(new Word("brown", "weṭeṭṭi", R.drawable.color_brown,
                R.raw.color_brown));
        colors.add(new Word("gray", "ṭopoppi", R.drawable.color_gray,
                R.raw.color_gray));
        colors.add(new Word("black", "kululli",
                R.drawable.color_black, R.raw.color_black));
        colors.add(new Word("white", "kelelli",
                R.drawable.color_white, R.raw.color_white));
        colors.add(new Word("dusty yellow", "ṭopiisә",
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colors.add(new Word("mustard yellow", "chiwiiṭә",
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //Create the ArrayAdapter
        WordAdapter colorsAdapter = new WordAdapter(this, colors, R.color.category_colors);

        //Find the ListView Object
        ListView listView = (ListView)findViewById(R.id.list);

        //Make the listView use the WordAdapter
        listView.setAdapter(colorsAdapter);

        // Create an OnClickListener for each item in the ArrayList
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //Initiate the MediaPlayer on the click of the item in the listView and play
                    //the relevant audio file
                    mMediaPlayer = MediaPlayer.create(ColoursActivity.this,
                            colors.get(i).getAudioResourceId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
    }

        @Override
        protected void onStop(){
            super .onStop();

            releaseMediaPlayer();
        }
        private void releaseMediaPlayer(){
            if (mMediaPlayer != null){
                mMediaPlayer.release();
                mMediaPlayer = null;
                mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
        }
}
