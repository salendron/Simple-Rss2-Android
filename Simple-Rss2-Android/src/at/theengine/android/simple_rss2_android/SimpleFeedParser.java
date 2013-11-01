package at.theengine.android.simple_rss2_android;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
        	 HttpURLConnection conn = null;
        	 if (feedUrl.getProtocol().toLowerCase().equals("https")) {
    	        trustAllHosts();
    	        HttpsURLConnection https = (HttpsURLConnection) feedUrl.openConnection();
    	        https.setHostnameVerifier(DO_NOT_VERIFY);
    	        conn = https;
    	     } else {
    	        conn = (HttpURLConnection) feedUrl.openConnection();
    	     }
        	
        	 return conn.getInputStream();
            //return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    //trust ssl
    // always verify the host - dont check for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
          public boolean verify(String hostname, SSLSession session) {
              return true;
          }
   };


    private static void trustAllHosts() {
	  // Create a trust manager that does not validate certificate chains
	  TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return new java.security.cert.X509Certificate[] {};
	        }
	
			@Override
			public void checkClientTrusted(X509Certificate[] arg0,
					String arg1) throws CertificateException {
			}
	
			@Override
			public void checkServerTrusted(X509Certificate[] arg0,
					String arg1) throws CertificateException {
			}
	  } };
	
	  // Install the all-trusting trust manager
	  try {
	          SSLContext sc = SSLContext.getInstance("TLS");
	          sc.init(null, trustAllCerts, new java.security.SecureRandom());
	          HttpsURLConnection
	                          .setDefaultSSLSocketFactory(sc.getSocketFactory());
	  } catch (Exception e) {
	          e.printStackTrace();
	  }
    }
}