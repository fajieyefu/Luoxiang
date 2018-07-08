package fajieyefu.com.luoxiang.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fajieyefu.com.luoxiang.R;
import fajieyefu.com.luoxiang.adapter.FragmentAdapter;
import fajieyefu.com.luoxiang.fragment.ClaimFragment;
import fajieyefu.com.luoxiang.fragment.MyClueInfoFragment;

/**
 * Created by Administrator on 2017-10-17.
 */
public class ClueManageActivity extends FragmentActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.my_clue_info)
    TextView myClueInfo;
    @BindView(R.id.claim_info)
    TextView claimInfo;
    @BindView(R.id.id_tab_line_iv)
    ImageView idTabLineIv;
    @BindView(R.id.id_paper_vp)
    ViewPager idPaperVp;
    @BindView(R.id.add)
    ImageView add;
    private MyClueInfoFragment myClueInfoFragment;
    private ClaimFragment claimFragment;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    //当前选中页
    private int currentIndex;
    //屏幕的宽度
    private int screenWidth;
    private int first;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clue_list_layout);
        ButterKnife.bind(this);
        initTabLineWidth();
        init();

    }

    private void init() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClueManageActivity.this,NewClueInfoActivity.class);
                startActivity(intent);
            }
        });
        myClueInfo.setOnClickListener(new MyClickListener(0));
        claimInfo.setOnClickListener(new MyClickListener(1));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myClueInfoFragment = new MyClueInfoFragment();
        mFragmentList.add(myClueInfoFragment);
        claimFragment = new ClaimFragment();
        mFragmentList.add(claimFragment);
        fragmentAdapter = new FragmentAdapter(
                this.getSupportFragmentManager(), mFragmentList);
        idPaperVp.setAdapter(fragmentAdapter);
        idPaperVp.setCurrentItem(0);
        idPaperVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idTabLineIv
                        .getLayoutParams();
                Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1
                 * 0->1; 1->0
                 */

                if (currentIndex == 0 && position == 0) {// 0->1

                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
                            * (screenWidth / 2));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 2) + currentIndex
                            * (screenWidth / 2));

                }
                idTabLineIv.setLayoutParams(lp);

            }


            @Override
            public void onPageSelected(int position) {

                currentIndex = position;


            }

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyClickListener implements View.OnClickListener {

        private int index = 0;

        public MyClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            idPaperVp.setCurrentItem(index);

        }

    }

    /*
设置TAB下划线的宽度
 */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 2;
        idTabLineIv.setLayoutParams(lp);
        first = screenWidth / 2;

    }
}


