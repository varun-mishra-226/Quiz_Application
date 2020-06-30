package com.example.quizzzie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Questions extends AppCompatActivity {

    TextView tvQuestionCount, tvValid, tvDone;
    ImageView ivQuestion;
    Button btn1, btn2, btn3, btn4;
    int chosen, locCorrect, selCorr=0, done=0;
    String[] options = new String[4];
    boolean optSelected;
    String score;

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> image = new ArrayList<String>();

    public class CartoonImage extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                myConnection.connect();
                InputStream in = myConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void newQuestion (){
        try {
            optSelected = false;

            Random random = new Random();
            chosen = random.nextInt(names.size());

            CartoonImage imageTask = new CartoonImage();
            Bitmap cartoonPic = imageTask.execute(image.get(chosen)).get();

            ivQuestion.setImageBitmap(cartoonPic);

            locCorrect = random.nextInt(4);

            ArrayList<Integer> used = new ArrayList<>();
            used.add(chosen);

            int notChosen;

            for (int i=0; i<4; i++){
                if (i == locCorrect)
                    options[i] = names.get(chosen);
                else{
                    notChosen = random.nextInt(names.size());

                    while (used.contains(notChosen))
                        notChosen = random.nextInt(names.size());

                    options[i] = names.get(notChosen);
                    used.add(notChosen);
                }
            }

            btn1.setText(options[0]);
            btn2.setText(options[1]);
            btn3.setText(options[2]);
            btn4.setText(options[3]);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cartoonSelect (final View view){
        if (!optSelected){
            optSelected = true;
            done += 1;
            final boolean corr;
            if (view.getTag().toString().equals(Integer.toString(locCorrect))) {
                selCorr += 1;
                corr = true;
            }
            else {
                corr = false;
            }

            score = "Score: "+String.valueOf((float)selCorr/done);
            tvQuestionCount.setText(score);
            tvDone.setText("Done: "+String.valueOf(done));

            new CountDownTimer(250, 250) {
                @Override
                public void onTick(long millisUntilFinished) {
                    view.setBackgroundResource(R.drawable.layout_button_click);
                    if (corr)
                        tvValid.setText("Correct!!");
                    else
                        tvValid.setText("Incorrect!!");
                }

                @Override
                public void onFinish() {
                    tvValid.setText("");
                    view.setBackgroundResource(R.drawable.layout_button);
                    newQuestion();
                }
            }.start();

            if (done==5){
                Intent intent = new Intent(Questions.this, Result.class);
                intent.putExtra("Result", score);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        ivQuestion = (ImageView) findViewById(R.id.ivQuestion);
        tvQuestionCount = (TextView)findViewById(R.id.tvQuesCount);
        tvValid = (TextView) findViewById(R.id.tvValid);
        tvDone = (TextView) findViewById(R.id.tvCount);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        names.add("TOM");
        names.add("HOMER SIMPSON");
        names.add("OPTIMUS PRIME");
        names.add("BUZZ LIGHTYEAR");
        names.add("BUGS BUNNY");
        names.add("CARTMAN");
        names.add("MICKEY MOUSE");
        names.add("FRED FLINTSTONE");
        names.add("INSPECTOR GADGET\n");
        names.add("JESSICA RABBIT");

        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image207.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image185.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image2051.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image2041.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image2031.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image2021.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image2011.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image2001.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image1991.jpg");
        image.add("https://besttoppers.com/wp-content/uploads/2011/09/image1981.jpg");

        newQuestion();
    }
}
