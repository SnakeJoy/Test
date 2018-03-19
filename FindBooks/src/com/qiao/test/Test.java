package com.qiao.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.qiao.bean.Book;
import com.qiao.config.SysConfig;
import com.qiao.getBooks.MainJava;
import com.qiao.getBooks.RunJava;
import com.qiao.getBooks.WriteToExcel;

public class Test {

	public static void main(String[] args) {
		String[] keywords = SysConfig.SEARCH_KEYWORD;
		//页面（正式环境使用）
		int pageNumber = 0;
		try {
			pageNumber = (new MainJava()).getTotalPageNumber(SysConfig.BASE_URL + keywords[0] + "?type=S&start=0")/2;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//页面（测试环境使用）
		List<Thread> workers = new ArrayList<Thread>();
		
		for(String key : keywords){			
			String url = SysConfig.BASE_URL + key + "?type=S&start=";
			for(int i=0;i<pageNumber;i++){		
				System.out.println(url+i*20);
				RunJava rs= new RunJava(url+i*20);
				try {
					Thread.sleep((long) (Math.random()*10000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rs.start();
				workers.add(rs);
			}
		}
		for(int i = 0; i < workers.size(); i++) {
            try {
				workers.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }

		Collections.sort(MainJava.result,new Comparator<Book>() {

			@Override
			public int compare(Book o1, Book o2) {
				String s1 = String.valueOf(o1.getThoughtGrade());
				String s2 = String.valueOf(o2.getThoughtGrade());
				return s2.compareTo(s1);
			}
		});
		WriteToExcel.writeExcel(MainJava.result, SysConfig.EXCEL_FILE_PATH);
	}


}
