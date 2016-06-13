package com.lzf.letscook.net;

import rx.Subscriber;

/**
 * Created by liuzhaofeng on 16/5/9.
 */
public class SubscriberHolder<T> {

//    private SoftReference<Subscriber<? super T>> mSubscriberRef;

//    public void setSubscriber(Subscriber<? super T> subscriber){
//        if(subscriber != null ){
//            mSubscriberRef = new SoftReference<Subscriber<? super T>>(subscriber);
//        }
//    }
//
//    public Subscriber<? super T> getSubscriber(){
//        if(mSubscriberRef != null){
//            return mSubscriberRef.get();
//        }
//        return null;
//    }

    private Subscriber<? super T> mSub;

    public void setSubscriber(Subscriber<? super T> subscriber){
        this.mSub = subscriber;
    }

    public Subscriber<? super T> getSubscriber(){
        return this.mSub;
    }

}
