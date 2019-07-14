package org.jun.avoidddongo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ScoreMainActivity extends AppCompatActivity {

    String rankString[];
    TextView rankText[] ;
    TextView top ;
    TextView bottom ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_score_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        top = (TextView)findViewById(R.id.top) ;
        bottom = (TextView)findViewById(R.id.bottom) ;

        rankText = new TextView[10] ;
        rankText[0] = (TextView) findViewById(R.id.textView1);
        rankText[1] = (TextView) findViewById(R.id.textView2);
        rankText[2] = (TextView) findViewById(R.id.textView3);
        rankText[3] = (TextView) findViewById(R.id.textView4);
        rankText[4] = (TextView) findViewById(R.id.textView5);
        rankText[5] = (TextView) findViewById(R.id.textView6);
        rankText[6] = (TextView) findViewById(R.id.textView7);
        rankText[7] = (TextView) findViewById(R.id.textView8);
        rankText[8] = (TextView) findViewById(R.id.textView9);
        rankText[9] = (TextView) findViewById(R.id.textView10);

        rankString = new String[11];
        for (int i = 0; i < 11; i++) {
            rankString[i] = " 0";
        }

        new BackgroundTask().execute() ;//순위데이터베이스 불러오기
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target ;

        @Override
        protected void onPreExecute() {
            target = "http://icd0422.cafe24.com/mainRank.php" ;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                URL url = new URL(target) ;
                HttpURLConnection httpURlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURlConnection.getInputStream() ;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp ;
                StringBuilder stringBuilder = new StringBuilder() ;
                while((temp = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(temp + "\n") ;
                }
                bufferedReader.close();
                inputStream.close();
                httpURlConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int c = 0 ;
                while(c<jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(c) ;
                    rankString[Integer.parseInt(object.getString("rank"))-1] = object.getString("rankString") ;
                    c++;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            rankText[0].setText((rankText[0].getText().toString())+" "+rankString[0]);
            rankText[1].setText((rankText[1].getText().toString())+" "+rankString[1]);
            rankText[2].setText((rankText[2].getText().toString())+" "+rankString[2]);
            rankText[3].setText((rankText[3].getText().toString())+" "+rankString[3]);
            rankText[4].setText((rankText[4].getText().toString())+" "+rankString[4]);
            rankText[5].setText((rankText[5].getText().toString())+" "+rankString[5]);
            rankText[6].setText((rankText[6].getText().toString())+" "+rankString[6]);
            rankText[7].setText((rankText[7].getText().toString())+" "+rankString[7]);
            rankText[8].setText((rankText[8].getText().toString())+" "+rankString[8]);
            rankText[9].setText((rankText[9].getText().toString())+" "+rankString[9]);

        }
    }

}

