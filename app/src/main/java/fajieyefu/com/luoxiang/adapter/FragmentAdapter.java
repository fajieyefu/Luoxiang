package fajieyefu.com.luoxiang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fajieyefu.com.luoxiang.fragment.ClaimFragment;
import fajieyefu.com.luoxiang.fragment.MyClueInfoFragment;

/**
 * Created by qiancheng on 2016/9/21.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
//        return fragmentList.get(position);
        Fragment fragment;
        if (position==0){
            fragment= new MyClueInfoFragment();
        }else{
            fragment=new ClaimFragment();
        }
        return fragment;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
