package com.example.myapp.fragments;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapp.MainActivity.SlidingMenuItem;
import com.example.myapp.R;

public class MenuFragment extends ListFragment {
    public interface MenuFragmentComunucationInterface {
        public void onMenuItemSelect(int position);
    }

	private static final String TAG = "MenuFragment";
	private SampleAdapter adapter;
	private List<SlidingMenuItem> menuItems;
	private MenuFragmentComunucationInterface activityCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		//return listview that will be menu
		Log.d(TAG,"onCreateView");
		View v =  inflater.inflate(R.layout.list, null);
		setRetainInstance(true);
		return v;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d(TAG,"Menu list item click"+ position);
		activityCallback.onMenuItemSelect(position); //notifiy activity about menu selection
	}

	

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG,"onActivityCreated");
		
		
		adapter = new SampleAdapter(getActivity());
		
		//set  up menu items
		for (SlidingMenuItem item : menuItems)
		{
			adapter.add(item);
			Log.d(TAG,"menu item add "+item.tag);
			
		}
		setListAdapter(adapter);
	}



	public void setMenuItems(List<SlidingMenuItem> menuItems) {
		this.menuItems = menuItems;
	}




	public class SampleAdapter extends ArrayAdapter<SlidingMenuItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.sliding_menu_row_item, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            activityCallback = (MenuFragmentComunucationInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MenuFragmentComunucationInterface");
        }
	}

}
