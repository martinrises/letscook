package com.lzf.letscook.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzf.letscook.entity.Material;
import com.lzf.letscook.net.SubscriberHolder;
import com.lzf.letscook.system.ShopSystem;
import com.lzf.letscook.util.rx.SubcriberAdapter;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by liuzhaofeng on 16/6/11.
 */
public class ShopItemView extends LinearLayout {
    private Material mMaterial;

    private TextView mNameTv;
    private TextView mNumTv;

    private Paint mPaint;
    private ProgressDialogFragment mProgressDialog;

    public ShopItemView(Context context) {
        this(context, null);
    }

    public ShopItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);

        mNameTv = new TextView(getContext());
        LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        addView(mNameTv, lp);

        mNumTv = new TextView(getContext());
        addView(mNumTv, lp);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        initClickObserver();
    }

    private void initClickObserver() {

        final SubscriberHolder<View> subHolder = new SubscriberHolder();
        Observable<View> ob = Observable.create(new Observable.OnSubscribe<View>() {
            @Override
            public void call(Subscriber<? super View> subscriber) {
                subHolder.setSubscriber(subscriber);
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Subscriber<? super View> sub = subHolder.getSubscriber();
                sub.onNext(v);
            }
        });

        ob.flatMap(new Func1<View, Observable<View>>() {
            @Override
            public Observable<View> call(View view) {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialogFragment();
                }
                mProgressDialog.showPd(((FragmentActivity)getContext()).getSupportFragmentManager(), "操作中..");
                return Observable.just(view);
            }
        }).flatMap(new Func1<View, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(View view) {

                if (mMaterial.isBuyed()) {
                    return ShopSystem.getInstance().unbuyMaterial(mMaterial.get_id(), mMaterial.isMajor());
                }else{
                    return ShopSystem.getInstance().buyMaterial(mMaterial.get_id(), mMaterial.isMajor());
                }
            }
        }).flatMap(new Func1<Boolean, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(Boolean hasBuySuccess) {

                mProgressDialog.dismiss();
                return Observable.just(hasBuySuccess);
            }
        }).subscribe(new SubcriberAdapter<Boolean>(){
            @Override
            public void onNext(Boolean aBoolean) {
                if(aBoolean){
                    mMaterial.setBuyed(!mMaterial.isBuyed());
                    invalidate();
                }
            }
        });


    }

    public void setMaterial(Material m){
        mMaterial = m;

        mNameTv.setText(mMaterial.getTitle());
        mNumTv.setText(mMaterial.getNote());

        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if(mMaterial != null){
            if(mMaterial.isBuyed()){

                int width = getWidth();
                int height = getHeight();

                canvas.drawLine(0, height / 2, width, height / 2, mPaint);
            }
        }
    }

}
