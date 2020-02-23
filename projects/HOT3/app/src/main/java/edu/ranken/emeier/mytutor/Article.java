package edu.ranken.emeier.mytutor;

import java.util.Calendar;

public class Article {

    private final String title, author, topic, link;
    private final Calendar publishedDate;

    // THIS IS JUST FOR TEST DATA
    public Article() {
        this.title = "This is a test title!!!";
        this.author = "Random Fella";
        this.topic = "Java";
        this.link = "http://espn.com";
        this.publishedDate = Calendar.getInstance();
    }

    // TEST DATA
    public Article(String author, String topic) {
        this.title = "This is a test title!!!";
        this.author = author;
        this.topic = topic;
        this.link = "http://espn.com";
        this.publishedDate = Calendar.getInstance();
    }

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

    public Article(
            String title,
            String author,
            String topic,
            String link,
            int year,
            int month,
            int day) {
        this.title = title;
        this.author = author;
        this.topic = topic;
        this.link = link;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        this.publishedDate = calendar;
    }

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
