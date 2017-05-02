package com.example.android.guardiannews;

/**
 * Created by J on 4/5/2017.
 *
 * An {@link Article} object to populate the ListViews of the Fragments for each news source
 */

class Article {

    // The section of the paper of the article
    private final String mSection;

    // The author of the article
    private final String mAuthor;

    // The title of the article
    private final String mTitle;

    // The description (or blurb) of the article
    private final String mDescription;

    // The URL of the article in String format
    private final String mArticleUrl;

    // The URL of the article's image in String format
    private final String mArticleImageUrl;

    // The time and date that the article was published (YYYY-MM-DD"T"HH:MM:SS"Z")
    private final String mPublishedDate;

    /**
     * An {@link Article} object with all of the params retrieved from a JSON response
     *
     * @param section         section of the paper of the article
     * @param author          author of the article
     * @param title           title of the article
     * @param description     description (or blurb) of the article
     * @param articleUrl      URL of the article on the internet
     * @param articleImageUrl URL of the image of the article on the internet
     * @param publishedDate   published date and time of the article (YYYY-MM-DD"T"HH:MM:SS"Z")
     */
    public Article(String section, String author, String title, String description, String articleUrl,
                   String articleImageUrl, String publishedDate) {
        mSection = section;
        mAuthor = author;
        mTitle = title;
        mDescription = description;
        mArticleUrl = articleUrl;
        mArticleImageUrl = articleImageUrl;
        mPublishedDate = publishedDate;
    }

    // All the getters for an Article Object
    public String getSection() {return mSection;}

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public String getArticleImageUrl() {
        return mArticleImageUrl;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }
}