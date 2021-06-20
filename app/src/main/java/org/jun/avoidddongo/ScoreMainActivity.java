package org.jun.avoidddongo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

        if (AppHelper.requestQueue == null)
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        showRanking();
        //new BackgroundTask().execute() ;//순위데이터베이스 불러오기
    }

    public void showRanking() {
        String url = "http://13.124.97.131:8080/v1/avoidddong/ranks?page=0&size=10&sort=score,desc";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        OffsetList rankingOffsetList = gson.fromJson(response, OffsetList.class);

                        int i = 0;
                        for (RankingDTO rankingDTO : rankingOffsetList.getResults()) {
                            rankText[i].setText(i + 1 + "등 : " + rankingDTO.getName() + " / 점수 : " + rankingDTO.getScore() + " / 기록 일자 : " + rankingDTO.getCreatedAt());
                            i++;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {////Post방식으로 파라미터를 전달해주고싶을 때
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
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

