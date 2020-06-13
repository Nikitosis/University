package com.del;

public abstract class YoutubeSubscriber {
    public abstract void showNotification();
    public void subscribe(YoutubeBloger bloger) {
        bloger.subscribe(this);
    }
}
