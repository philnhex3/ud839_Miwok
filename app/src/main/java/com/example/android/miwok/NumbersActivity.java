package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    /** Handles playback of all the sound files */
    private MediaPlayer mMediaPlayer;

    /** Handles the audio gain and loss */
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mAudioChangeFocusListener = new OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }

            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }

            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener =
            new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Enable UP Navigation to the Main Activity
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Created the ArrayList of numbers
        final ArrayList<Word> words = new ArrayList<Word>();

        //Assign the values
        words.add(new Word("one", "lutti", R.drawable.number_one,
                R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two,
                R.raw.number_two));
        words.add(new Word("three", "tolookosu",
                R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four,
                R.raw.number_four));
        words.add(new Word("five", "massokka",
                R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six,
                R.raw.number_six));
        words.add(new Word("seven", "kenekaku",
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta",
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine,
                R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten,
                R.raw.number_ten));

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);

        // Create an OnClickListener for each item in the ArrayList
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mAudioChangeFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    //Initiate the MediaPlayer on the click of the item in the listView and play
                    //the relevant audio file
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,
                            words.get(i).getAudioResourceId());

                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop(){
        super .onStop();

        releaseMediaPlayer();
    }

    //Once the audio is finished, we release it to free up application resources
    private void releaseMediaPlayer(){
        //If the mediaPlayer is not null, then it may be currently playing a sound
        if (mMediaPlayer != null) {
            //Regardless of the current state of the media player, release its resources
            //because we no longer need it.
            mMediaPlayer.release();

            //set the mediaPlayer back to null. For the code, we've decided that
            //setting the media player to null is an easy way to tell that the mediia player
            //is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mAudioChangeFocusListener);
            }
        }

        /**
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //Respond to actions bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
        }
        */
}

