package com.tuyuong.ge;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toyoung.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.toyoung.liu.MainHotActivity;
import com.toyuong.util.ShowToast;

public class hotanswerslistAdapter extends ArrayAdapter<hotInfors.hotAnswers>{
	 public hotanswerslistAdapter(Context context, List<hotInfors.hotAnswers> list, ListView hotlistview) {
		super(context, 0, list);
		// TODO Auto-generated constructor stub
	}


	private  ViewHolder viewHolder = null;
	 
	
	  @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
		  if (convertView == null) {
	            //初始化布局
	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hot_item_answers_item, null);
	            viewHolder = new ViewHolder();
	         	          
	            viewHolder.ivAuserlogo = (ImageView) convertView.findViewById(R.id.imageLogo);	       
	            
	            viewHolder.tvAname = (TextView) convertView.findViewById(R.id.tvName);	         
	            viewHolder.tvAnswer = (TextView) convertView.findViewById(R.id.tv_Answer);	          
	            viewHolder.tvAgoodumb = (TextView) convertView.findViewById(R.id.tvgoodNum);
	            

	            convertView.setTag(viewHolder);

	        } else viewHolder = (ViewHolder) convertView.getTag();
		  
	        //绑定位置信息Tag
	        viewHolder.ivAuserlogo.setTag(position);
	        viewHolder.tvAname.setTag(position);
	        viewHolder.tvAnswer.setTag(position);
	        viewHolder.tvAgoodumb.setTag(position);       

	        viewHolder.tvAname.setText(getItem(position).getAusername());//显示姓名                    	        
	        viewHolder.tvAnswer.setText(getItem(position).getAnswser());//显示回答                
	        viewHolder.tvAgoodumb.setText(getItem(position).getAgoodnum()+"");//显示回答被赞的数量

//	        final int width =  com.toyuong.util.DisplayManager.dip2px
//	        		(getContext(), getContext().getResources().getDimension(R.dimen.small_logo_width));
//	        ImageSize imageSize = new ImageSize(width, width);// 设置头像图片大小
//	        ImageLoader.getInstance().loadImage(getItem(position).getLogoUrl(), imageSize, imageLodeoptions, new SimpleImageLoadingListener() {
//	            @Override
//	            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//	                super.onLoadingComplete(imageUri, view, loadedImage);
//	                viewHolder.imageLogo.setImageBitmap(loadedImage);
//	            }
//	        });
	        ImageLoader.getInstance().displayImage(getItem(position).getAuserloge(), viewHolder.ivAuserlogo,MainHotActivity.imageLodeoptions);
		  
	        
	        viewHolder.ivAuserlogo.setOnClickListener(new OnClickListener() {		
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					int position =(Integer)view.getTag();
					 new ShowToast().shortshow(getContext(), position+"");
				}
			});
	       
	        
		  return convertView;		  		 
	  }
	  
	  /**
	     * 用于缓存的类
	     * */
	        public final class ViewHolder {
	          	            
	            private ImageView ivAuserlogo;//头像
	            private TextView tvAname;//姓名
	            private TextView tvAnswer;//回答
	            private TextView tvAgoodumb;//回答赞的数目

	        }
}
