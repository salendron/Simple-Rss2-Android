package at.theengine.android.simple_rss2_android;

public interface Copyable<T> {
    T copy ();
    T createForCopy ();
    void copyTo (T dest);
}
