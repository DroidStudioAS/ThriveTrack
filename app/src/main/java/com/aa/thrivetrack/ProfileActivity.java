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
import com.aa.thrivetrack.helpers.StreakHelper;
import com.aa.thrivetrack.models.Data;
import com.aa.thrivetrack.models.Streak;
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

    List<CalendarDay> streakDays;

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
        /******End Of OnClickListeners******/

        streakDays = new ArrayList<>();
        streakDays=buildCalendarDays();

        calendarView.setCalendarDays(streakDays);
        setProfilePageContent();
    }

    public void setProfilePageContent(){
        User user = SessionStorage.getUserData().getUser();
        usernameTv.setText(SessionStorage.getUsername());
        userRankTv.setText(user.getUser_rank());
        userRankTv.setTextColor(Color.parseColor(StreakHelper.getRankColor(user.getUser_rank())));
        userBadgeIv.setImageDrawable(StreakHelper.getUserBadge(getApplicationContext()));
    }


    @Override
    public void onChange(String newValue) {
        usernameTv.setText(newValue);
    }

    public List<CalendarDay> buildCalendarDays(){
        for(Streak streak : SessionStorage.getUserData().getStreaks()){
            ArrayList<String> datesInRange = DateHelper.datesInRange(streak.getStreak_start(), streak.getStreak_end());
            addDayToCalendar(datesInRange);
        }

        return streakDays;
    }
    public void addDayToCalendar(ArrayList<String> datesInRange){
        for(String date : datesInRange){
            String[] parsed = date.split("-");
            int year = Integer.parseInt(parsed[0]);
            int month = Integer.parseInt(parsed[1])-1;
            int day = Integer.parseInt(parsed[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day);
            CalendarDay calendarDay = new CalendarDay(calendar);

            calendarDay.setLabelColor(R.color.accentgreen);
            streakDays.add(calendarDay);
        }
    }

}