package org.jun.avoidddongo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class ScoreActivity extends AppCompatActivity {

    TextView scoreText;
    TextView rankoneText;
    TextView ranktwoText;
    TextView rankthreeText;
    TextView rankfourText ;
    TextView rankfiveText ;
    TextView ranksixText ;
    TextView ranksevenText ;
    TextView rankeightText ;
    TextView ranknineText ;
    TextView ranktenText ;
    String rankString[];
    rankHandler rank_Handler;
    EditText nameEdit;
    Button confirmBtn;
    String name;
    String score;
    String ipAdress;
    String t = "0";
    int maxscore = 9999999;
    TextView rankText[] ;
    TextView top ;
    TextView bottom ;
    TextView moneyTextView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_score);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        moneyTextView = (TextView)findViewById(R.id.moneyTextView) ;
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        top = (TextView)findViewById(R.id.top) ;
        bottom = (TextView)findViewById(R.id.bottom) ;

        scoreText = (TextView) findViewById(R.id.textView);

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

        nameEdit = (EditText) findViewById(R.id.editText);
        confirmBtn = (Button) findViewById(R.id.button);
        rankString = new String[11];
        for (int i = 0; i < 11; i++) {
            rankString[i] = " 0";
        }
        rank_Handler = new rankHandler();

        Intent intent = getIntent();
        scoreText.setText(intent.getStringExtra("score"));
        moneyTextView.setText("획득돈 : "+intent.getStringExtra("money"));

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("money",  pref.getInt("money",0) + Integer.parseInt(moneyTextView.getText().toString().split(" ")[2]));
        editor.commit();

        ipAdress = intent.getStringExtra("ipAdress");

        //rankThread rank_Thread = new rankThread();
       //rank_Thread.start();

        //linkCheck lc = new linkCheck();
        //lc.execute();

        new BackgroundTask().execute() ;//순위데이터베이스 불러오기

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = (nameEdit.getText().toString()).replace(" ", "");
                score = scoreText.getText().toString();

                rankString[10] = name + " " + score;

                sort();

                for(int i=0 ; i<10 ; i++)
                {
                    rankText[i].setText(i+1+"등 ");
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

                new DeleteBackgroundTask().execute() ;//데이터 베이스 삭제


                /*if (t.equals("0") == true)//서버가 꺼져있으면
                {
                    SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putString("rank1",rankString[0]) ;
                    editor.commit();
                    editor.putString("rank2",rankString[1]) ;
                    editor.commit();
                    editor.putString("rank3",rankString[2]) ;
                    editor.commit();
                }

                else//서버가 켜져있으면
                {
                    confirmThread confirm_Thread = new confirmThread();
                    confirm_Thread.start();
                }*/

                confirmBtn.setVisibility(View.GONE);
            }
        });
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

    class DeleteBackgroundTask extends AsyncTask
    {
        String target ;

        @Override
        protected void onPreExecute() {
            target = "http://icd0422.cafe24.com/delete.php" ;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try
            {
                URL url = new URL(target) ;
                HttpURLConnection httpURlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURlConnection.getInputStream() ;
                inputStream.close();
                httpURlConnection.disconnect();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try
                    {
                        JSONObject jsonResponse =  new JSONObject(response) ;
                        boolean success = jsonResponse.getBoolean("success") ;

                        if(success) {
                            //Toast.makeText(ScoreActivity.this,"성공",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            //Toast.makeText(ScoreActivity.this,"실패",Toast.LENGTH_SHORT).show();
                        }
                    }

                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            RequestQueue queue = Volley.newRequestQueue(ScoreActivity.this);
            for(int i=0 ; i<10 ; i++)
            {
                InsertRequest insertRequest = new InsertRequest(Integer.toString(i+1),rankString[i],responseListener) ;

                queue.add(insertRequest) ;
            }
        }
    }

    class rankThread extends Thread {
        @Override
        public void run() {
            super.run();

            try {
                for (int i = 0; i < 3; i++) {
                    int port = 5001;
                    Socket sock = new Socket(ipAdress, port);

                    ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                    outstream.writeObject(Integer.toString(i));
                    outstream.flush();

                    ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                    String r = (String) instream.readObject();
                    rankString[i] = r;
                    t = r;

                    sock.close();

                    if (i == 2) {
                        Message msg = rank_Handler.obtainMessage();
                        rank_Handler.sendMessage(msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class confirmThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                for (int i = 0; i < 3; i++) {
                    int port = 5001;
                    Socket sock = new Socket(ipAdress, port);

                    ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                    outstream.writeObject(rankString[i]);
                    outstream.flush();

                    sock.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class rankHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

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

    public void sort()

    {
        String name[] = new String[11];
        for (int i = 0; i < 11; i++) {
            name[i] = rankString[i].split(" ")[0];
        }

        Integer score[] = new Integer[11];
        for (int i = 0; i < 11; i++) {
            score[i] = Integer.parseInt(rankString[i].split(" ")[1]);
        }

        Person p[] = new Person[11];

        for (int i = 0; i < 11; i++) {
            p[i] = new Person(name[i], score[i]);
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <= 9; j++) {
                if (p[j].score < p[j + 1].score) {
                    Person temp = new Person();
                    temp = p[j];
                    p[j] = p[j + 1];
                    p[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < 11; i++) {
            rankString[i] = p[i].name + " " + Integer.toString(p[i].score);
        }
    }

    public class Person {
        String name;
        int score;

        public Person() {
        }

        public Person(String n, int s) {
            name = n;
            score = s;
        }
    }

    class linkCheck extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Thread.sleep(300);
                publishProgress();
            } catch (
                    Exception e)

            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

            if (t.equals("0") == true)//서버가 꺼져있으면
            {
                SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                if ((pref != null) && (pref.contains("rank1"))) {
                    String r = "";
                    rankString[0] = pref.getString("rank1", r);
                }
                rankoneText.setText(rankString[0]);

                if ((pref != null) && (pref.contains("rank2"))) {
                    String r = "";
                    rankString[1] = pref.getString("rank2", r);
                }
                ranktwoText.setText(rankString[1]);

                if ((pref != null) && (pref.contains("rank3"))) {
                    String r = "";
                    rankString[2] = pref.getString("rank3", r);
                }
                rankthreeText.setText(rankString[2]);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
