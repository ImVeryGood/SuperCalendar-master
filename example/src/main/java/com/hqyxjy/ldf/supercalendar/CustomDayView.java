package com.hqyxjy.ldf.supercalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.State;
import com.ldf.calendar.interf.IDayRenderer;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.DayView;

import java.util.HashMap;

/**
 * Created by ldf on 17/6/26.
 */

@SuppressLint("ViewConstructor")
public class CustomDayView extends DayView {

    private TextView dateTv;
    private ImageView marker;
    private View selectedBackground;
    private View todayBackground;
    private final CalendarDate today = new CalendarDate();

    /**
     * 构造器
     *
     * @param context        上下文
     * @param layoutResource 自定义DayView的layout资源
     */
    public CustomDayView(Context context, int layoutResource) {
        super(context, layoutResource);
        dateTv = (TextView) findViewById(R.id.date);
        marker = (ImageView) findViewById(R.id.maker);
        selectedBackground = findViewById(R.id.selected_background);
        todayBackground = findViewById(R.id.today_background);
    }

    @Override
    public void refreshContent() {
        renderToday(day.getDate(), day.getState());
        renderSelect(day.getState());
        renderMarker(day.getDate(), day.getState());
        super.refreshContent();
    }

    private void renderMarker(CalendarDate date, State state) {
            Log.d("SSSSSSSSSSSS", "renderMarker: "+state);
        if (Utils.loadMarkData().containsKey(date.toString())&&state==State.CURRENT_MONTH) {
            marker.setVisibility(VISIBLE);
            if (Utils.loadMarkData().get(date.toString()).equals("0")) {
                marker.setEnabled(true);
            } else {
                marker.setEnabled(false);
            }

        } else {
            marker.setVisibility(GONE);
        }
}
    private void renderSelect(State state) {
        Log.d("SSSSSSSSSS", "renderSelect: "+state);
        if (state == State.SELECT) {
//            本月
            selectedBackground.setVisibility(VISIBLE);
            dateTv.setTextColor(Color.WHITE);
        } else if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
//            不是本月
            selectedBackground.setVisibility(GONE);
            // dateTv.setTextColor(Color.parseColor("#5b5b5b"));
            dateTv.setText("");
        } else {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(Color.parseColor("#111111"));
        }
    }

    private void renderToday(CalendarDate date, State state) {
        if (date != null) {
//            只渲染本月的
            if (date.equals(today) && state == State.SELECT) {
                dateTv.setText(date.day + "");
                todayBackground.setVisibility(VISIBLE);
            } else {
                dateTv.setText(date.day + "");
                todayBackground.setVisibility(GONE);
            }
        }

    }

    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context, layoutResource);
    }
}
