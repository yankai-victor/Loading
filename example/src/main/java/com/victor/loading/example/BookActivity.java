package com.victor.loading.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.victor.loading.book.BookLoading;

/**
 * @author Victor
 *         create at 15/7/28 21:31
 */
public class BookActivity extends Activity {

    private BookLoading bookLoading;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        bookLoading= (BookLoading) findViewById(R.id.bookloading);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookLoading.isStart()) {
                    button.setText(R.string.start);
                    bookLoading.stop();
                }else {
                    button.setText(R.string.stop);
                    bookLoading.start();
                }
            }
        });
    }


}
