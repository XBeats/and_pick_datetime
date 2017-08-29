package com.aitangba.pickdatetime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aitangba.pickdatetime.adapter.datetime.DayAdapter;
import com.aitangba.pickdatetime.adapter.datetime.HourAdapter;
import com.aitangba.pickdatetime.adapter.datetime.MinuteAdapter;
import com.aitangba.pickdatetime.adapter.datetime.MonthAdapter;
import com.aitangba.pickdatetime.adapter.datetime.YearAdapter;
import com.aitangba.pickdatetime.bean.DateParams;
import com.aitangba.pickdatetime.bean.DatePick;
import com.aitangba.pickdatetime.view.OnWheelChangedListener;
import com.aitangba.pickdatetime.view.WheelView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by fhf11991 on 2017/8/29.
 */
public class DateTimePickerView extends LinearLayout {

    private static final String TAG = "WheelPicker";

    private LayoutParams mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    final DatePick mDatePick = new DatePick();
    private OnChangeListener mOnChangeListener;
    private WheelView mDayView;
    private DayAdapter mDayAdapter;

    public DateTimePickerView(Context context) {
        super(context);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DateTimePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.mOnChangeListener = onChangeListener;
    }

    public void show(@NonNull DateParams params) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(params.currentDate);
        mDatePick.setData(calendar);

        if(params.style == DateParams.STYLE_ALL || params.style == DateParams.STYLE_DATE_ONLY) {
            WheelView yearView = new WheelView(getContext());
            final YearAdapter yearAdapter = new YearAdapter(params, mDatePick);
            yearView.setCyclic(false);
            yearView.setAdapter(yearAdapter);
            yearView.setCurrentItem(yearAdapter.getCurrentIndex());
            mLayoutParams.weight = 3;
            addView(yearView, mLayoutParams);
            yearView.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    mDatePick.year = yearAdapter.getValue(newValue);
                    mDayAdapter.refreshValues();
                    DateTimePickerView.this.onChanged();
                }
            });

            WheelView monthView = new WheelView(getContext());
            final MonthAdapter monthAdapter = new MonthAdapter(params, mDatePick);
            monthView.setCyclic(true);
            monthView.setAdapter(monthAdapter);
            monthView.setCurrentItem(monthAdapter.getCurrentIndex());
            mLayoutParams.weight = 2;
            addView(monthView, mLayoutParams);
            monthView.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    mDatePick.month = monthAdapter.getValue(newValue);
                    mDayAdapter.refreshValues();
                    DateTimePickerView.this.onChanged();
                }
            });

            mDayView = new WheelView(getContext());
            mDayView.setCyclic(true);
            mDayView.setAdapter(mDayAdapter = new DayAdapter(params, mDatePick));
            mDayView.setCurrentItem(mDayAdapter.getCurrentIndex());
            mLayoutParams.weight = 2;
            addView(mDayView, mLayoutParams);
            mDayView.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    mDatePick.day = mDayAdapter.getValue(newValue);
                    DateTimePickerView.this.onChanged();
                }
            });
        }

        if(params.style == DateParams.STYLE_ALL || params.style == DateParams.STYLE_TIME_ONLY) {
            final HourAdapter hourAdapter = new HourAdapter(params, mDatePick);
            WheelView hourView = new WheelView(getContext());
            hourView.setCyclic(true);
            hourView.setAdapter(hourAdapter);
            hourView.setCurrentItem(hourAdapter.getCurrentIndex());
            hourView.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    mDatePick.hour = hourAdapter.getValue(newValue);
                    DateTimePickerView.this.onChanged();
                }
            });
            mLayoutParams.weight = 2;
            addView(hourView, mLayoutParams);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            TextView textView = new TextView(getContext());
            TextPaint paint = textView.getPaint();
            paint.setFakeBoldText(true);
            textView.setText(":");
            textView.setTextColor(0xff444444);
            addView(textView, layoutParams);

            final MinuteAdapter minuteAdapter = new MinuteAdapter(params, mDatePick);
            WheelView minuteView = new WheelView(getContext());
            minuteView.setCyclic(true);
            minuteView.setAdapter(minuteAdapter);
            minuteView.setCurrentItem(minuteAdapter.getCurrentIndex());
            minuteView.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    mDatePick.minute = minuteAdapter.getValue(newValue);
                    DateTimePickerView.this.onChanged();
                }
            });
            mLayoutParams.weight = 2;
            addView(minuteView, mLayoutParams);
        }
    }

    private void onChanged() {
        if(mOnChangeListener != null) {
            mOnChangeListener.onChanged(getSelectDate());
        }
    }

    public Date getSelectDate() {
        int year = mDatePick.year;
        int moth = mDatePick.month;
        int day = mDatePick.day;
        int hour = mDatePick.hour;
        int minute = mDatePick.minute;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, moth - 1, 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(day > maxDay) { // the max day of every month is not the same !!!
            day = 1;
            mDatePick.day = 1;
            mDayView.setCurrentItem(mDayAdapter.getCurrentIndex());
        }
        calendar.set(year, moth - 1, day, hour, minute);
        return calendar.getTime();
    }
}
