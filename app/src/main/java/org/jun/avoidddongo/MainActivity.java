package org.jun.avoidddongo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {

    Button startBtn;
    Button exitBtn;
    Button rankBtn;
    Button storeBtn ;
    Button addBtn ;
    RadioGroup rg;
    String ipAdress = "";
    TextView alarmTxt;
    boolean alarmbool = true;
    EditText ipEdit ;
    RadioButton r1,r2 ;
    TextView coinText ;
    TextView jmText, mujukText, boostText ;
    int serviceItem = 20 ;
    Button b1,b2,b3 ;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        jmText = (TextView) findViewById(R.id.jmText);
        mujukText = (TextView) findViewById(R.id.mujukText);
        boostText = (TextView) findViewById(R.id.boostText);

        startBtn = (Button) findViewById(R.id.button1);
        exitBtn = (Button) findViewById(R.id.button4);
        rankBtn = (Button) findViewById(R.id.button2);
        storeBtn = (Button) findViewById(R.id.button3);
        ipEdit = (EditText) findViewById(R.id.editText2) ;
        addBtn = (Button) findViewById(R.id.addBtn) ;

        r1 = (RadioButton) findViewById(R.id.radioButton) ;
        r2 = (RadioButton) findViewById(R.id.radioButton2) ;

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipEdit.setText("");
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipEdit.setText("");
            }
        });

        ipEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);

                rb.setChecked(false);

            }
        });

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        addBtn.setOnClickListener( new View.OnClickListener(){

            public void onClick(View view) {

                addBtn.setText("광고 로딩중...");


                mRewardedVideoAd.loadAd("ca-app-pub-5316264084399155/5751113269",
                new AdRequest.Builder().build());

            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*서버기능 넣고 싶으면 주석 해제
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);

                if((ipEdit.getText().toString().equals("")) == true)
                {
                    if ((rb.getText().toString().equals("서버2") == true)) {//전주
                        ipAdress = "211.33.5.55";
                    } else if ((rb.getText().toString().equals("서버1") == true)) {//청주
                        ipAdress = "210.124.172.246";
                    }
                }

                else
                {
                    ipAdress = ipEdit.getText().toString() ;
                }
                */

                Intent intent = new Intent(getApplicationContext(), OnGameActivity.class);
                intent.putExtra("ipAdress", ipAdress);
                startActivity(intent);

            }
        });

        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                startActivity(intent);
            }
        });

        rankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*서버기능 넣고 싶으면 주석 해제
                RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

                int id = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);

                if((ipEdit.getText().toString().equals("")) == true) {
                    if ((rb.getText().toString().equals("서버2") == true)) {
                        ipAdress = "211.33.5.55";
                    } else if ((rb.getText().toString().equals("서버1") == true)) {
                        ipAdress = "210.124.172.246";
                    }
                }

                else
                {
                    ipAdress = ipEdit.getText().toString() ;
                }
                */

                Intent intent = new Intent(getApplicationContext(), ScoreMainActivity.class);
                intent.putExtra("ipAdress", ipAdress);
                startActivity(intent);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

        int id = rg.getCheckedRadioButtonId();

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if((ipEdit.getText().toString().equals("")) == true)
        {
            editor.putInt("radioID", id);
            editor.commit();
        }

        else
        {
            editor.clear();
            editor.commit() ;
            editor.putString("ipEdit",ipEdit.getText().toString()) ;
            editor.commit() ;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        coinText = (TextView)findViewById(R.id.coinText);
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        coinText.setText(Integer.toString(pref.getInt("money",0)));

        jmText.setText(Integer.toString(pref.getInt("jm",serviceItem)));
        mujukText.setText(Integer.toString(pref.getInt("mujuk",serviceItem)));
        boostText.setText(Integer.toString(pref.getInt("boost",serviceItem)));

        /*서버기능 넣고 싶으면 주석 해제
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if ((pref != null) && (pref.contains("radioID"))) {
            int id = pref.getInt("radioID", 0);
            RadioButton rb = (RadioButton) findViewById(id);
            rb.setChecked(true);
        }

        else if((pref != null) && (pref.contains("ipEdit")))
        {
            String t="" ;
            ipEdit.setText(pref.getString("ipEdit",t));
        }
        */
    }

    public void onRewardedVideoAdLoaded() {mRewardedVideoAd.show();}
    public void onRewardedVideoAdOpened() {}
    public void onRewardedVideoStarted() {}
    public void onRewardedVideoAdClosed() {addBtn.setText("광고보고 아이템 각각 10개씩 받기");}
    public void onRewarded(RewardItem rewardItem) {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("boost",pref.getInt("boost",serviceItem)+10);
        editor.commit();
        editor.putInt("mujuk",pref.getInt("mujuk",serviceItem)+10);
        editor.commit();
        editor.putInt("jm",pref.getInt("jm",serviceItem)+10);
        editor.commit();

        addBtn.setText("광고보고 아이템 각각 10개씩 받기");
    }
    public void onRewardedVideoAdLeftApplication() {}
    public void onRewardedVideoAdFailedToLoad(int i) {}
}
