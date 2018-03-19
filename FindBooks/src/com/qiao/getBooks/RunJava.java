package com.qiao.getBooks;

import java.net.MalformedURLException;

public class RunJava extends Thread{
	
	String url;
	public RunJava(String url) {
		this.url = url;
	}
	
	@Override
	public void run() {

		try {
			(new MainJava()).getCurrentPageData(this.url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		super.run();
	}

}
