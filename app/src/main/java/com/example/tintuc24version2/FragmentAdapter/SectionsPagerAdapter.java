package com.example.tintuc24version2.FragmentAdapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tintuc24version2.NewsFragment;
import com.example.tintuc24version2.R;
import com.example.tintuc24version2.WeatherFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.title_home, R.string.title_dashboard};
    private final Context mContext;
    private NewsFragment mNewsFragment;
    private WeatherFragment mWeatherFragment;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        mNewsFragment = new NewsFragment();
        mWeatherFragment = new WeatherFragment();
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0 ){
            return  mNewsFragment;
        } else if (position == 1 ){
            return  mWeatherFragment;
        } else {
            //ToDO Something
        }
        return  null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
