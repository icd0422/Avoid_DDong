package org.jun.avoidddongo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class StoreActivity extends AppCompatActivity {

    TextView coinText ;
    TextView jmText, mujukText, boostText ;
    int serviceItem ;
    Button jmBtn, mujukBtn, boostBtn, exitBtn;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_store);

            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();

            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        coinText = (TextView) findViewById(R.id.coinText) ;
        jmText = (TextView) findViewById(R.id.jmText);
        mujukText = (TextView) findViewById(R.id.mujukText);
        boostText = (TextView) findViewById(R.id.boostText);

        final SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        coinText.setText("x "+Integer.toString(pref.getInt("money",0)));

        serviceItem = 20 ;

        jmText.setText("x "+Integer.toString(pref.getInt("jm",serviceItem)));
        mujukText.setText("x "+Integer.toString(pref.getInt("mujuk",serviceItem)));
        boostText.setText("x "+Integer.toString(pref.getInt("boost",serviceItem)));

        jmBtn = (Button) findViewById(R.id.jmBtn) ;
        mujukBtn = (Button) findViewById(R.id.mujukBtn) ;
        boostBtn = (Button) findViewById(R.id.boostBtn) ;
        exitBtn = (Button) findViewById(R.id.exitBtn) ;

        jmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t1 = Integer.parseInt(coinText.getText().toString().split(" ")[1]) - 100 ;
                if(t1>=0)
                {
                    coinText.setText("x "+t1);
                    int t2 = Integer.parseInt(jmText.getText().toString().split(" ")[1])+1 ;
                    jmText.setText("x "+t2);

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("money",t1);
                    editor.commit();
                    editor.putInt("jm",t2);
                    editor.commit();
                }

                else
                {

                }
            }
        });

        mujukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t1 = Integer.parseInt(coinText.getText().toString().split(" ")[1])-200 ;
                if(t1>=0)
                {
                    coinText.setText("x "+t1);
                    int t2 = Integer.parseInt(mujukText.getText().toString().split(" ")[1])+1 ;
                    mujukText.setText("x "+t2);

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("money",t1);
                    editor.commit();
                    editor.putInt("mujuk",t2);
                    editor.commit();
                }

                else
                {

                }
            }
        });

        boostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t1 = Integer.parseInt(coinText.getText().toString().split(" ")[1])-50 ;
                if(t1>=0)
                {
                    coinText.setText("x "+t1);
                    int t2 = Integer.parseInt(boostText.getText().toString().split(" ")[1])+1 ;
                    boostText.setText("x "+t2);

                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("money",t1);
                    editor.commit();
                    editor.putInt("boost",t2);
                    editor.commit();
                }

                else
                {

                }
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
