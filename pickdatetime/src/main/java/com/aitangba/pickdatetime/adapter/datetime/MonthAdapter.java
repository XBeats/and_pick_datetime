package com.aitangba.pickdatetime.adapter.datetime;

import android.support.annotation.NonNull;

import com.aitangba.pickdatetime.bean.DateParams;
import com.aitangba.pickdatetime.bean.DatePick;

/**
 * Created by fhf11991 on 2017/8/29.
 */

public class MonthAdapter extends DatePickAdapter {

    public MonthAdapter(@NonNull DateParams dateParams, @NonNull DatePick datePick) {
        super(dateParams, datePick);
    }

    @Override
    public int getCurrentIndex() {
        return mData.indexOf(mDatePick.month);
    }

    @Override
    public void refreshValues() {
        setData(getArray(12));
    }
}
