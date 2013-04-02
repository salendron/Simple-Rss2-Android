package at.theengine.android.simple_rss2_android;

import java.util.List;

public abstract class SimpleRss2ParserCallback {

	public abstract void onFeedParsed(List<RSSItem> items);

	public abstract void onError(Exception ex);
}
