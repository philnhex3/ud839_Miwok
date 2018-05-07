package com.example.android.miwok;

public class Word {

    /** Default translation of the word */
    private String mDefaultTranslation;

    /** Miwok translation of the word */
    private String mMiwokTranslation;

    /** Image of the word */
    private int mImageResourceID = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /** Audio file associated with the word */
    private int mAudioResourceId;

    /** The constructor of the class */
    public Word(String defaultTranslation, String miwokTranslation){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceID,
                int audioResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceID = imageResourceID;
        mAudioResourceId = audioResourceId;
    }

    // To get the default translation of the word
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    // To get the Miwok translation of the word
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    // To get the image describing the word
    public int getImageResourceID(){ return mImageResourceID; }

    //Returns whether or not a view has an image
    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }

    //To get the resource ID of the audio file
    public int getAudioResourceId(){return mAudioResourceId;}



}
