package fajieyefu.com.luoxiang.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-13.
 */

public class Option {
    private int option_code;
    private String option_name;
    private int enable_flag;
    private List<OptionItem> optionItems;

    public List<OptionItem> getOptionItems() {
        return optionItems;
    }

    public void setOptionItems(List<OptionItem> optionItems) {
        this.optionItems = optionItems;
    }

    public int getOption_code() {
        return option_code;
    }

    public void setOption_code(int option_code) {
        this.option_code = option_code;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public int getEnable_flag() {
        return enable_flag;
    }

    public void setEnable_flag(int enable_flag) {
        this.enable_flag = enable_flag;
    }
}
