package com.example.kfbraille;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity implements OnTouchListener, OnInitListener {
    MediaPlayer mp;
    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    TextToSpeech tts;
    long previoustime = 0;
    float downx = 0, downy = 0, upx = 0, upy = 0;
    float screenheight;
    float screenwidth;
    float initialx;
    float initialy;
    float activex;
    float activey;
    String ostr = "";
    String previousletter = "";
    int activate = 0;
    int enter;
    long start_time;
    long finish_time;
    long enter_time;
    long phrase_finish_time;
    long start_letter_time;
    long finish_letter_time;
    long exceptionastart;
    long exceptionaend;
    long exceptioncstart;
    long exceptioncend;
    int exceptiona;
    int exceptionc;
    int point1 = 0;
    int point2 = 0;
    int point3 = 0;
    int point4 = 0;
    int point5 = 0;
    int point6 = 0;
    int point7 = 0;
    int point8 = 0;
    int point9 = 0;
    int point10 = 0;
    int point11 = 0;
    int point12 = 0;
    int point13 = 0;
    int point14 = 0;
    int point15 = 0;
    int doubletouch = 0;
    FileWriter writer;
    FileWriter writer_1;
    SpellCheck spellCheck;
    String[] wordFreqTmp;
    TextView tv_touchPoint;
    InputStream in;
    BufferedReader reader;
    String testPhrase;
    int deletetimes = 0;
    int previoushover = 0;
    int previoustouchpoint = 0;
    SoundPool sp;
    int soundID1;
    int soundID2;
    float volume;
    String userid;
    String date;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userid = getIntent().getStringExtra("userid");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        char last = userid.charAt(userid.length() - 1);

        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        imageView = (ImageView) this.findViewById(R.id.imageView1);
        tv_touchPoint = (TextView)findViewById(R.id.textView1);
        Display currentDisplay = getWindowManager().getDefaultDisplay();
        screenwidth = currentDisplay.getWidth();
        screenheight = currentDisplay.getHeight();
        tts = new TextToSpeech(this,this);
        tts.setLanguage(Locale.US);

        start_time = System.currentTimeMillis();

        spellCheck = new SpellCheck();
        wordFreqTmp = getResources().getStringArray(R.array.wordFreq);
        SpellCheck.WordFreq[] wf = new SpellCheck.WordFreq[wordFreqTmp.length];
        StringTokenizer st;
        for (int i = 0; i < wf.length; ++i)
        {
            st = new StringTokenizer(wordFreqTmp[i]);
            String word = st.nextToken();
            int freq = Integer.parseInt(st.nextToken());
            wf[i] = spellCheck.getWordFreq(word, freq);
        }
        spellCheck.setDictionary(wf); // done!

        //based on the user's id, assign a set of phrase to type
        switch(last){
            case '1':
                try {
                    in = this.getAssets().open("Phrase_1.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '2':
                try {
                    in = this.getAssets().open("Phrase_2.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '3':
                try {
                    in = this.getAssets().open("Phrase_3.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '4':
                try {
                    in = this.getAssets().open("Phrase_4.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '5':
                try {
                    in = this.getAssets().open("Phrase_5.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '6':
                try {
                    in = this.getAssets().open("Phrase_6.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '7':
                try {
                    in = this.getAssets().open("Phrase_7.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '8':
                try {
                    in = this.getAssets().open("Phrase_8.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '9':
                try {
                    in = this.getAssets().open("Phrase_9.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case '0':
                try {
                    in = this.getAssets().open("Phrase_0.txt");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        
        reader = new BufferedReader(new InputStreamReader(in));
        try {
            if(reader != null && reader.ready())
                testPhrase = reader.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String line_1 = String.format("%s,%s,%s,%s\n","Start Time", "Finish Time", "Output","Correct Phrase");
        File root = Environment.getExternalStorageDirectory();
        File userstudy = new File (root, userid + "_" + date + "_CORRECT_" + ".csv");

        String line_detail = String.format("%s,%s,%s\n","Touch Time", "Finish Time", "Output Char");
        File userstudy1 = new File (root, userid + "_" + date + "_detail.csv");
        String line_phrase = String.format("%d,%s\n",System.currentTimeMillis(),testPhrase.toUpperCase());


        try {
            writer = new FileWriter(userstudy);
            writer.write(line_detail);
            writer.write(line_phrase);
            writer.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            writer_1 = new FileWriter(userstudy1);
            writer_1.write(line_detail);
            writer_1.write(line_phrase);
            writer_1.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        bitmap = Bitmap.createBitmap((int) screenwidth, (int) screenheight,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        imageView.setImageBitmap(bitmap);

        // Load the sound
        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
//        loaded = true;
            }
        });
        soundID1 = sp.load(this, R.raw.dingb, 1);
        soundID2 = sp.load(this, R.raw.dingc, 1);

        AudioManager am =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)/2,
                0);

        float actualVolume = (float) am
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) am
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;

        mp = MediaPlayer.create(MainActivity.this, R.raw.dinga);
        imageView.setOnTouchListener(this);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            }
        }
        else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        float constant = screenheight/15;
        if (event.getPointerCount() > 1){
            doubletouch = 1;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialx = event.getX();
                initialy = event.getY();
                //new DingTask().execute(sp);

                canvas.drawCircle(initialx, initialy, constant , paint);
                canvas.drawCircle(initialx, initialy + screenheight/6, constant , paint);
                canvas.drawCircle(initialx, initialy + screenheight/3, constant , paint);
                canvas.drawCircle(initialx, initialy - screenheight/6, constant , paint);
                canvas.drawCircle(initialx, initialy - screenheight/3, constant , paint);
                canvas.drawCircle(initialx + screenheight/6, initialy, constant , paint);
                canvas.drawCircle(initialx + screenheight/6, initialy + screenheight/6, constant , paint);
                canvas.drawCircle(initialx + screenheight/6, initialy + screenheight/3, constant , paint);
                canvas.drawCircle(initialx + screenheight/6, initialy - screenheight/6, constant , paint);
                canvas.drawCircle(initialx + screenheight/6, initialy - screenheight/3, constant , paint);
                canvas.drawCircle(initialx - screenheight/6, initialy, constant , paint);
                canvas.drawCircle(initialx - screenheight/6, initialy + screenheight/6, constant , paint);
                canvas.drawCircle(initialx - screenheight/6, initialy + screenheight/3, constant , paint);
                canvas.drawCircle(initialx - screenheight/6, initialy - screenheight/6, constant , paint);
                canvas.drawCircle(initialx - screenheight/6, initialy - screenheight/3, constant , paint);
                imageView.invalidate();
                enter_time = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                activex = event.getX();
                activey = event.getY();
                //long press to activate ENTER
                if (System.currentTimeMillis() - enter_time > 3000 && activate <= 1){
                    tts.speak("enter", TextToSpeech.QUEUE_FLUSH, null);
                    break;
                }
                //compute the dots in the Braille Patterns that the user's finger motion path passes through
                //e.g., ithe IDs of the dots go from the first row, second row and the last row horizontally
                //pointX records whether the corresponding dot is touch. e.g., point1 = 1 means the first dot is touched
                if (Math.sqrt((activex - (initialx - screenheight/6))*(activex - (initialx - screenheight/6))+(activey-(initialy - screenheight/3))*(activey-(initialy - screenheight/3))) < constant && point1 == 0){
                    point1 = 1;
                    tv_touchPoint.setText("point1");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 1;
                }
                if (Math.sqrt((activex - (initialx))*(activex - (initialx))+(activey-(initialy - screenheight/3))*(activey-(initialy - screenheight/3))) < constant && point2 == 0){
                    point2 = 1;
                    tv_touchPoint.setText("point2");
                    activate ++;
                    if (previoushover%2 == 0)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 2;
                }
                if (Math.sqrt((activex - (initialx + screenheight/6))*(activex - (initialx + screenheight/6))+(activey-(initialy - screenheight/3))*(activey-(initialy - screenheight/3))) < constant && point3 == 0){
                    point3 = 1;
                    tv_touchPoint.setText("point3");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 3;
                }
                if (Math.sqrt((activex - (initialx - screenheight/6))*(activex - (initialx - screenheight/6))+(activey-(initialy - screenheight/6))*(activey-(initialy - screenheight/6))) < constant && point4 == 0){
                    point4 = 1;
                    tv_touchPoint.setText("point4");
                    activate ++;
                    if (previoushover%2 == 0)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 4;
                }
                if (Math.sqrt((activex - (initialx))*(activex - (initialx))+(activey-(initialy - screenheight/6))*(activey-(initialy - screenheight/6))) < constant && point5 == 0){
                    point5 = 1;
                    tv_touchPoint.setText("point5");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 5;
                }
                if (Math.sqrt((activex - (initialx + screenheight/6))*(activex - (initialx + screenheight/6))+(activey-(initialy - screenheight/6))*(activey-(initialy - screenheight/6))) < constant && point6 == 0){
                    point6 = 1;
                    tv_touchPoint.setText("point6");
                    activate ++;
                    if (previoushover%2 == 0)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 6;
                }
                if (Math.sqrt((activex - (initialx - screenheight/6))*(activex - (initialx - screenheight/6))+(activey-(initialy))*(activey-(initialy))) < constant && point7 == 0){
                    point7 = 1;
                    tv_touchPoint.setText("point7");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 7;
                }
                if (Math.sqrt((activex - (initialx))*(activex - (initialx))+(activey-(initialy))*(activey-(initialy))) < constant && point8 == 0){
                    point8 = 1;
                    tv_touchPoint.setText("point8");
                    activate ++;
                    if (previoushover == 0 || previoushover%2 == 0)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 8;
                }
                if (Math.sqrt((activex - (initialx + screenheight/6))*(activex - (initialx + screenheight/6))+(activey-(initialy))*(activey-(initialy))) < constant && point9 == 0){
                    point9 = 1;
                    tv_touchPoint.setText("point9");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 9;
                }
                if (Math.sqrt((activex - (initialx - screenheight/6))*(activex - (initialx - screenheight/6))+(activey-(initialy + screenheight/6))*(activey-(initialy + screenheight/6))) < constant && point10 == 0){
                    point10 = 1;
                    tv_touchPoint.setText("point10");
                    activate ++;
                    if (previoushover%2 == 0)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 10;
                }
                if (Math.sqrt((activex - (initialx))*(activex - (initialx))+(activey-(initialy + screenheight/6))*(activey-(initialy + screenheight/6))) < constant && point11 == 0){
                    point11 = 1;
                    tv_touchPoint.setText("point11");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 11;
                }
                if (Math.sqrt((activex - (initialx + screenheight/6))*(activex - (initialx + screenheight/6))+(activey-(initialy + screenheight/6))*(activey-(initialy + screenheight/6))) < constant && point12 == 0){
                    point12 = 1;
                    tv_touchPoint.setText("point12");
                    activate ++;
                    if (previoushover%2 == 0)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 12;
                }
                if (Math.sqrt((activex - (initialx - screenheight/6))*(activex - (initialx - screenheight/6))+(activey-(initialy + screenheight/3))*(activey-(initialy + screenheight/3))) < constant && point13 == 0){
                    point13 = 1;
                    tv_touchPoint.setText("point13");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 13;
                }
                if (Math.sqrt((activex - (initialx))*(activex - (initialx))+(activey-(initialy + screenheight/3))*(activey-(initialy + screenheight/3))) < constant && point14 == 0){
                    point14 = 1;
                    tv_touchPoint.setText("point14");
                    activate ++;
                    if (previoushover%2 == 0)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 14;
                }
                if (Math.sqrt((activex - (initialx + screenheight/6))*(activex - (initialx + screenheight/6))+(activey-(initialy + screenheight/3))*(activey-(initialy + screenheight/3))) < constant && point15 == 0){
                    point15 = 1;
                    tv_touchPoint.setText("point15");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 15;
                }
                if (point7 == 1 && point8 == 1 && Math.abs(activey - initialy - screenheight/6) <  constant && activex < initialx - screenheight/6 - constant && point10 == 0){
                    point10 = 1;
                    tv_touchPoint.setText("point10");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 10;
                }
                if (point9 == 1 && point8 == 1 && Math.abs(activey - initialy - screenheight/6) <  constant && activex > initialx + screenheight/6 + constant && point12 == 0){
                    point12 = 1;
                    tv_touchPoint.setText("point12");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 12;
                }
                if (point7 == 1 && point8 == 1 && point10 == 1 && Math.abs(activey - initialy - screenheight/3) <  constant && activex < initialx - screenheight/6 - constant && point13 == 0){
                    point13 = 1;
                    tv_touchPoint.setText("point13");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 13;
                }
                if (point9 == 1 && point8 == 1 && point12 == 1 && Math.abs(activey - initialy - screenheight/3) <  constant && activex > initialx + screenheight/6 + constant && point15 == 0){
                    point15 = 1;
                    tv_touchPoint.setText("point15");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 15;
                }
                if (point7 == 1 && point8 == 1 && Math.abs(activey - initialy + screenheight/6) <  constant && activex < initialx - screenheight/6 - constant && point4 == 0){
                    point4 = 1;
                    tv_touchPoint.setText("point4");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 4;
                }
                if (point9 == 1 && point8 == 1 && Math.abs(activey - initialy + screenheight/6) <  constant && activex > initialx + screenheight/6 + constant && point6 == 0){
                    point6 = 1;
                    tv_touchPoint.setText("point6");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 6;
                }
                if (point7 == 1 && point8 == 1 && point4 == 1 && Math.abs(activey - initialy + screenheight/3) <  constant && activex < initialx - screenheight/6 - constant && point1 == 0){
                    point1 = 1;
                    tv_touchPoint.setText("point1");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 1;
                }
                if (point9 == 1 && point8 == 1 && point6 == 1 && Math.abs(activey - initialy + screenheight/3) <  constant && activex > initialx + screenheight/6 + constant && point3 == 0){
                    point3 = 1;
                    tv_touchPoint.setText("point3");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 3;
                }
                if (point8 == 1 && point11 == 1 && point14 == 1 && Math.abs(activex - initialx - screenheight/6) < constant && activey > initialy + screenheight/3 + constant && point15 == 0){
                    point15 = 1;
                    tv_touchPoint.setText("point15");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 15;
                }
                if (point8 == 1 && point5 == 1 && point2 == 1 && Math.abs(activex - initialx - screenheight/6) < constant && activey < initialy - screenheight/3 - constant && point3 == 0){
                    point3 = 1;
                    tv_touchPoint.setText("point3");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 3;
                }
                if (point8 == 1 && point10 == 1 && point11 == 1 && activex < initialx - screenheight/6 - constant && Math.abs(activey - initialy - screenheight/3) < constant && point13 == 0){
                    point13 = 1;
                    tv_touchPoint.setText("point13");
                    activate ++;
                    if (previoushover%2 == 1)
                        new DingTask().execute(sp,new Integer(soundID2));
                    else
                        new DingTask().execute(sp,new Integer(soundID1));
                    previoushover = 13;
                }
                break;
            case MotionEvent.ACTION_UP:
                //the logic for determining what letter is entered depending on the pattern of the touched dots
                canvas.drawColor(0, Mode.CLEAR);
                imageView.invalidate();
                previoushover = 0;
                int length = ostr.length();
                if (doubletouch == 1 && length != 0 && event.getX() < 20){
                    phrase_finish_time = System.currentTimeMillis();
                    doubletouch = 0;
                    String line_4 = String.format("%d,%d,%s\n",System.currentTimeMillis(),0,"delete");
                    try {
                        writer_1.write(line_4);
                        writer_1.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    deletetimes += 1;
                    ostr = ostr.substring(0, ostr.length()-1);
                    tv_touchPoint.setText(ostr);
                    tts.speak("delete", TextToSpeech.QUEUE_FLUSH, null);
                    break;
                }
                else if (activate == 1 || activate == 0){
                    if (System.currentTimeMillis() - enter_time > 3000){
                        finish_time = System.currentTimeMillis();
                        try {
                            testPhrase = reader.readLine();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        boolean spellCheckOn = true;
                        //String presentedPhrase = ostr.toLowerCase(Locale.getDefault()).trim();
                        String transcribedPhraseUncorrected = ostr.toString().toLowerCase(Locale.getDefault()).trim();
                        String transcribedPhraseCorrected = spellCheckOn ? spellCheck.correctPhrase(transcribedPhraseUncorrected)
                                : transcribedPhraseUncorrected;
                        long temp_start = start_time;
                        long temp_end = phrase_finish_time;
                        long time_interval = temp_end - temp_start;
                        int temp_length = transcribedPhraseCorrected.length();
                        String temp_corrected = transcribedPhraseCorrected.toUpperCase();
                        long loop_start, loop_end;
                        long final_time = temp_end;
                        for (int i = 0; i < temp_length; i++){
                            loop_start = temp_start + i * time_interval / (temp_length + 2*deletetimes);
                            loop_end = loop_start + time_interval/(temp_length + 2*deletetimes);
                            if (loop_end > temp_end){
                                loop_start = temp_end;
                                loop_end = temp_end;
                            }
                            String line_3;
                            if (temp_corrected.charAt(i) == ' '){
                                line_3 = String.format("%d,%d,%s\n",loop_start,0,"space");
                            }
                            else{
                                line_3 = String.format("%d,%d,%s\n",loop_start,loop_end,temp_corrected.charAt(i));
                            }

                            try {
                                writer.write(line_3);
                                //writer_1.write(line_5);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            try {
                                writer.flush();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < deletetimes; i++){
                            String line_3 = String.format("%d,%d,%s\n",final_time,final_time,"A");
                            String line_7 = String.format("%d,%d,%s\n",final_time,0,"delete");
                            try {
                                writer.write(line_3);
                                //writer_1.write(line_5);
                                writer.write(line_7);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            try {
                                writer.flush();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        String line_4 = String.format("%d,%s\n",System.currentTimeMillis(),ostr);
                        String line_5 = String.format("%d,%s\n",System.currentTimeMillis(),testPhrase.toUpperCase());
                        String line_6 = String.format("%d,%s\n",phrase_finish_time,transcribedPhraseCorrected.toUpperCase());
                        try {
                            writer.write(line_6);
                            //writer_1.write(line_5);
                            writer.write(line_5);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        try {
                            writer_1.write(line_4);
                            //writer_1.write(line_5);
                            writer_1.write(line_5);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        tts.speak(testPhrase, TextToSpeech.QUEUE_FLUSH, null);
                        deletetimes = 0;
                        finish_time = System.currentTimeMillis();
                        ostr = "";
                        start_time = System.currentTimeMillis();
                        break;
                    }
                    if (previousletter.equals("A") && System.currentTimeMillis() - previoustime < 800){
                        ostr = ostr.substring(0, ostr.length()-1);
                        ostr = ostr + "K";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"K");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if (previousletter.equals("C") && System.currentTimeMillis() - previoustime < 800){
                        ostr = ostr.substring(0, ostr.length()-1);
                        ostr = ostr + "M";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"M");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else{
                        ostr = ostr + "A";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"A");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else if (activate == 2){
                    if (point5 == 1 || point11 == 1){
                        ostr = ostr + "B";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"B");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if (point7 == 1 || point9 == 1){
                        if (previousletter.equals("A") && System.currentTimeMillis() - previoustime < 800){
                            ostr = ostr.substring(0, ostr.length()-1);
                            ostr = ostr + "U";
                            previoustime = System.currentTimeMillis();
                            phrase_finish_time = System.currentTimeMillis();
                            String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"U");
                            try {
                                writer_1.write(line_3);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            try {
                                writer_1.flush();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        else if (previousletter.equals("C") && System.currentTimeMillis() - previoustime < 800){
                            ostr = ostr.substring(0, ostr.length()-1);
                            ostr = ostr + "X";
                            previoustime = System.currentTimeMillis();
                            phrase_finish_time = System.currentTimeMillis();
                            String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"X");
                            try {
                                writer_1.write(line_3);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            try {
                                writer_1.flush();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        else{
                            ostr = ostr + "C";
                            previoustime = System.currentTimeMillis();
                            phrase_finish_time = System.currentTimeMillis();
                            String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"C");
                            try {
                                writer_1.write(line_3);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            try {
                                writer_1.flush();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    else if (point4 == 1 || point12 == 1){
                        ostr = ostr + "E";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"E");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if (point6 == 1 || point10 == 1){
                        ostr = ostr + "I";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"I");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else if (activate == 3){
                    if ((point4 == 1 && point5 == 1) || (point7 == 1 && point11 == 1) || (point9 == 1 && point12 == 1)){
                        ostr = ostr + "D";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"D");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point6 == 1 && point5 == 1) || (point7 == 1 && point10 == 1) || (point9 == 1 && point11 == 1)){
                        ostr = ostr + "F";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"F");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point9 == 1 && point5 == 1) || (point7 == 1 && point4 == 1) || (point12 == 1 && point11 == 1)){
                        ostr = ostr + "H";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"H");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point7 == 1 && point5 == 1) || (point6 == 1 && point9 == 1) || (point10 == 1 && point11 == 1)){
                        ostr = ostr + "J";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"J");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point2 == 1 && point5 == 1) || (point11 == 1 && point14 == 1) || (point5 == 1 && point11 == 1)){
                        ostr = ostr + "L";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"L");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point4 == 1 && point10 == 1) || (point2 == 1 && point6 == 1) || (point12 == 1 && point14 == 1)){
                        ostr = ostr + "O";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"O");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point3 == 1 && point5 == 1) || (point6 == 1 && point11 == 1) || (point10 == 1 && point13 == 1)){
                        ostr = ostr + "S";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"S");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else if (activate == 4){
                    if ((point4 == 1 && point5 == 1 && point7 == 1)||(point5 == 1 && point6 == 1 && point9 == 1)||(point7 == 1 && point10 == 1 && point11 == 1)||(point9 == 1 && point11 == 1 && point12 == 1)){
                        ostr = ostr + "G";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"G");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point9 == 1 && point12 == 1 && point14 == 1)||(point2 == 1 && point3 == 1 && point6 == 1)||(point7 == 1 && point13 == 1 && point11 == 1)||(point4 == 1 && point5 == 1 && point10 == 1)){
                        ostr = ostr + "N";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"N");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point2 == 1 && point5 == 1 && point3 == 1)||(point5 == 1 && point6 == 1 && point11 == 1)||(point7 == 1 && point10 == 1 && point13 == 1)||(point9 == 1 && point11 == 1 && point14 == 1)){
                        ostr = ostr + "P";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"P");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point2 == 1 && point5 == 1 && point6 == 1)||(point5 == 1 && point11 == 1 && point9 == 1)||(point14 == 1 && point12 == 1 && point11 == 1)||(point4 == 1 && point7 == 1 && point10 == 1)){
                        ostr = ostr + "R";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"R");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point3 == 1 && point5 == 1 && point6 == 1)||(point5 == 1 && point7 == 1 && point10 == 1)||(point6 == 1 && point9 == 1 && point11 == 1)||(point10 == 1 && point11 == 1 && point13 == 1)){
                        ostr = ostr + "T";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"T");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point4 == 1 && point1 == 1 && point7 == 1)||(point5 == 1 && point2 == 1 && point9 == 1)||(point5 == 1 && point12 == 1 && point11 == 1)||(point14 == 1 && point11 == 1 && point15 == 1)){
                        ostr = ostr + "V";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"V");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point4 == 1 && point5 == 1 && point2 == 1)||(point5 == 1 && point7== 1 && point11 == 1)||(point14 == 1 && point10 == 1 && point11 == 1)||(point9 == 1 && point6 == 1 && point12 == 1)){
                        ostr = ostr + "W";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"W");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point1 == 1 && point5 == 1 && point7 == 1)||(point2 == 1 && point6 == 1 && point9 == 1)||(point4 == 1 && point10 == 1 && point11 == 1)||(point14 == 1 && point15 == 1 && point12 == 1)){
                        ostr = ostr + "Z";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"Z");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else if (activate == 5){
                    if ((point2 == 1 && point3 == 1 && point5 == 1 && point6 == 1)||(point4 == 1 && point5 == 1 && point7 == 1 && point10 == 1)||(point5 == 1 && point6 == 1 && point9 == 1 && point11 == 1)||(point7 == 1 && point10 == 1 && point11 == 1 && point13 == 1)||(point9 == 1 && point11 == 1 && point12 == 1 && point14 == 1)){
                        ostr = ostr + "Q";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"Q");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if ((point2 == 1 && point1 == 1 && point5 == 1 && point7 == 1)||(point2 == 1 && point3 == 1 && point6 == 1 && point9 == 1)||(point5 == 1 && point5 == 1 && point10 == 1 && point11 == 1)||(point7 == 1 && point14 == 1 && point11 == 1 && point13 == 1)||(point9 == 1 && point15 == 1 && point12 == 1 && point14 == 1)){
                        ostr = ostr + "Y";
                        previoustime = System.currentTimeMillis();
                        phrase_finish_time = System.currentTimeMillis();
                        String line_3 = String.format("%d,%d,%s\n",enter_time,previoustime,"Y");
                        try {
                            writer_1.write(line_3);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        try {
                            writer_1.flush();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

                tv_touchPoint.setText(ostr);

                if (ostr.length() > 0){
                    previousletter = ostr.substring(ostr.length() - 1);
                }
                if (ostr.length() == 0 || (ostr.length() == length && !previousletter.equals("K") && !previousletter.equals("U") && !previousletter.equals("X") && !previousletter.equals("M"))){
                    tts.speak("no", TextToSpeech.QUEUE_FLUSH, null);
                }
                else{
                    //tts.speak(previousletter, TextToSpeech.QUEUE_FLUSH, null);
                }

                activate = 0;
                point1 = 0;
                point2 = 0;
                point3 = 0;
                point4 = 0;
                point5 = 0;
                point6 = 0;
                point7 = 0;
                point8 = 0;
                point9 = 0;
                point10 = 0;
                point11 = 0;
                point12 = 0;
                point13 = 0;
                point14 = 0;
                point15 = 0;

                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP: //repeat the test phrase
                if (action == KeyEvent.ACTION_DOWN) {
                    tts.speak(testPhrase, TextToSpeech.QUEUE_FLUSH, null);
                    tv_touchPoint.setText(testPhrase);
                }
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN: // space key
                if (action == KeyEvent.ACTION_DOWN) {
                    ostr = ostr + " ";
                    String line_4 = String.format("%d,%d,%s\n",System.currentTimeMillis(),0,"space");
                    try {
                        writer_1.write(line_4);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        writer_1.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    tts.speak("space", TextToSpeech.QUEUE_FLUSH, null);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    private class DingTask extends AsyncTask<Object, Integer, Long> {
        protected Long doInBackground(Object... obj) {
            ((SoundPool)obj[0]).play(((Integer)obj[1]).intValue(), volume, volume, 1, 0, 1f);

            return(new Long(0));
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }

}
