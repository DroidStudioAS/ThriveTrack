package com.aa.thrivetrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class DiaryActivity extends AppCompatActivity {

    ConstraintLayout diaryContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        /**Of Ui Initializations**/
        diaryContainer=(ConstraintLayout) findViewById(R.id.diaryContainer);
        /**Of Ui Initializations**/

    }
}