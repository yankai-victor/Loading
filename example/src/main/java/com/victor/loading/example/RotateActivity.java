package com.victor.loading.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.victor.loading.rotate.RotateLoading;

/**
 * @author Victor
 *         create at 15/7/28 21:37
 */
public class RotateActivity extends Activity {

    private RotateLoading rotateLoading;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rotateLoading.isStart()) {
                    rotateLoading.stop();
                    button.setText(R.string.start);
                } else {
                    rotateLoading.start();
                    button.setText(R.string.stop);
                }
            }
        });
    }

}
