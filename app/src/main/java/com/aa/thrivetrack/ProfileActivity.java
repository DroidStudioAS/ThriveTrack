package com.aa.thrivetrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aa.thrivetrack.callback.PatchCallback;
import com.aa.thrivetrack.dialogs.DeleteDialog;
import com.aa.thrivetrack.helpers.DateHelper;
import com.aa.thrivetrack.helpers.DialogHelper;
import com.aa.thrivetrack.models.Data;
import com.aa.thrivetrack.models.User;
import com.aa.thrivetrack.network.SessionStorage;
import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements PatchCallback {

    TextView usernameTv;
    TextView userRankTv;
    TextView changeUsernameTrigger;
    TextView changePasswordTrigger;
    TextView deleteTrigger;
    TextView streakTv;

    ImageView userBadgeIv;

    protected CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /******Start Of Ui Initializations******/
        usernameTv=(TextView) findViewById(R.id.usernameTv);
        userRankTv = (TextView) findViewById(R.id.userRankTv);
        changeUsernameTrigger = (TextView)findViewById(R.id.changeUsernameTrigger);
        changePasswordTrigger=(TextView)findViewById(R.id.changePasswordTrigger);
        deleteTrigger=(TextView)findViewById(R.id.deleteAcountTrigger);
        streakTv = (TextView) findViewById(R.id.userStreakTv);
        calendarView=(CalendarView) findViewById(R.id.calendarView);
        userBadgeIv=(ImageView)findViewById(R.id.userRankIv);
        /******End Of Ui Initializations******/
        String streakString = "You Have A " + String.valueOf(SessionStorage.getUserData().getUser().getUser_streak()) + " Day Streak";
        streakTv.setText(streakString);
        /******Start Of OnClickListeners******/
        changeUsernameTrigger.setOnClickListener(DialogHelper.openPatchDialog(ProfileActivity.this, "username"));
        changePasswordTrigger.setOnClickListener(DialogHelper.openPatchDialog(ProfileActivity.this,"password"));
        deleteTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog dd = new DeleteDialog(ProfileActivity.this,"user");
                dd.show();
            }
        });



        ArrayList<String> datesInRange = DateHelper.datesInRange(SessionStorage.getUserData().getStreaks().get(0).getStreak_start(),
                SessionStorage.getUserData().getStreaks().get(0).getStreak_end());
        List<CalendarDay> cd = new ArrayList<>();
        for(String x : datesInRange){
            String[] parsed = x.split("-");
            int year = Integer.parseInt(parsed[0]);
            int month = Integer.parseInt(parsed[1])-1;
            int day = Integer.parseInt(parsed[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day);

            CalendarDay calendarDay = new CalendarDay(calendar);

            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable drawable = getDrawable(R.drawable.ic_streak);
            //calendarDay.setBackgroundDrawable(drawable);
            calendarDay.setLabelColor(R.color.accentgreen);
            cd.add(calendarDay);



        }

        calendarView.setCalendarDays(cd);

        /******End Of OnClickListeners******/
        setProfilePageContent();
    }

    public void setProfilePageContent(){
        User user = SessionStorage.getUserData().getUser();
        SessionStorage.getUserData().getUser().setUser_rank("gold");
        usernameTv.setText(SessionStorage.getUsername());
        userRankTv.setText(user.getUser_rank());
        userBadgeIv.setImageDrawable(getUserBadge());
    }


    @Override
    public void onChange(String newValue) {
        usernameTv.setText(newValue);
    }

    public Drawable getUserBadge(){
        Drawable drawable = getDrawable(R.drawable.basic);
        switch (SessionStorage.getUserData().getUser().getUser_rank()){
            case "bronze":
                drawable= getDrawable(R.drawable.bronze);
                break;
            case "silver":
                drawable= getDrawable(R.drawable.silver);
                break;
            case "gold":
                drawable=getDrawable(R.drawable.gold);
                break;
            case "diamond":
                drawable=getDrawable(R.drawable.diamond);
                break;
        }
        return  drawable;
    }
}