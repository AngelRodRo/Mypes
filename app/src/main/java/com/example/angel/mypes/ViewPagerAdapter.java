package com.example.angel.mypes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by angel on 16/02/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT=6;

    public ViewPagerAdapter (FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                FragmentTab1 fragmenttab1 = new FragmentTab1();
                return fragmenttab1;
            case 1:
                FragmentTab2 fragmenttab2 = new FragmentTab2();
                return fragmenttab2;
            case 2:
                FragmentTab3 fragmenttab3 = new FragmentTab3();
                return fragmenttab3;
            case 3:
                FragmentTab4 fragmenttab4 = new FragmentTab4();
                return fragmenttab4;
            case 4:
                FragmentTab5 fragmenttab5 = new FragmentTab5();
                return fragmenttab5;
            case 5:
                FragmentTab6 fragmenttab6 = new FragmentTab6();
                return fragmenttab6;

        }

        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
