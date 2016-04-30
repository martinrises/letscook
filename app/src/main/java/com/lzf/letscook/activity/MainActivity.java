package com.lzf.letscook.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lzf.letscook.R;
import com.lzf.letscook.entity.Recipe;
import com.lzf.letscook.system.CookSystem;
import com.lzf.letscook.util.Logger;

import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        final Button btn = (Button) findViewById(R.id.btn_get);
        Observable<View> btnGetObservable = Observable.create(new Observable.OnSubscribe<View>() {
            @Override
            public void call(final Subscriber<? super View> subscriber) {

                if(btn == null){
                    return;
                }
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }
                        subscriber.onNext(v);
                    }
                });
            }
        });

        btnGetObservable.map(new Func1<View, List<Recipe>>() {
            @Override
            public List<Recipe> call(View view) {
                try {
                    return CookSystem.getInstance().getRecipes("减肥食谱", "1", 0, 10);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }).subscribe(new Action1<List<Recipe>>() {
            @Override
            public void call(List<Recipe> recipes) {
                Logger.v(TAG, recipes == null ? "recipe is null ###" : recipes.toString());
            }
        });

    }
}
