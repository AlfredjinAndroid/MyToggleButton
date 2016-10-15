package com.alfredjin.mytoggleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alfredjin.mytoggleview.view.ToggleView;

/**
 * 自定义开关
 *
 * @author Created by AlfredJin on 2016/10/10 14:52.
 */

public class MainActivity extends AppCompatActivity {

    private ToggleView toggleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleView = (ToggleView) findViewById(R.id.toggleView);

        //设置背景图片
        toggleView.setBackgroundBitmap(R.drawable.switch_background);

        //设置滑动按钮的图片
        toggleView.setSlidButton(R.drawable.slide_button);

        //设置滑动的状态
        toggleView.setSlidState(true);

        toggleView.setOnSwitchStateUpdateListener(new ToggleView.onSwitchStateUpdateListener() {

            @Override
            public void onStateUpdate(boolean state) {
                Toast.makeText(getApplicationContext(),
                        state ? "开关被打开了" : "开关被关闭了",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
