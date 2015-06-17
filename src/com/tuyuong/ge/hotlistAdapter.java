package com.tuyuong.ge;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toyoung.R;
import com.toyuong.util.QuestionTypeSelector;

public class hotlistAdapter extends ArrayAdapter<hotInfors> {

	private ViewHolder viewHolder = null;

	public hotlistAdapter(Context context, List<hotInfors> list,
			ListView hotlistview) {
		super(context, 0, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			// 初始化布局
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.main_hot_item, null);
			viewHolder = new ViewHolder();

			viewHolder.tvQustion = (TextView) convertView
					.findViewById(R.id.tv_question);
			viewHolder.tvclass = (TextView) convertView
					.findViewById(R.id.tv_class);
			viewHolder.tvAnswer = (TextView) convertView
					.findViewById(R.id.tv_answer);

			convertView.setTag(viewHolder);

		} else	viewHolder = (ViewHolder) convertView.getTag();
		
		
		viewHolder.tvAnswer.setTag(position);	

		viewHolder.tvclass.setText(QuestionTypeSelector.getType(getItem(position).getQuestiontype()));
		
		viewHolder.tvQustion.setText(getItem(position).getQuestion());// 显示问题
		
		if(getItem(position).getAnswers()!=null){
		String lastanswer = getItem(position).getHotanswer().getAnswser();
		if(lastanswer!=null)
		viewHolder.tvAnswer.setText(lastanswer);// 显示最近一个回答,是这样吗？不知道规则是什么需要探讨
		}	
		viewHolder.tvAnswer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				int position = (Integer)view.getTag();
				Intent intent = new Intent(getContext(),HotAnswerDetailActivity.class);
				intent.setAction("MainHotActivity");
				
				Bundle mBundle = new Bundle();    
		        mBundle.putSerializable("hotInfors", getItem(position));						
		        intent.putExtras(mBundle);
				getContext().startActivity(intent);
			}
		});

		return convertView;
	}

	/**
	 * 用于缓存的类，缓存一般的type0
	 * */
	public final class ViewHolder {

		private TextView tvQustion;// 大的提问
		private TextView tvclass;// 类别文字
		private TextView tvAnswer;// 回答

	}


}
