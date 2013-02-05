package com.example.myapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.example.myapp.fragments.FirstFragment;
import com.example.myapp.fragments.MenuFragment;
import com.example.myapp.fragments.MenuFragment.MenuFragmentComunucationInterface;
import com.example.myapp.fragments.SecondFragment;
import com.example.myapp.fragments.ViewPagerFragment;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SlidingFragmentActivity implements MenuFragmentComunucationInterface {

    private ArrayList<SlidingMenuItem> menuItems;
	private MenuFragment mFrag;
	private Fragment oldFrag;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		//set menu items
		menuItems = new ArrayList<SlidingMenuItem>();
		menuItems.add(new SlidingMenuItem("First",android.R.drawable.btn_star, new FirstFragment()));
		menuItems.add(new SlidingMenuItem("Second",android.R.drawable.btn_star, new SecondFragment()));
		menuItems.add(new SlidingMenuItem("View pager",android.R.drawable.btn_star,new ViewPagerFragment()));

		//set first fragment to family and friends
		changeFragment(menuItems.get(0).fragmentReference);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		mFrag = new MenuFragment();
		mFrag.setMenuItems(menuItems);
		t.replace(R.id.menu_frame, mFrag);
		t.commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setBehindOffsetRes(R.dimen.actionbar_home_width);
		setSlidingActionBarEnabled(false);

		final ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
        
    }
	
	//Change fragment in appropriate container
	private void changeFragment(Fragment fragment)
	{
		if (fragment != getSupportFragmentManager().findFragmentById(R.id.fragment_container))
		{
			//old impleemntation
			//change login fragment with forgot password fragment
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack
			//transaction.replace(R.id.fragment_container, fragment);
			//transaction.addToBackStack(null);
			//transaction.commit();
			
			//new implementation
			if (oldFrag != null)
				transaction.detach(oldFrag);
			transaction.replace(R.id.fragment_container, fragment);
			transaction.attach(fragment);
			transaction.addToBackStack(null);
			transaction.commit();
			oldFrag = fragment;
		}
	}
	
	public class SlidingMenuItem {
		public String tag;
		public int iconRes;
		public Fragment fragmentReference;

		public SlidingMenuItem(String tag, int iconRes, Fragment fragment) {
			this.tag = tag;
			this.iconRes = iconRes;
			this.fragmentReference = fragment;
		}
	}

	//callback from menu fragment
	@Override
	public void onMenuItemSelect(int position) {
		changeFragment(menuItems.get(position).fragmentReference);
		//close menu
		getSlidingMenu().toggle();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle(); //sliding menu toggle
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
}
