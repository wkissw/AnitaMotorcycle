package com.anita.anitamotorcycle.activities2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.adapters.Main2FragmentAdapter;
import com.anita.anitamotorcycle.adapters.MainFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class Main2Activity extends AppCompatActivity {

    private long firstTime = 0;   //第一次点击返回键的时间
    private ViewPager mView_pager;
    private TabLayout mTab_layout;
    private Main2FragmentAdapter mAdapter;

    //    菜单标题
    private final int[] TAB_TITLES = new int[]{R.string.menu_orders,  R.string.menu_me};
    //    菜单图标
    private final int[] TAB_IMGS = new int[]{
            R.drawable.icon_orders_select,
            R.drawable.icon_me_select};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
        initListener();
        setTabs(mTab_layout, getLayoutInflater(), TAB_TITLES, TAB_IMGS);
    }

    private void initView() {
        mView_pager = findViewById(R.id.view_pager);
        mTab_layout = findViewById(R.id.tab_layout);

        mAdapter = new Main2FragmentAdapter(getSupportFragmentManager());
        mView_pager.setAdapter(mAdapter);
    }

    private void initListener() {

        // 关联切换
        mView_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab_layout));
        mTab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 取消平滑切换
                mView_pager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 设置底部菜单栏显示效果
     *
     * @param tabLayout
     * @param inflater
     * @param tabTitlees
     * @param tabImgs
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.item_menu, null);
            // 使用自定义视图，便于修改，也可使用自带的视图
            tab.setCustomView(view);

            TextView tvTitle = view.findViewById(R.id.text_tab);
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = view.findViewById(R.id.img_icon);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);
        }
    }

    //  点击两次返回键退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - firstTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
