package at.theengine.android.simple_rss2_android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;
import android.widget.ArrayAdapter;

public class SimpleRss2Parser extends SimpleFeedParser {

	private SimpleRss2ParserCallback mCallback;
	
    public SimpleRss2Parser(String feedUrl, SimpleRss2ParserCallback callback) {
        super(feedUrl);
        this.mCallback = callback;
    }
    
    public void parseAsync(){
    	AsyncTask task = new AsyncTask() {

			private Exception mEx;
			private List<RSSItem> items;
			
			@Override
			protected void onPostExecute(Object result) {
				if(mEx != null){
					if(mCallback != null){
						mCallback.onError(mEx);
					}
				} else {
					if(mCallback != null){
						mCallback.onFeedParsed(items);
					}
				}
			}
			
			@Override
			protected Object doInBackground(Object... arg0) {
				try {
					items = parse();
				} catch(Exception e){
					mEx = e;
				}
				
				return null;
			}
		};
		
		task.execute();
    }

    public List<RSSItem> parse() {
        final RSSItem currentMessage = new RSSItem();
        RootElement root = new RootElement("rss");
        final List<RSSItem> messages = new ArrayList<RSSItem>();
        Element channel = root.getChild("channel");
        Element item = channel.getChild(ITEM);
        item.setEndElementListener(new EndElementListener(){
            public void end() {
                messages.add(currentMessage.copy());
            }
        });
        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setTitle(body);
            }
        });
        item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setLink(body);
            }
        });
        item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setDescription(body);
            }
        });
        item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setDate(body);
            }
        });
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messages;
    }
}
