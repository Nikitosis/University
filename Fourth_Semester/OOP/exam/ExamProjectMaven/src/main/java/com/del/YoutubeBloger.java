package com.del;

import java.util.List;

public class YoutubeBloger {
    private List<YoutubeSubscriber> subscribers;

    public void uploadVideo() {
        //upload video
        //notify subscribers
        for(YoutubeSubscriber subscriber: subscribers) {
            subscriber.showNotification();
        }
    }

    public void subscribe(YoutubeSubscriber subscriber) {
        subscribers.add(subscriber);
    }
}
