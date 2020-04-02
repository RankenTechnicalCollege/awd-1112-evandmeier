package edu.ranken.emeier.hot4.models;

public class Article {

    private String _title, _authorName, _summary, _datePublished, _articleUrl, _imageUrl;

    public Article(
            String title,
            String authorName,
            String summary,
            String datePublished,
            String articleUrl,
            String imageUrl) {

        _title = title;
        _authorName = authorName;
        _summary = summary;
        _datePublished = datePublished;
        _articleUrl = articleUrl;
        _imageUrl = imageUrl;
    }

    public String getTitle() {
        return _title;
    }

    public String getAuthorName() {
        return _authorName;
    }

    public String getSummary() {
        return _summary;
    }

    public String getDatePublished() {
        return _datePublished;
    }

    public String getArticleUrl() {
        return _articleUrl;
    }

    public String getImageUrl() {
        return _imageUrl;
    }
}
