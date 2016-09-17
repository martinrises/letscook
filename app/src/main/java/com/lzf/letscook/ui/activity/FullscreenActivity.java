package com.lzf.letscook.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lzf.letscook.R;
import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;

public class FullscreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //准备展示开屏广告的容器
        FrameLayout container = (FrameLayout) this
                .findViewById(R.id.ad_container_fl);
        //创建开屏广告，广告拉取成功后会自动展示在container中。Container会首先被清空
        new SplashAD(this, container, Constants.APPId, Constants.SplashPosId,
                new SplashADListener() {
                    @Override
                    public void onADDismissed() {
                        goToMainActivity();
                    }

                    @Override
                    public void onNoAD(int i) {

                    }
                    ic_launcher.png
                    @Override
                    public void onADPresent() {
                        Toast.makeText(FullscreenActivity.this,
                                "SplashPresent", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onADClicked() {

                    }

                });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //阻止用户在展示过程中点击手机返回键，推荐开发者使用
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void goToMainActivity() {
        FullscreenActivity.this.startActivity(new Intent(FullscreenActivity.this, MainActivity.class));
        FullscreenActivity.this.finish();
    }
}
