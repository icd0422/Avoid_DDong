package org.jun.avoidddongo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OnGameActivity extends AppCompatActivity {

    TextView scoreText, maxscoreText;
    int score;
    Handler handler;
    scoreTextRunable scoreRunable;
    boolean scorebool, ddongFallbool;
    ddongFallHandler ddongFall_Handler[];
    MyddongFallThread ddongFallThread[];
    ImageView ddongimg[];
    ImageView leftPerson, rightPerson;
    ImageView leftBtn, rightBtn;
    boolean leftbool, rightbool;
    leftHandler left_Handler;
    rightHandler right_Handler;
    int initialStop;
    boolean judgecollisionbool;
    judgeCollisionHandler judgeCollision_Handler;
    int t;
    int s;
    int mddong[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};//int mddong[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int fddong[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};//int fddong[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    boolean mujukbool = false;
    boolean firebool = false;
    int PersonSpeed;
    TextView boostText2;
    double boostPasstime;
    boolean eatfire = false;
    TextView boost2Text;
    double personHeight;
    double personY;
    boolean coinBool[] = {false, false, false, false, false, false, false, false, false, false, false};
    TextView moneyText;
    boolean start = true;
    TextView moneyTextView;
    ImageView jmBtn, mujukBtn, boostBtn;
    TextView jmText, mujukText, boostText;
    int serviceItem = 20;
    int low = 15, high = 16;//20 40
    //boolean stageBool = false;
    TextView stageText;
    boolean bonusBool = false;
    int stage = 1;
    int ss = 0;
    int bb = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_on_game);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //StageBackground sb = new StageBackground();
        //sb.execute();

        jmBtn = (ImageView) findViewById(R.id.jmBtn);
        mujukBtn = (ImageView) findViewById(R.id.mujukBtn);
        boostBtn = (ImageView) findViewById(R.id.boostBtn);

        jmText = (TextView) findViewById(R.id.jmText);
        mujukText = (TextView) findViewById(R.id.mujukText);
        boostText = (TextView) findViewById(R.id.boostText);
        stageText = (TextView) findViewById(R.id.stageText);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        jmText.setText("x " + Integer.toString(pref.getInt("jm", serviceItem)));
        mujukText.setText("x " + Integer.toString(pref.getInt("mujuk", serviceItem)));
        boostText.setText("x " + Integer.toString(pref.getInt("boost", serviceItem)));

        moneyTextView = (TextView) findViewById(R.id.moneyTextView);

        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar3);
        pb.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        moneyText = (TextView) findViewById(R.id.moneyTextView);
        maxscoreText = (TextView) findViewById(R.id.textView15);

        Intent intent = getIntent();
        /*if(intent.getStringExtra("ipAdress").equals("")==true)//서버가 연결 안됬으면
        {
            SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
            if ((pref != null) && pref.contains("rank1")) {
                String t = "" ;
                String rankString = pref.getString("rank1",t) ;
                maxscoreText.setText("1등("+rankString.split(" ")[0]+") : "+rankString.split(" ")[1]);

            }
        }*/

        boostPasstime = 0;
        PersonSpeed = 50;
        t = 1;
        s = 1;
        scorebool = false;
        ddongFallbool = false;
        leftbool = false;
        rightbool = false;
        initialStop = 1;
        score = 0;
        scoreText = (TextView) findViewById(R.id.textView);
        boostText2 = (TextView) findViewById(R.id.textView4);
        boost2Text = (TextView) findViewById(R.id.textView8);
        handler = new Handler();
        left_Handler = new leftHandler();
        right_Handler = new rightHandler();
        scoreRunable = new scoreTextRunable();
        judgecollisionbool = true;

        ddongimg = new ImageView[11];//ddongimg = new ImageView[12];
        ddongimg[1] = (ImageView) findViewById(R.id.imageView1);
        ddongimg[2] = (ImageView) findViewById(R.id.imageView2);
        ddongimg[3] = (ImageView) findViewById(R.id.imageView3);
        ddongimg[4] = (ImageView) findViewById(R.id.imageView6);
        ddongimg[5] = (ImageView) findViewById(R.id.imageView4);
        ddongimg[6] = (ImageView) findViewById(R.id.imageView5);
        ddongimg[7] = (ImageView) findViewById(R.id.imageView7);
        ddongimg[8] = (ImageView) findViewById(R.id.imageView8);
        ddongimg[9] = (ImageView) findViewById(R.id.imageView9);
        ddongimg[10] = (ImageView) findViewById(R.id.imageView10);
        //ddongimg[11] = (ImageView) findViewById(R.id.imageView11);


        ddongFall_Handler = new ddongFallHandler[11];//ddongFall_Handler = new ddongFallHandler[12];
        for (int i = 1; i <= 10; i++) {//for (int i = 1; i <= 11; i++) {
            ddongFall_Handler[i] = new ddongFallHandler(i);
        }

        leftPerson = (ImageView) findViewById(R.id.imageView12);
        rightPerson = (ImageView) findViewById(R.id.imageView13);
        leftBtn = (ImageView) findViewById(R.id.imageView11);
        rightBtn = (ImageView) findViewById(R.id.imageView14);
        personHeight = leftPerson.getHeight();
        personY = leftPerson.getY();

        scorebool = true;
        final Thread scoreThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (scorebool) {
                        Thread.sleep(100);
                        if (ss == 1) continue;
                        score += 1;

                        handler.post(scoreRunable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start) {
                    scoreThread.start();
                    start = false;
                }
                if (rightPerson.getVisibility() == View.VISIBLE || t == 1)//오른쪽으로 이동중이면
                {
                    float m = rightPerson.getX();
                    rightPerson.setVisibility(View.GONE);
                    leftPerson.setX(m);
                    leftPerson.setVisibility(View.VISIBLE);

                    rightbool = false;
                    leftbool = true;
                    leftPersonThread lftThread = new leftPersonThread();
                    lftThread.start();
                    t = 0;
                } else if (t == 1)//왼쪽으로 이동중이면
                {
                    leftbool = true;
                    leftPersonThread lftThread = new leftPersonThread();
                    lftThread.start();
                    t = 0;
                } else {

                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start) {
                    scoreThread.start();
                    start = false;
                }
                if (leftPerson.getVisibility() == View.VISIBLE)//왼쪽으로 이동중이면
                {
                    float m = leftPerson.getX();
                    leftPerson.setVisibility(View.GONE);
                    rightPerson.setX(m);
                    rightPerson.setVisibility(View.VISIBLE);

                    leftbool = false;
                    rightbool = true;
                    rightPersonThread rgtThread = new rightPersonThread();
                    rgtThread.start();
                } else//오른쪽으로 이동중이면
                {

                }
            }
        });

        jmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftPerson.getVisibility() == View.VISIBLE && !start)//왼쪽으로 이동중이면
                {
                    int t;
                    t = Integer.parseInt(jmText.getText().toString().split(" ")[1]) - 1;
                    if (t >= 0) {
                        leftPerson.setX(leftPerson.getX() - 40);
                        jmText.setText("x " + t + "");

                        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putInt("jm", t);
                        editor.commit();
                    } else {

                    }
                } else if (rightPerson.getVisibility() == View.VISIBLE && !start)//오른쪽으로 이동중이면
                {
                    int t;
                    t = Integer.parseInt(jmText.getText().toString().split(" ")[1]) - 1;
                    if (t >= 0) {
                        rightPerson.setX(rightPerson.getX() + 40);
                        jmText.setText("x " + t + "");

                        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putInt("jm", t);
                        editor.commit();
                    } else {

                    }
                }
            }
        });

        mujukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftPerson.getVisibility() == View.VISIBLE && !start)//왼쪽으로 이동중이면
                {
                    int t;
                    t = Integer.parseInt(mujukText.getText().toString().split(" ")[1]) - 1;
                    if (t >= 0) {
                        mujukBtn.setVisibility(View.GONE);
                        mujukBackground mujuk = new mujukBackground();
                        mujuk.execute();
                        mujukText.setText("x " + t + "");

                        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putInt("mujuk", t);
                        editor.commit();
                    } else {

                    }
                } else if (rightPerson.getVisibility() == View.VISIBLE && !start)//오른쪽으로 이동중이면
                {
                    int t;
                    t = Integer.parseInt(mujukText.getText().toString().split(" ")[1]) - 1;
                    if (t >= 0) {
                        mujukBtn.setVisibility(View.GONE);
                        mujukBackground mujuk = new mujukBackground();
                        mujuk.execute();
                        mujukText.setText("x " + t + "");


                        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putInt("mujuk", t);
                        editor.commit();

                    } else {

                    }
                }
            }
        });

        boostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leftPerson.getVisibility() == View.VISIBLE && !start)//왼쪽으로 이동중이면
                {
                    int t;
                    t = Integer.parseInt(boostText.getText().toString().split(" ")[1]) - 1;
                    if (t >= 0) {
                        boostBtn.setVisibility(View.GONE);
                        fireBackground fire = new fireBackground();
                        fire.execute();
                        boostText.setText("x " + t + "");

                        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putInt("boost", t);
                        editor.commit();
                    } else {

                    }
                } else if (rightPerson.getVisibility() == View.VISIBLE && !start)//오른쪽으로 이동중이면
                {
                    int t;
                    t = Integer.parseInt(boostText.getText().toString().split(" ")[1]) - 1;
                    if (t >= 0) {
                        boostBtn.setVisibility(View.GONE);
                        fireBackground fire = new fireBackground();
                        fire.execute();
                        boostText.setText("x " + t + "");

                        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putInt("boost", t);
                        editor.commit();
                    } else {

                    }
                }
            }
        });


        judgeCollision_Handler = new judgeCollisionHandler();

    }

    @Override
    protected void onResume() {
        super.onResume();

        scoreText.setText(Integer.toString(Integer.parseInt(scoreText.getText().toString())));
        ss = 0;
        ddongFallbool = true;
        judgecollisionbool = true;
        //StageBackground sb = new StageBackground();
        //sb.execute() ;

        ddongFallThread = new MyddongFallThread[11];//ddongFallThread = new MyddongFallThread[12];
        for (int i = 1; i <= 10; i++) {//for (int i = 1; i <= 11; i++) {
            ddongFallThread[i] = new MyddongFallThread(i);
        }
        for (int i = 1; i <= 10; i++) {//for (int i = 1; i <= 11; i++) {
            ddongFallThread[i].start();
        }

        if (leftPerson.getVisibility() == View.VISIBLE && initialStop != 1 && !start) // 왼쪽으로 이동중이면
        {
            leftbool = true;
            leftPersonThread lftThread = new leftPersonThread();
            lftThread.start();
        } else if (rightPerson.getVisibility() == View.VISIBLE && initialStop != 1)// 오른쪽으로 이동중이면
        {
            rightbool = true;
            rightPersonThread rgtThread = new rightPersonThread();
            rgtThread.start();
        }

        initialStop = 0;

        judgeCollisionThread judgeCollision_Thread = new judgeCollisionThread();
        judgeCollision_Thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //scorebool = false;
        ss = 1;
        ddongFallbool = false;
        leftbool = false;
        rightbool = false;
        judgecollisionbool = false;
        mujukbool = false;
        firebool = false;
        //stageBool = false ;

    }


    public class scoreTextRunable implements Runnable {

        @Override
        public void run() {
            scoreText.setText(Integer.toString(score));
            if(score%400==0&&score!=0) {
                bb = 1;

                for (int i = 1; i <= 10; i++) {
                    setddongTop(i);
                }

                stageText.setText("BONUS!!");

                stage++;
                high -= 2;
                if(high<=0) high=1 ;
                //stageText.setText("Stage " + stage);
            }
            else if(score%50==0){
                    bb = 0;
                    stageText.setText("Stage " + stage);
            }
        }
    }

    public class ddongFallHandler extends Handler {
        int num;

        public ddongFallHandler() {

        }

        public ddongFallHandler(int n) {
            num = n;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ((ddongimg[num].getY() + ddongimg[num].getHeight()) >= leftPerson.getY() + leftPerson.getHeight())//똥이 다 떨어지면
            {
                setddongTop(num);
            } else ddongimg[num].setY(ddongimg[num].getY() + 10); //그렇지 않다면 10씩 아래로 이동
        }
    }

    public class leftHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if ((leftPerson.getX() - 10) < ddongimg[1].getX())//왼쪽구석을 넘어갔을시
            {
                leftPerson.setX(ddongimg[10].getX());//leftPerson.setX(ddongimg[11].getX() + ddongimg[11].getWidth());
            } else leftPerson.setX(leftPerson.getX() - 10);
        }
    }

    public class rightHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            if (((rightPerson.getX() + rightPerson.getWidth())) + 10 > (ddongimg[10].getX() + ddongimg[10].getWidth()))//오른쪽구석을 넘어갔을시//if (((rightPerson.getX() + rightPerson.getWidth())) > (ddongimg[11].getX() + ddongimg[11].getWidth()))//오른쪽구석을 넘어갔을시
            {
                rightPerson.setX(ddongimg[1].getX());
            } else rightPerson.setX(rightPerson.getX() + 10);
        }
    }

    public class judgeCollisionHandler extends Handler { //충돌 핸들러

        @Override
        public void handleMessage(Message msg) {
            int gap = 50;
            for (int i = 1; i <= 10; i++) {//for (int i = 1; i <= 11; i++) {
                if (leftPerson.getVisibility() == View.VISIBLE)//왼쪽사람이 보일때
                {
                    if (((ddongimg[i].getY() + ddongimg[i].getHeight()) > leftPerson.getY()) && (((ddongimg[i].getX() + ddongimg[i].getWidth()) > (leftPerson.getX() + gap) && (ddongimg[i].getX() < ((leftPerson.getX() + leftPerson.getWidth()) - gap))))) {

                        if (coinBool[i])//코인이라면
                        {
                            setddongTop(i);
                            moneyText.setText(Integer.toString(Integer.parseInt(moneyText.getText().toString()) + 10));//돈10증가
                            continue;
                        }

                        if (mujukbool) {
                            setddongTop(i);
                            continue;
                        }

                        /*if (mujukbool == true) {
                            if (fddong[i] == 1)//무적상태인데 불똥일때
                            {
                                setddongTop(i);
                                mujukbool = false;
                                fireBackground fire = new fireBackground();
                                fire.execute();
                                continue;
                            }
                            setddongTop(i);
                            continue;
                        }//무적상태일때는 안죽음

                        if (firebool == true) {
                            if (mddong[i] == 1) {
                                setddongTop(i);
                                firebool = false;

                                /*try {
                                    Thread.sleep(100);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                mujukBackground mujuk = new mujukBackground();
                                mujuk.execute();
                                continue;
                            } //빠른상태이고 황금똥일때
                            else if (fddong[i] == 1)//빠른상태이고 불똥일때
                            {
                                setddongTop(i);
                                eatfire = true;
                                continue;
                            }
                        }

                        //if (firebool == true && ((mddong[i] == 1)||(fddong[i] == 1))) continue ;

                        if (mddong[i] == 1)//황금똥일때
                        {
                            setddongTop(i);
                            mujukBackground mujuk = new mujukBackground();
                            mujuk.execute();
                            continue;
                        } else if (fddong[i] == 1)//불똥일때
                        {
                            setddongTop(i);
                            fireBackground fire = new fireBackground();
                            fire.execute();
                            continue;
                        }*/
                        Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                        exit();
                        if (s == 1) {
                            s = 0;
                            intent.putExtra("score", scoreText.getText().toString());
                            intent.putExtra("money", moneyTextView.getText().toString());
                            Intent intent2 = getIntent();
                            intent.putExtra("ipAdress", intent2.getStringExtra("ipAdress"));
                            startActivity(intent);
                        }

                    }
                } else if (rightPerson.getVisibility() == View.VISIBLE)//오른쪽 사람이 보일때
                {
                    if (((ddongimg[i].getY() + ddongimg[i].getHeight()) > rightPerson.getY()) && (((ddongimg[i].getX() + ddongimg[i].getWidth()) > (rightPerson.getX() + gap) && (ddongimg[i].getX() < ((rightPerson.getX() + rightPerson.getWidth()) - gap))))) {
                        if (coinBool[i])//코인이라면
                        {
                            setddongTop(i);
                            moneyText.setText(Integer.toString(Integer.parseInt(moneyText.getText().toString()) + 10));//돈10증가
                            continue;
                        }


                        if (mujukbool) {
                            setddongTop(i);
                            continue;
                        }

                        /*if (mujukbool == true) {
                            if (fddong[i] == 1)//무적상태인데 불똥일때
                            {
                                setddongTop(i);
                                mujukbool = false;
                                fireBackground fire = new fireBackground();
                                fire.execute();
                                continue;
                            }
                            setddongTop(i);
                            continue;
                        }//무적상태일때는 안죽음

                        if (firebool == true) {
                            if (mddong[i] == 1) {
                                setddongTop(i);
                                firebool = false;

                                 try {
                                    Thread.sleep(100);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                mujukBackground mujuk = new mujukBackground();
                                mujuk.execute();
                                continue;
                            } //빠른상태이고 황금똥일때
                            else if (fddong[i] == 1)//빠른상태이고 불똥일때
                            {
                                setddongTop(i);
                                eatfire = true;
                                continue;
                            }
                        }

                        if (firebool == true && ((mddong[i] == 1)|| (fddong[i] == 1))) continue ;

                        if (mddong[i] == 1)//황금똥일때
                        {
                            setddongTop(i);
                            mujukBackground mujuk = new mujukBackground();
                            mujuk.execute();
                            continue;
                        } else if (fddong[i] == 1)//불똥일때
                        {
                            setddongTop(i);
                            fireBackground fire = new fireBackground();
                            fire.execute();
                            continue;
                        }*/
                        Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                        exit();
                        if (s == 1) {
                            s = 0;
                            intent.putExtra("score", scoreText.getText().toString());
                            intent.putExtra("money", moneyTextView.getText().toString());
                            Intent intent2 = getIntent();
                            intent.putExtra("ipAdress", intent2.getStringExtra("ipAdress"));
                            startActivity(intent);
                        }
                    }
                }
            }
        }
    }

    class fireBackground extends AsyncTask {

        int time = score+30 ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mujukbool = false;
            leftPerson.setImageResource(R.drawable.flperson);
            rightPerson.setImageResource(R.drawable.frperson);
            firebool = true;
            PersonSpeed = 25;
            //boostText.setText(Integer.toString(3));//총시간 3초
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                while (firebool) {
                    Thread.sleep(5);
                    /*if (eatfire == true) {
                        eatfire = false;
                        boostPasstime -= 3000;
                        ;
                        publishProgress();
                    }
                    boostPasstime += 10;
                    //if((boostPasstime%1000)==0)
                    publishProgress();
                    if (boostPasstime == 3000) break;//총시간 2초*/
                    if (time == score) break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            double time = (2.0 - (boostPasstime / 1000.0)); //총시간 3초
            boostText2.setText(Integer.toString((int) time));
            boost2Text.setText(Integer.toString((int) ((time - ((double) (int) time)) * 100)));
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            firebool = false;
            if (mujukbool == true) {
            } else {
                leftPerson.setImageResource(R.drawable.lperson);
                rightPerson.setImageResource(R.drawable.rperson);
            }
            PersonSpeed = 50;
            boostPasstime = 0;
            boostText2.setText("0");
            boost2Text.setText("0");
            boostBtn.setVisibility(View.VISIBLE);
        }
    }


    public class MyddongFallThread extends Thread {
        int num;
        int term;

        public MyddongFallThread() {

        }

        public MyddongFallThread(int n) {
            double randomValue = Math.random();

            term = (int) (randomValue * (low)) + high;//최소:10, 최대:30


            num = n;
        }

        public void changeTerm(int t) {
            term = t;
        }

        @Override
        public void run() {
            super.run();

            try {
                while (ddongFallbool) {
                    Thread.sleep(term);

                    Message msg = ddongFall_Handler[num].obtainMessage();
                    ddongFall_Handler[num].sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class leftPersonThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                while (leftbool) {
                    Thread.sleep(PersonSpeed);

                    Message msg = left_Handler.obtainMessage();
                    left_Handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class rightPersonThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                while (rightbool) {
                    Thread.sleep(PersonSpeed);

                    Message msg = right_Handler.obtainMessage();
                    right_Handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class judgeCollisionThread extends Thread {

        @Override
        public void run() {
            super.run();

            try {
                while (judgecollisionbool) {
                    sleep(10);
                    Message msg = judgeCollision_Handler.obtainMessage();
                    judgeCollision_Handler.sendMessage(msg);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class mujukBackground extends AsyncTask {
        int time = score + 30;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            firebool = false;
            leftPerson.setImageResource(R.drawable.mlperson);
            rightPerson.setImageResource(R.drawable.mrperson);
            mujukbool = true;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                while (mujukbool) {
                    Thread.sleep(5);
                    if (time == score) break;//3초
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mujukbool = false;
            mujukBtn.setVisibility(View.VISIBLE);

            if (firebool == true) {
            } else {
                leftPerson.setImageResource(R.drawable.lperson);
                rightPerson.setImageResource(R.drawable.rperson);
            }
        }
    }

    /*class StageBackground extends AsyncTask {

        int t;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            stageBool = true;
            t = 0;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                while (stageBool) {
                        Thread.sleep(10);
                        if (score % 1000 == 0 && score != 0) {
                           publishProgress();
                        }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onProgressUpdate(Object[] values) {
            BonusBackground bonusBackground = new BonusBackground();
            bonusBackground.execute() ;
        }

        @Override
        protected void onPostExecute(Object o) {

        }
    }

    class BonusBackground extends AsyncTask {

        double k = 0 ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bonusBool = true ;
            bb = 1;
            for (int i = 1; i <= 10; i++) {
                setddongTop(i);
            }
            stageText.setText("BONUS!!");
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                while (bonusBool) {
                    Thread.sleep(10);
                    k+=10 ;
                    if(k%1000>=850) break ;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onProgressUpdate(Object[] values) {
        }

        @Override
        protected void onPostExecute(Object o) {
            bb = 0 ;
            stageText.setText("Stage " + ++stage);
        }
    }*/



    private void exit() {
        scorebool = false;
        ddongFallbool = false;
        leftbool = false;
        rightbool = false;
        judgecollisionbool = false;
        mujukbool = false;
        firebool = false;
        //stageBool = false ;
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //stageBool = false ;
    }

    private void setddongTop(int num) {
        ddongimg[num].setY(0);//똥이 다 떨어지면 처음위치로 이동

        double randomValue = Math.random();
        int t = (int) (randomValue * (low)) + high ;
        ddongFallThread[num].changeTerm(t);//똥이 다 떨어지면 속도 변경


        if(bb==1) {ddongimg[num].setImageResource(R.drawable.coin); coinBool[num] = true;return;}

        if (coinBool[num] || mddong[num] == 1 || fddong[num] == 1)//황금똥이거나 코인이거나 불똥이면
        {
            ddongimg[num].setImageResource(R.drawable.ddong);
            mddong[num] = 0;
            fddong[num] = 0;
            coinBool[num] = false ;
        } else if (mddong[num] == 0)//일반 똥이면
        {
            ddongimg[num].setImageResource(R.drawable.ddong);
            int r = (int) (randomValue * 10);
            if (r == 0) {
                ddongimg[num].setImageResource(R.drawable.coin);//동전으로 바꾼다
                coinBool[num] = true ;
                //ddongimg[num].setImageResource(R.drawable.mddong);//황금똥으로 바꾼다
                //mddong[num] = 1;//황금똥이라고 지정
            } /*else if (r == 1 || r == 2) {
                ddongimg[num].setImageResource(R.drawable.fddong);//불똥으로 바꾼다
                fddong[num] = 1;//불똥이라고 지정
            }*/
        }
    }
}
