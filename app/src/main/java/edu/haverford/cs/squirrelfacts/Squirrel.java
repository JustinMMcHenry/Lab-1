package edu.haverford.cs.squirrelfacts;

/**
 * Documents various facts about a squirrel
 */
public class Squirrel {

    private int mId=0;
    // The Squirrel's name
    private String mName;

    // Where the Squirrel resides
    private String mLocation;

    // A URL pointing to a picture of the squirrel
    private String mPicture;

    public Squirrel(String name, String location, String url) {
        mName = name;
        mLocation = location;
        mPicture = url;
    }

    public void setId(int i){mId = i;}
    public int getId(){return mId;}

    @Override
    public boolean equals(Object b) {
        if (b instanceof Squirrel) {
            Squirrel s = (Squirrel)b;
            return mName == s.mName
                    && mLocation == s.mLocation
                    && mPicture == s.mPicture;
        } else {
            return false;
        }
    }

    // Getters
    public String getName() { return mName; }
    public String getLocation() { return mLocation; }
    public String getPicture() { return mPicture; }
}
