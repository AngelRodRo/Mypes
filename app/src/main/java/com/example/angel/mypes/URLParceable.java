package com.example.angel.mypes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by angel on 18/02/15.
 */
public class URLParceable implements Parcelable {

    public String url;

    public URLParceable(Parcel source) {
        url = source.readString();
    }

    public URLParceable(String _url) {
        this.url=_url;
    }

    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public URLParceable createFromParcel(Parcel in) {
            return new URLParceable(in);
        }

        public URLParceable[] newArray(int size) {
            return new URLParceable[size];
        }
    };

}