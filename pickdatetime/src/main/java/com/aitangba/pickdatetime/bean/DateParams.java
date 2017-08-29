package com.aitangba.pickdatetime.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

/**
 * Created by fhf11991 on 2017/8/29.
 */

public class DateParams {
    public int style;
    public Date currentDate;
    public Date startDate;
    public Date endDate;

    public DateParams(@Style int style) {
        this.style = style;
    }

    @IntDef({STYLE_ALL, STYLE_DATE_ONLY, STYLE_TIME_ONLY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Style{}

    public final static int STYLE_ALL = 1;
    public final static int STYLE_DATE_ONLY = 2;
    public final static int STYLE_TIME_ONLY = 3;
}
