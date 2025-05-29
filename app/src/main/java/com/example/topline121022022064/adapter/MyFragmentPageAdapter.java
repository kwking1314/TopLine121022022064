package com.example.topline121022022064.adapter;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;
public class MyFragmentPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
    @Override
    public int getCount() {
        return list.size();
    }
}
