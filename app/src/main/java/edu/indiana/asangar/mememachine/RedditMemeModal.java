package edu.indiana.asangar.mememachine;

import org.json.JSONException;
import org.json.JSONObject;

/* RedditMemeModal.java
 *
 * Java class to map Reddit API response
 *
 * Created by: Amol Sangar
 * Created on: 2/24/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 2/27/23
 * Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, referred by all fragments
 **/

interface IMemeModal {
    // Icon
    String getIconURL();
    void setIconURL(String iconUrl);
    // Forum Name
    String getForumName();
    void setForumName(String forumName);
    // Forum URL
    String getForumURL();
    void setForumURL(String forumURL);
    // Author/Creator of image
    String getAuthor();
    void setAuthor(String author);
    // Image to display
    String getImageURL();
    void setImageURL(String imageURL);
    // Likes count
    String getLikesCount();
    void setLikesCount(String likesCount);
    // Caption
    String getCaption();
    void setCaption(String caption);
}

public class RedditMemeModal implements IMemeModal {
    JSONObject jsonObject;
    private String iconUrl;
    private String forumName;
    private String forumUrl;
    private String author;
    private String imageURL;
    private String likesCount;
    private String caption;

    public RedditMemeModal(JSONObject obj) throws JSONException {
        this.jsonObject = obj;
        this.iconUrl = "";
        setForumName(obj.optString("subreddit"));
        setForumURL(obj.optString("postLink"));
        setAuthor(obj.optString("author"));
        setImageURL(obj.optString("url"));
        setLikesCount(obj.optString("ups"));
        setCaption(obj.optString("title"));
    }

    @Override
    public String getIconURL() { return this.iconUrl; }

    @Override
    public void setIconURL(String iconUrl) { this.iconUrl = iconUrl; }

    @Override
    public String getForumName() { return this.forumName; }

    @Override
    public void setForumName(String forumName) { this.forumName = forumName; }

    @Override
    public String getForumURL() { return this.forumUrl; }

    @Override
    public void setForumURL(String forumURL) { this.forumUrl = forumURL; }

    @Override
    public String getAuthor() { return this.author; }

    @Override
    public void setAuthor(String author) { this.author = author; }

    @Override
    public String getImageURL() { return  this.imageURL; }

    @Override
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    @Override
    public String getLikesCount() { return this.likesCount; }

    @Override
    public void setLikesCount(String likesCount) { this.likesCount = likesCount; }

    @Override
    public String getCaption() { return this.caption; }

    @Override
    public void setCaption(String caption) { this.caption = caption; }
}