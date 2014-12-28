package com.xidian.udpchat.ui.fragment;

import com.example.wechatsample.R;
import com.xidian.udpchat.utils.ConnectUtils;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;


public class SettingFragment extends Fragment {
	static EditText localIp;
	static EditText localPort;
	static EditText remoteIp;
	static EditText remotePort;
	ActionBar actionBar;
	@Override
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView  = inflater.inflate(R.layout.setting, null);
		initView(contentView);
		return contentView;
	}
    public void initView(View contentView) {
    	localIp = (EditText)contentView.findViewById(R.id.localip);
    	localPort = (EditText)contentView.findViewById(R.id.localport);
    	remoteIp = (EditText)contentView.findViewById(R.id.remoteip);
    	remotePort = (EditText)contentView.findViewById(R.id.remoteport);
    	actionBar = getActivity().getActionBar();
    	
    	
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
        switch (item.getItemId()) {
            case R.id.edit:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    public static void save(){
    	ConnectUtils.setLocalIp(localIp.getText().toString()==null?"0":localIp.getText().toString());
    	ConnectUtils.setLocalPort(Integer.parseInt(localPort.getText().toString()==null? "0":localPort.getText().toString()));
    	ConnectUtils.setRemoteIp(remoteIp.getText().toString()==null?"0":remoteIp.getText().toString());
    	ConnectUtils.setRemoteport(Integer.parseInt(remotePort.getText().toString()==null? "0":remotePort.getText().toString()));
    }
}
