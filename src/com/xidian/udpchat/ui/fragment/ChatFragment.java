package com.xidian.udpchat.ui.fragment;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.wechatsample.R;
import com.xidian.udpchat.adapter.ChatMsgViewAdapter;
import com.xidian.udpchat.model.ChatMsgEntity;

import com.xidian.udpchat.utils.ConnectUtils;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 聊天Fragment的界面
 * 
 */
public class ChatFragment extends Fragment implements OnClickListener{
	private Button mBtnSend;// 发送btn
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
	private DatagramSocket daSocket;
	private DatagramPacket daPacket;
	private Thread mThread ;
	private UdpClient mUdpClient;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) { 
		View contentView = inflater.inflate(R.layout.main, null);
		initView(contentView);
		return contentView;
	}
	public void initView(View contentView) {
		
		mListView = (ListView) contentView.findViewById(R.id.listview);
		mBtnSend = (Button) contentView.findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) contentView.findViewById(R.id.et_sendmessage);
		mAdapter = new ChatMsgViewAdapter(getActivity(), mDataArrays);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(mAdapter.getCount() - 1);
		
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_send:// 发送按钮点击事件
			send();
			break;
		}
	 }
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
        switch (item.getItemId()) {
            case R.id.edit:
            	connect();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	private void connect() {
		mUdpClient = new UdpClient();
		mThread = new Thread(mUdpClient);
		mThread.start();
	}
	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName("World");
			entity.setDate(getDate());
			entity.setMessage(contString);
			entity.setMsgType(false);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

			mEditTextContent.setText("");// 清空编辑框数据
			new SendAsyncTack().execute(contString);
			mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
		}
	}
	private void recieve(ChatMsgEntity entity) {
		mDataArrays.add(entity);
		mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

		mEditTextContent.setText("");// 清空编辑框数据

		mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
	}
	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}		
	Handler mHandle  = new Handler() {
		 @Override  
		    public void handleMessage(Message msg) {  
		        super.handleMessage(msg);  
		        
		        ChatMsgEntity entity = (ChatMsgEntity) msg.obj;
			     recieve(entity);	         
		     }  
	};
	public class SendAsyncTack extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			DatagramSocket ds = null;
			try {
				ds = new DatagramSocket(ConnectUtils.localPort);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//2，确定数据，并封装成数据包。DatagramPacket(byte[] buf, int length, InetAddress address, int port) 
			byte[] buf = arg0[0].getBytes();
			DatagramPacket dp;
			try {
				dp = new DatagramPacket(buf,buf.length,InetAddress.getByName(ConnectUtils.remoteIp),ConnectUtils.remotePort);
				//3，通过socket服务，将已有的数据包发送出去。通过send方法。
				ds.send(dp);
				//4，关闭资源。
				ds.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
				return null;
			}		
		}
		public class UdpClient implements Runnable {
			byte[] buf = new byte[1024];	
			public UdpClient()  {
				try {
					daSocket = new DatagramSocket(ConnectUtils.remotePort);
					daPacket = new DatagramPacket(buf,buf.length);
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
			@Override
			public void run() {
				while(true) {
					try {
						Message mMessage = mHandle.obtainMessage();
						
						
						daSocket.receive(daPacket);
						ConnectUtils.setRemoteIp( daPacket.getAddress().getHostAddress());
						String data= new String(daPacket.getData(),0,daPacket.getLength());
						ConnectUtils.setRemoteport( daPacket.getPort());
						ChatMsgEntity chatMsgEntity =new ChatMsgEntity("hello",ConnectUtils.getRemoteIp()+":"+ConnectUtils.getLocalPort()+"\n"+getDate(),data,true);
						mMessage.obj=chatMsgEntity;
						mMessage.sendToTarget();						

					} catch (IOException e) {
						// TODO Auto-generated catch block
						Log.d("Tag",daSocket+"" );
						e.printStackTrace();
					}//阻塞式方法。
				}		
			}	
		}
}
