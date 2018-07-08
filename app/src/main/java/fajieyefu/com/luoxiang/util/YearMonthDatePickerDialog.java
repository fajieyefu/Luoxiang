package fajieyefu.com.luoxiang.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Administrator on 2017-06-06.
 */

public class YearMonthDatePickerDialog extends DatePickerDialog {
    public YearMonthDatePickerDialog(Context context, int themeResId, OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
       View view = ((ViewGroup) ((ViewGroup) (getDatePicker().getChildAt(0))).getChildAt(0)).getChildAt(2);
        if (view!=null){
            view.setVisibility(View.GONE);
        }

    }


}
