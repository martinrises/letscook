package com.lzf.letscook.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lzf.letscook.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

//        final Button btn = (Button) findViewById(R.id.btn_get);
//        Observable<View> btnGetObservable = Observable.create(new Observable.OnSubscribe<View>() {
//            @Override
//            public void call(final Subscriber<? super View> subscriber) {
//
//                if (btn == null) {
//                    return;
//                }
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (subscriber.isUnsubscribed()) {
//                            return;
//                        }
//                        subscriber.onNext(v);
//                    }
//                });
//            }
//        });

//        btnGetObservable.map(new Func1<View, List<Recipe>>() {
//            @Override
//            public List<Recipe> call(View view) {
//                try {
//                    Logger.v(TAG, "thread >>> " + Thread.currentThread().getName());
//                    return CookSystem.getInstance().getRecipes("减肥食谱", "1", 0, 10);
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//        }).subscribe(new Action1<List<Recipe>>() {
//            @Override
//            public void call(List<Recipe> recipes) {
//                Logger.v(TAG, recipes == null ? "recipe is null ###" : recipes.toString());
//            }
//        });


//        btnGetObservable.flatMap(new Func1<View, Observable<List<Recipe>>>() {
//            @Override
//            public Observable<List<Recipe>> call(View view) {
//
//
//                return CookSystem.getInstance().getRecipes("减肥食谱", "1", 0, 10);
//            }
//        }).subscribe(new Action1<List<Recipe>>() {
//            @Override
//            public void call(List<Recipe> recipes) {
//
//
//            }
//        });

    }
}
