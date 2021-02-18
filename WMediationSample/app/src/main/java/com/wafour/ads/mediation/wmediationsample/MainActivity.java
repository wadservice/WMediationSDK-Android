package com.wafour.ads.mediation.wmediationsample;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.wafour.ads.mediation.AdManager;
import com.wafour.ads.mediation.AdManagerInterstitial;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private AdManagerInterstitial mAdManagerInterstitial;

    private String mInterstitialSlotId = Config.INTERSTITIAL_SLOT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WebView.setDataDirectorySuffix("wads");
        }

        initMediation();

        mAdManagerInterstitial = new AdManagerInterstitial(this, mInterstitialSlotId);
        mAdManagerInterstitial.setAdListener(new AdManagerInterstitial.AdListener() {
            @Override
            public void onAdLoaded(String platform) {
                Log.d(TAG, "[Interstitial] onAdLoaded : " + platform);
                mAdManagerInterstitial.show();

                toast("(전면) 성공 : " + platform);
            }

            @Override
            public void onAdFailedToLoad(String platform) {
                Log.d(TAG, "[Interstitial] onAdFailedToLoad : " + platform);

                toast("(전면) 실패 : " + platform);
            }

            @Override
            public void onAdShown(String platform) {
                Log.d(TAG, "[Interstitial] onAdShown : " + platform);
            }

            @Override
            public void onAdDismissed(String platform) {
                Log.d(TAG, "[Interstitial] onAdDismissed : " + platform);
            }
        });

        Button bannerButton = findViewById(R.id.btn_banner);
        bannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoBannerTest();
            }
        });

        Button intersButton = findViewById(R.id.btn_interstitial);
        intersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdManagerInterstitial.requestInterstitial();
            }
        });

    }

    @Override
    protected void onPause() {
        if (mAdManagerInterstitial != null) {
            mAdManagerInterstitial.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdManagerInterstitial != null) {
            mAdManagerInterstitial.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdManagerInterstitial != null) {
            mAdManagerInterstitial.onDestroy();
        }
        super.onDestroy();
    }
    private void initMediation() {
        AdManager.init(this);
    }

    private void toast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 200);
        toast.show();
    }

    private void gotoBannerTest() {
        Intent i = new Intent(this, BannerActivity.class);
        startActivity(i);
    }


}
