package com.example.myapp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.myapp.R;
import com.viewpagerindicator.TabPageIndicator;

public class ViewPagerFragment extends SherlockFragment {
	private static final String TAG = "CubeFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_view_pager, container, false);
		
        CubeFragmentAdapter adapter = new CubeFragmentAdapter(getChildFragmentManager());
        
        
        //put fragment in list
        List<ViewPagerItem> fragments = new ArrayList<ViewPagerItem>();
        fragments.add(new ViewPagerItem("Wish", new FragmentViewPagerTabOne()));
        fragments.add(new ViewPagerItem("Goal", new FragmentViewPagerTwo()));
        fragments.add(new ViewPagerItem("Reward", new FragmentViewPagerThree()));
        
        adapter.listOfFragments= fragments;
        
        ViewPager pager = (ViewPager)v.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        TabPageIndicator indicator = (TabPageIndicator)v.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
		
        Log.d(TAG,"onCreateView in Cube fragment end");
		return v;
	}
	
	
	private class ViewPagerItem
	{
		public String tag;
		public Fragment fragment;
		public ViewPagerItem(String tag, Fragment fragment)
		{
			this.tag = tag;
			this.fragment = fragment;
		}
	}
	
    class CubeFragmentAdapter extends FragmentStatePagerAdapter {
    	private List<ViewPagerItem> listOfFragments;
        public CubeFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int position) {
            return listOfFragments.get(position).fragment;
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            return listOfFragments.get(position).tag;
        }
        
        @Override
        public int getCount() {
          return listOfFragments.size();
        }

    }

}
