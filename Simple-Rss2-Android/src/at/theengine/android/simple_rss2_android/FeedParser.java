package at.theengine.android.simple_rss2_android;

import java.util.List;

public interface FeedParser {
    List<RSSItem> parse();
}
