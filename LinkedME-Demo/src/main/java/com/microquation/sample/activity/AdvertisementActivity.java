package com.microquation.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microquation.linkedme.android.LinkedME;
import com.microquation.sample.R;

/**
 * Created by LinkedME06 on 24/02/2017.
 */

public class AdvertisementActivity extends BaseActivity {

    private TextView down_timer;
    //计时器
    private CountDownTimer timer;
    //判断计时器是否被取消
    private boolean isTimerCanceled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement);
        startCountDownTime(5);
        Button show_ad = (Button) findViewById(R.id.show_ad);
        down_timer = (TextView) findViewById(R.id.down_timer);
        show_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdvertisementActivity.this, AdDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //微信中通过应用宝唤起退台后台的APP
        Log.d(getClass().getSimpleName(), "onCreate: isTimerCanceled=" + isTimerCanceled);
    }

    private void startCountDownTime(long time) {
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                down_timer.setText(String.format(getString(R.string.count_down_str), millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                // TODO: 27/02/2017 广告演示：广告展示完毕需要调用该方法执行深度链接跳转
                //广告显示完后执行跳转到详情页面
                LinkedME.getInstance().setImmediate(true);
                finish();
            }

        };
        timer.start();// 开始计时
    }

    @Override
    protected void onResume() {
        Log.d(getClass().getSimpleName(), "onResume: isTimerCanceled=" + isTimerCanceled + "timer=" + timer);
        //此处判断timer==null是为了解决应用宝唤起后台APP后，有时isTimerCanceled一直为false的情况
        if (timer == null || isTimerCanceled) {
            //若被取消，则直接finish掉
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(getClass().getSimpleName(), "onStop: isTimerCanceled=" + isTimerCanceled);
        timer.cancel();
        isTimerCanceled = true;
        super.onStop();
    }
}
