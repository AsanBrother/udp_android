package com.xidian.udpchat.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Toast;
import com.example.wechatsample.R;
import com.xidian.udpchat.pagerslidingtabstrip.PagerSlidingTabStrip;
import com.xidian.udpchat.ui.fragment.ChatFragment;
import com.xidian.udpchat.ui.fragment.SettingFragment;


/**
 * 主界面
 */
public class MainActivity extends ActionBarActivity {

	/**
	 * 聊天界面的Fragment
	 */
	private ChatFragment chatFragment;

	/**
	 * 发现界面的Fragment
	 */
	private SettingFragment foundFragment;

	/**
	 * PagerSlidingTabStrip的实例
	 */
	private PagerSlidingTabStrip tabs;

	/**
	 * 获取当前屏幕的密度
	 */
	private DisplayMetrics dm;
	 private Menu mMenu;
	 Toolbar toolbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//dm = getResources().getDisplayMetrics();
//		ViewPager pager = (ViewPager) findViewById(R.id.pager);
//		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//		tabs.setViewPager(pager);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
//		ActionBar actionBar = this.getActionBar();    
//	    actionBar.setDisplayHomeAsUpEnabled(true);   
		//setTabsValue();
		
	}

	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.parseColor("#B8B8B8"));
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 2, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 7, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#3f51b5"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#3f51b5"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "聊天", "设置" };

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
//				mMenu.findItem(R.id.edit).setVisible(true);
//				mMenu.findItem(R.id.connect).setVisible(false);
				if (chatFragment == null) {
					chatFragment = new ChatFragment();
					
				}
				return chatFragment;
			case 1:
				if (foundFragment == null) {
					foundFragment = new SettingFragment();
//					mMenu.findItem(R.id.connect).setVisible(true);
//					mMenu.findItem(R.id.edit).setVisible(false);
					
				}
				return foundFragment;
			default:
				return null;
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		mMenu = menu;
		return true;
	}
	
	
	

}