package com.yxj.countdown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvResult;
    private CountDown countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);
        findViewById(R.id.btn_restart).setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);

        countDown = new CountDown(5, new CountDown.Callback() {

            @Override
            public void onPreStart() {
                tvResult.setText("准备开始");
            }

            @Override
            public void onStep(int count) {
                tvResult.setText("倒计时：" + count);
            }

            @Override
            public void onSuccess() {
                tvResult.setText("倒计时结束");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                countDown.start();
                break;
            case R.id.btn_stop:
                countDown.stop();
                break;
            case R.id.btn_reset:
                countDown.reset();
                break;
            case R.id.btn_restart:
                countDown.restart();
                break;
        }
    }
}
