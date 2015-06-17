package com.tuyoung.wu;

import com.example.toyoung.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.toyoung.R;
public class Message extends Activity{
	private ImageView back;
	private ListView list_aboutme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		list_aboutme = (ListView) findViewById(R.id.listview);
		list_aboutme.setAdapter(new MessageListAdapter(Message.this));
		setCustomActionBar();
	}
	
	private void setCustomActionBar() {  
	    ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);  
	    View mActionBarView = LayoutInflater.from(this).inflate(R.layout.customtitle, null);  
	    ActionBar actionBar = getActionBar();  
	    actionBar.setCustomView(mActionBarView, lp);  
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  
	    actionBar.setDisplayShowCustomEnabled(true);  
	    actionBar.setDisplayShowHomeEnabled(false);  
	    actionBar.setDisplayShowTitleEnabled(false);
	    
	    back = (ImageView) mActionBarView.findViewById(R.id.home);
	    back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
	    
	} 
	class MessageListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context mContext;

		public MessageListAdapter(Context context)
		{
			mInflater = LayoutInflater.from(context);

		}

		@Override
		public int getCount()
		{
			return 6;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;  
	        if (convertView == null)  
	        {  
	            convertView = mInflater.inflate(R.layout.list_cell_message, parent, false);  
	            viewHolder = new ViewHolder();  
	            viewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.tv_name);
	            viewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.tv_action);
	            viewHolder.userphoto = (CircleImageView) convertView.findViewById(R.id.iv_avatar1);
	            convertView.setTag(viewHolder);  
	        } else  
	        {  
	            viewHolder = (ViewHolder) convertView.getTag();  
	        }  
	        return convertView;  
		}

		

	}
	
	private final class ViewHolder  
	{ 
		CircleImageView userphoto;
		TextView mTextView1;
		TextView mTextView2;

	}
	
	

}
