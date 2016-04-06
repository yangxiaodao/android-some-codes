package com.xiaodao.factory;

import com.xiaodao.base.BaseFragment;
import com.xiaodao.fragment.Tab_1_Fragment;
import com.xiaodao.fragment.Tab_2_Fragment;
import com.xiaodao.fragment.Tab_3_Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by android on 2016/4/6.
 */
public class FragmentFactory {

    private static final int TAB_1 = 0;
    private static final int TAB_2 = 1;
    private static final int TAB_3 = 2;
    private static Map<Integer,BaseFragment> fragments = new HashMap<>();

    public static BaseFragment createFragment(int position){
        BaseFragment fragment = fragments.get(position);

        if (fragment != null)return fragment;

        switch (position){
            case TAB_1:
                fragment = new Tab_1_Fragment();
                break;
            case TAB_2:
                fragment = new Tab_2_Fragment();
                break;
            case TAB_3:
                fragment = new Tab_3_Fragment();
                break;
        }
        fragments.put(position,fragment);
        return fragment;
    }

    public static void removeFragments(){
        fragments.clear();
    }
}
