package com.example.android.miwok;

public class Word {

    /** Default translation of the word */
    private String mDefaultTranslation;

    /** Miwok translation of the word */
    private String mMiwokTranslation;

    /** Image of the word */
    private String mImageSrc;

    /** The constructor of the class */
    public Word(String defaultTranslation, String miwokTranslation){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;

    }

    // To get the default translation of the word
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    // To get the Miwok translation of the word
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }


}
