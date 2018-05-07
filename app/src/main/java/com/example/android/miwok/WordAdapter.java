package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.GONE;

/*
     * {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
     * based on a data source, which is a list of {@link Word} objects.
     * */

public class WordAdapter extends ArrayAdapter<Word> {

    /** Resource ID for the background color for this list of words */
    private int mColorResourceId;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param Word           A List of AndroidFlavor objects to display in a list
     */

    public WordAdapter (Activity context, ArrayList<Word> Word, int colorResourceId){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
    super (context, 0, Word);
    mColorResourceId = colorResourceId;

     }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position      The position in the list of data that should be displayed in the
     *                      list item view.
     * @param colorResourceId is the resource ID for the background color for this list of words
     * @param convertView   The recycled view to populate.
     * @param parent        The parent ViewGroup that is used for inflation.
     * @return              The View for the position in the AdapterView.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check if the existing view is being reused, otherwise inflate the view

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        //Get the {@link Word} object located at this position in the list
        final Word currentWord = getItem(position);

        //find the TextView in the list_item.xml layout with the ID miwok_text
        TextView miwokTextView = (TextView)listItemView.findViewById(R.id.miwok_text);
        //Get the miwok translation for the current word object
        //and set this text on the TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        //find the TextView in the list_item.xml layout with the ID default_text
        TextView defaultTextView = (TextView)listItemView.findViewById(R.id.default_text);
        //Get the default text for the current word object and set it to this TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        //find the ImageView in the list_item.xml layout with the ID icon_img
        ImageView miwokImageView = (ImageView)listItemView.findViewById(R.id.miwok_image);

        //check if the view has an image
        if (currentWord.hasImage()) {
            //IF the view has an image, do the following
            //Get the default image and set it to the source for this image view
            miwokImageView.setImageResource(currentWord.getImageResourceID());
            //Set the visibility to VISIBLE because view are recycled and may inherit
            //other parameters from previous use
            miwokImageView.setVisibility(View.VISIBLE);
        } else {
            //If the view does not have an image, set the visibility to GONE
            miwokImageView.setVisibility(GONE);
        }

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);

        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);

        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
