package com.lzf.letscook.net;

import java.lang.ref.SoftReference;

import rx.Subscriber;

/**
 * Created by liuzhaofeng on 16/5/9.
 */
public class SubscriberHolder<T> {

    private SoftReference<Subscriber<? extends T>> mSubscriberRef;

    public void setSubscriber(Subscriber<? extends T> subscriber){
        if(subscriber != null ){
            mSubscriberRef = new SoftReference<Subscriber<? extends T>>(subscriber);
        }
    }

    public Subscriber<? extends T> getSubscriber(){
        if(mSubscriberRef != null){
            return mSubscriberRef.get();
        }
        return null;
    }

}
