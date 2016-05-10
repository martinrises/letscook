package com.lzf.letscook.util.rx;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by liuzhaofeng on 16/5/9.
 */
public class OnSubcriberAdapter<T> implements Observable.OnSubscribe<T> {
    @Override
    public void call(Subscriber<? super T> subscriber) {

    }
}
