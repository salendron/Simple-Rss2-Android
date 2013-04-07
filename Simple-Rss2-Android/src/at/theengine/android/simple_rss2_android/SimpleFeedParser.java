package at.theengine.android.simple_rss2_android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class SimpleFeedParser implements FeedParser {

    static final String PUB_DATE = "pubDate";
    static final  String DESCRIPTION = "description";
    static final  String CONTENT = "content";
    static final  String LINK = "link";
    static final  String TITLE = "title";
    static final  String ITEM = "item";
    
    final URL feedUrl;

    protected SimpleFeedParser(String feedUrl){
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}