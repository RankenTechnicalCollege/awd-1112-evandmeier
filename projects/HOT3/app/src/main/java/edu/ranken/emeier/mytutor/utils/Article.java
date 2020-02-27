package edu.ranken.emeier.mytutor.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Article implements Parcelable {

    private final String title, author, topic, link;
    private final Calendar publishedDate;

    public Article(
            String title,
            String author,
            String topic,
            String link,
            Calendar publishedDate) {
        this.title = title;
        this.author = author;
        this.topic = topic;
        this.link = link;
        this.publishedDate = publishedDate;
    }

    protected Article(Parcel in) {
        title = in.readString();
        author = in.readString();
        topic = in.readString();
        link = in.readString();
        publishedDate = (java.util.Calendar) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(topic);
        dest.writeString(link);
        dest.writeSerializable(publishedDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTopic() {
        return topic;
    }

    public String getLink() {
        return link;
    }

    public Calendar getPublishedDate() {
        return publishedDate;
    }
}
