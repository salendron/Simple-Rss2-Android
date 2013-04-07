package at.theengine.android.simple_rss2_android.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import at.theengine.android.simple_rss2_android.R;
import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;

public class MainActivity extends Activity {

	private Context mContext;
	
	private EditText etFeedUrl;
	private Button btnLoad;
	private ListView lvFeedItems;
	
	private SimpleRss2ParserCallback mCallback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = this;
		
		initViews();
	}
	
	private SimpleRss2ParserCallback getCallback(){
		if(mCallback == null){
			mCallback = new SimpleRss2ParserCallback() {
				
				@Override
				public void onFeedParsed(List<RSSItem> items) {
					for(int i = 0; i < items.size(); i++){
						Log.d("SimpleRss2ParserDemo",items.get(i).getTitle());
					}
					
					lvFeedItems.setAdapter(
							new MyListAdapter(mContext,R.layout.list_item,(ArrayList<RSSItem>) items)
							);
				}
				
				@Override
				public void onError(Exception ex) {
					Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
				}
			};
		}
		
		 return mCallback;
	}

	private void initViews(){
		etFeedUrl = (EditText) findViewById(R.id.etFeedUrl);
		btnLoad = (Button) findViewById(R.id.btnLoad);
		lvFeedItems = (ListView) findViewById(R.id.lvFeedItems);
		
		btnLoad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SimpleRss2Parser parser = new SimpleRss2Parser(etFeedUrl.getText().toString(), getCallback());
				parser.parseAsync();
			}
		});
	}
	
	private class MyListAdapter extends ArrayAdapter<RSSItem> {

        private ArrayList<RSSItem> items;
        private Context ctx;
        private int layout;

        public MyListAdapter(Context context, int layout, ArrayList<RSSItem> items) {
                super(context, layout, items);
                this.items = items;
                this.ctx = context;
                this.layout = layout;
        }
        
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(layout, null);
                }
                
                RSSItem o = items.get(position);
                if (o != null) {
                	TextView tvPubDate = ((TextView) v.findViewById(R.id.tvPubDate));
                	TextView tvTitle = ((TextView) v.findViewById(R.id.tvTitle));
                	TextView tvDescription = ((TextView) v.findViewById(R.id.tvDescription));
                	TextView tvLnk = ((TextView) v.findViewById(R.id.tvLnk));
                	
                	if (tvPubDate != null) {
                		tvPubDate.setText(o.getDate());
                    }
                	
                	if (tvTitle != null) {
                    	tvTitle.setText(o.getTitle());
                    }
                	
                	if (tvDescription != null) {
                    	tvDescription.setText(o.getContent());
                    }
                	
                	if (tvLnk != null) {
                    	tvLnk.setText(o.getLink().toExternalForm());
                    	Linkify.addLinks(tvLnk, Linkify.ALL);
                    }
                }
                
                return v;
        }
    }  
}
