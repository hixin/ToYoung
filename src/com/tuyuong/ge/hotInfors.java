package com.tuyuong.ge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * hot列表的提问总属性类
 * */
@SuppressWarnings("serial")
public class hotInfors implements Cloneable,Serializable{
	private int id;//相关id
	private int inforclass;//提问类别
	private int quserid;//提问者的id
	private String qusername;//提问者昵称
	private String quserlogo;//提问者头像链接
	private String question;//提问问题
	private String qtime;//提问时间
	private int qsamenum;//同问次数
	private String title;//问题标题
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	private List<hotAnswers> answers=new ArrayList<hotInfors.hotAnswers>();//相关回答列表
	
	private hotAnswers hotanswer=new hotAnswers();//热门页面的最近的一条回答
	
	private boolean addshow=true;//add标志位	
	
	private int questiontype;//问题列表类型，为1的时候则是大图形式，为0时候则是文字展现。
	private String questionbigimage;//为1类型时候的大的图片url
	
	
	public hotInfors setId(int id){this.id=id;return this;}
    public int getId(){return this.id;}
    
    public hotInfors setInforclass(int inforclass){this.inforclass=inforclass;return this;}
    public int getInforclass(){return this.inforclass;}
    
    public hotInfors setQuserid(int quserid){this.quserid=quserid;return this;}
    public int getQuserid(){return this.quserid;}
    
    public hotInfors setQusername(String qusername){this.qusername=qusername;return this;}
    public String getQusername(){return this.qusername;}
    
    public hotInfors setquserlogo(String quserlogo){this.quserlogo=quserlogo;return this;}
    public String getQuserlogo(){return this.quserlogo;}
    
    public hotInfors setQuestion(String question){this.question=question;return this;}
    public String getQuestion(){return this.question;}
    
    public hotInfors setQtime(String qtime){this.qtime=qtime;return this;}
    public String getQtime(){return this.qtime;}
    
    public hotInfors setQsamenum(int qsamenum){this.qsamenum=qsamenum;return this;}
    public int getQsamenum(){return this.qsamenum;}
    
    public hotInfors setAnswers(List<hotAnswers> answers){this.answers=answers;return this;}
    public List<hotAnswers> getAnswers(){return this.answers;}
    
    public hotInfors setAddshow(boolean addshow){this.addshow=addshow;return this;}
    public boolean getAddshow(){return this.addshow;}
    
    public hotInfors setQuestiontype(int questiontype){this.questiontype =questiontype;return this;}
    public int getQuestiontype(){return this.questiontype;}
   
    public hotInfors setQuestionbigimage(String questionbigimage){this.questionbigimage=questionbigimage;return this;}
    public String getQuestionbigimage(){return this.questionbigimage;}
    
    public hotInfors setHotanswer(hotAnswers hotanswer){this.hotanswer=hotanswer;return this;}
    public hotAnswers getHotanswer(){return this.hotanswer;}
    
    /**
     * 回答类
     * */
    class hotAnswers implements Serializable{
    	private int id;//相关id
    	private int auserid;//回答者id
    	private String ausername;//回答者昵称
    	private String auserloge;//回答者logeurl
    	private String answser;//回答内容
    	private String atime;//回答时间
    	private int agoodnum;//被赞次数
    	
    	private boolean saveshow=true;//保存标志位
    	private boolean goodshow=true;//点赞标志位
    	
    	public hotAnswers setId(int id){this.id=id;return this;}
        public int getId(){return this.id;}
        
        public hotAnswers setAuserid(int auserid){this.auserid=auserid;return this;}
        public int getQuserid(){return this.auserid;}
        
        public hotAnswers setAusername(String ausername){this.ausername=ausername;return this;}
        public String getAusername(){return this.ausername;}
        
        public hotAnswers setAuserloge(String auserloge){this.auserloge=auserloge;return this;}
        public String getAuserloge(){return this.auserloge;}
        
        public hotAnswers setAnswser(String answser){this.answser=answser;return this;}
        public String getAnswser(){return this.answser;}
        
        public hotAnswers setAtime(String atime){this.atime=atime;return this;}
        public String getAtime(){return this.atime;}
        
        public hotAnswers setAgoodnum(int agoodnum){this.agoodnum=agoodnum;return this;}
        public int getAgoodnum(){return this.agoodnum;}
        
        public hotAnswers setGoodshow(boolean goodshow){this.goodshow=goodshow;return this;}
        public boolean getGoodshow(){return this.goodshow;}
        
        public hotAnswers setSaveshow(boolean saveshow){this.saveshow=saveshow;return this;}
        public boolean getSaveshow(){return this.saveshow;}
    	
    }
    
    @Override
    public hotInfors clone(){
    	hotInfors hotinfors =null;
    	try{
    		hotinfors=(hotInfors)super.clone();
    	}catch(CloneNotSupportedException e){
    		e.printStackTrace();
    	}
    	return hotinfors;
    }

}


