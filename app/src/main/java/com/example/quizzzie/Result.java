package com.example.quizzzie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    TextView tvResult;
    Button btnStartAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = (TextView) findViewById(R.id.tvResult);
        btnStartAgain = (Button) findViewById(R.id.btnStartAgain);

        final Intent intent = getIntent();
        String result = intent.getStringExtra("Result");

        tvResult.setText(result);

        btnStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Result.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
