package com.wafour.ads.mediation.wmediationsample;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.wafour.ads.mediation.AdManagerAdView;

import androidx.appcompat.app.AppCompatActivity;

public class BannerActivity extends AppCompatActivity {

    private static final String TAG = BannerActivity.class.getSimpleName();

    private AdManagerAdView mAdManagerAdView;
    private TextView mPlatform;

    private String mBannerSlotId = Config.BANNER_SLOT_ID;
    private boolean NO_AD_SYNC = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        String slotId = mBannerSlotId;
        mPlatform = findViewById(R.id.platform);

        mAdManagerAdView = new AdManagerAdView(this, slotId, R.id.avc);
        if(NO_AD_SYNC) {
            mAdManagerAdView.enableSchedulSync(false);
        }
        mAdManagerAdView.setAdListener(new AdManagerAdView.AdListener() {
            @Override
            public void onAdRequested(String platform) {
                Log.d(TAG, "[AdView] onAdRequested: " + platform);
            }

            @Override
            public void onAdLoaded(String platform) {
                Log.d(TAG, "[AdView] onAdLoaded : " + platform);
                toast("(배너) 성공 : " + platform);
                mPlatform.setText("Platform: " + platform);
            }

            @Override
            public void onAdClicked(String platform) {
                Log.d(TAG, "[AdView] onAdClicked: " + platform);
                toast("(배너) 클릭 : " + platform);
            }

            @Override
            public void onAdFailedToLoad(String platform) {
                Log.d(TAG, "[AdView] onAdFailedToLoad : " + platform);
                toast("(배너) 실패 : " + platform);
            }
        });
    }

    @Override
    protected void onPause() {
        if (mAdManagerAdView != null) {
            mAdManagerAdView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdManagerAdView != null) {
            mAdManagerAdView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdManagerAdView != null) {
            mAdManagerAdView.onDestroy();
        }
        super.onDestroy();
    }

    private void toast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 200);
        toast.show();
    }


}
