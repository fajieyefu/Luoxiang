package fajieyefu.com.luoxiang.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Administrator on 2017-07-14.
 */

public class MyTextWatcher implements TextWatcher {
    private EditText editText;
    public MyTextWatcher(EditText editText){
        this.editText=editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
