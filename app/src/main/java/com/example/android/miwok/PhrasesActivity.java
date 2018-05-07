package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

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
            mMediaPlayer.release();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Create the ArrayList
        final ArrayList<Word> phrases = new ArrayList<Word>();

        //Assign the values
        phrases.add(new Word("Where are you going?", "minto wuksus",
                R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?", "tinnә oyaase'nә",
                R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is...", "oyaaset...",
                R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?", "michәksәs?",
                R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.", "kuchi achit",
                R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?", "әәnәs'aa?",
                R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.", "hәә’ әәnәm",
                R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I'm coming", "әәnәm",
                R.raw.phrase_im_coming));
        phrases.add(new Word("Let’s go.", "yoowutis",
                R.raw.phrase_lets_go));
        phrases.add(new Word("Come here.", "әnni'nem",
                R.raw.phrase_come_here));

        //Create the ArrayAdapter
        WordAdapter phrasesAdapter = new WordAdapter(this, phrases, R.color.category_phrases);

        //Find the ListView
        ListView listView = (ListView)findViewById(R.id.list);

        //Add the ArrayAdapter to the ListView
        listView.setAdapter(phrasesAdapter);

        // Create an OnClickListener for each item in the ArrayList
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                //Initiate the MediaPlayer on the click of the item in the listView and play
                //the relevant audio file

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,
                            phrases.get(i).getAudioResourceId());
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
