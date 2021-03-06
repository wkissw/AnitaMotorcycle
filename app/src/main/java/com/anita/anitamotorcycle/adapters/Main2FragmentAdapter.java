package com.anita.anitamotorcycle.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.anita.anitamotorcycle.fragments.Me2Fragment;
import com.anita.anitamotorcycle.fragments.MessageFragment;
import com.anita.anitamotorcycle.fragments.Home2Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 主界面底部菜单适配器
 * 页面切换，使用Fragment作为子页面
 */
public class Main2FragmentAdapter extends FragmentPagerAdapter {

    private static Map<Integer, Fragment> sCache = new HashMap<>();

    public Main2FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    //    返回具体位置的viewPager切换到i位置时对应的fragment
    public Fragment getItem(int i) {
        Fragment fragment = null;
//        Fragment fragment = sCache.get(i);
//        if (fragment != null) {
//            return fragment;
//        }
        switch (i) {
            case 0:
                fragment = new Home2Fragment();
                break;
            case 1:
                fragment = new Me2Fragment();
                break;
            default:
                break;
        }
//        sCache.put(i, fragment);
        return fragment;
    }

    //    返回视图的总数量
    public int getCount() {
        return 2;
    }

}

