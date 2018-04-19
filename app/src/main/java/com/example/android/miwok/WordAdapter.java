package com.example.android.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
     * {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
     * based on a data source, which is a list of {@link Word} objects.
     * */

public class WordAdapter extends ArrayAdapter<Word> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param Word           A List of AndroidFlavor objects to display in a list
     */

    public WordAdapter (Activity context, ArrayList<Word> Word){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
    super (context, 0, Word);
     }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position      The position in the list of data that should be displayed in the
     *                      list item view.
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
        Word currentWord = getItem(position);

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
        ImageView iconTextView = (ImageView)listItemView.findViewById(R.id.icon_img);
        //Get the default image and set it to the source for this image view

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
