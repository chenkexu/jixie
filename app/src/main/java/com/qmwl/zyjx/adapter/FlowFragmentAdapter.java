package com.qmwl.zyjx.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class FlowFragmentAdapter extends FragmentPagerAdapter {
	List<Fragment> mList;

	public FlowFragmentAdapter(FragmentManager fm, List<Fragment> l) {
		super(fm);
		this.mList = l;
	}

	@Override
	public Fragment getItem(int arg0) {

		return mList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
}
