package com.tuyoung.wu;

import java.util.ArrayList;
import java.util.List;

import com.toyoung.wu.bean.Entity;
import com.toyoung.wu.bean.Question;

public class GenData {
	static int j =1;
	public List<Question> gendata() {
		List<Question> questions = new ArrayList<Question>();
		for(int i=0; i<20 ; i++) {
			Question q = new Question(j++,i,"在美国怎么打出租车？美国的出租车和国内是一样的模式么？");
			questions.add(q);
		}
		return questions;
	}
}

