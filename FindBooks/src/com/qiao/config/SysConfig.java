package com.qiao.config;

public class SysConfig {
	
	 /*参数名称：书名是否包含副标题<br/>
	 参数值：[true:包含|false:不包含]<br/>*/
	 
	public static final boolean SWITCH_USE_SUBHEAD = true;
	
//  参数名称：评价数量<br/>
	 
	public static final long THOUGHT_COUNT = 1000;
	
//	参数名称：需要的数量<br/>
	
	public static final int BOOK_COUNT = 100;
	
//	参数名称：网站基础地址<br/>
	
	public static final String BASE_URL = "https://book.douban.com/tag/";
	
//	参数名称：搜索关键词<br/>
	
	public static final String[] SEARCH_KEYWORD = { "互联网", "编程", "算法" };
	
//	参数名称：Excel表格导出路径<br/>
	
	public static final String EXCEL_FILE_PATH = "D:\\book_Details.xls";


}
