package at.theengine.android.simple_rss2_android;

import java.net.MalformedURLException;
import java.net.URL;

public class RSSItem implements Comparable<RSSItem>, Copyable<RSSItem>{
    private String title;
    private URL link;
    private String description;
    private String content;
    private String date;
    
    public void setContent(String content){
    	this.content = content;
    }
    
    public String getContent(){
    	return this.content;
    }
    
    public void setTitle(String title){
    	this.title = title;
    }
    
    public String getTitle(){
    	return this.title;
    }
    
    public void setDescription(String description){
    	this.description = description;
    }
    
    public String getDescription(){
    	return this.description;
    }
    
    public void setLink(String link) {
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public URL getLink(){
    	return this.link;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public boolean equals(Object obj) {
            return super.equals(obj);
    }
      // sort by date
    public int compareTo(RSSItem another) {
        if (another == null) return 1;
        return another.date.compareTo(date);
    }

	@Override
	public RSSItem copy() {
		RSSItem copy = createForCopy();
        copyTo(copy);
        return copy;
	}

	@Override
	public RSSItem createForCopy() {
		return new RSSItem();
	}

	@Override
	public void copyTo(RSSItem dest) {
		dest.setTitle(title);
		dest.setDescription(description);
		dest.setLink(link.toExternalForm());
		dest.setDate(date);
		dest.setContent(content);
	}
}
