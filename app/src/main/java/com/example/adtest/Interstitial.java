package com.example.adtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Interstitial extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    Button button;
    private boolean aBoolean=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                if (aBoolean) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    interstitialAdds(adRequest);
                }
                else {
                    Bundle networkExtrasBundle = new Bundle();
                    networkExtrasBundle.putInt("rdp", 1);
                    AdRequest request = new AdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, networkExtrasBundle)
                            .build();
                    interstitialAdds(request);
                }

            }
        });

        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(Interstitial.this);

                } else {
                    Log.e("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        });





    }
    public void interstitialAdds(AdRequest adRequest) {
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.e("aa", "onAdLoaded");
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        Log.e("ee", "The ad was dismissed.");
                        // next add showing
                        if (aBoolean) {
                            AdRequest adRequest = new AdRequest.Builder().build();
                            interstitialAdds(adRequest);
                        }
                        else {
                            Bundle networkExtrasBundle = new Bundle();
                            networkExtrasBundle.putInt("rdp", 1);
                            AdRequest request = new AdRequest.Builder()
                                    .addNetworkExtrasBundle(AdMobAdapter.class, networkExtrasBundle)
                                    .build();
                            interstitialAdds(request);
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.e("TAG", "The ad was shown.");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("aa", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }
}