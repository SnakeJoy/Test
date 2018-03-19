package com.qiao.getBooks;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.qiao.bean.Book;
import com.qiao.config.SysConfig;


public class WriteToExcel {
	public static void main(String[] args) {
		
		List<Book> dataList = new ArrayList<Book>();
		Book b1 = new Book();
		b1.setNo(1);
		b1.setBookName("Java编程思想");
		b1.setThoughtGrade(9.0f);
		b1.setThoughtCount(1020);
		b1.setBookAuthor("某某某");
		b1.setPublishingHouse("出版社");
		b1.setPublishingDate("2010-8");
		b1.setBookPrice(65.00f);
		b1.setBookPriceUnit("元");
		dataList.add(b1);
		Book b2 = new Book();
		b2.setNo(2);
		b2.setBookName("Java编程思想");
		b2.setThoughtGrade(9.0f);
		b2.setThoughtCount(1020);
		b2.setBookAuthor("某某某");
		b2.setPublishingHouse("出版社");
		b2.setPublishingDate("2010-8");
		b2.setBookPrice(65.00f);
		b2.setBookPriceUnit("元");
		dataList.add(b2);
		writeExcel(dataList, SysConfig.EXCEL_FILE_PATH);
	}

	public static void writeExcel(List<Book> dataList, String filePath){
		if(dataList.size() == 0){
			System.out.println("数据为空");
			return;
		}
		String[] headData = {"序号","书名","评分","评价人数","作者","出版社","出版时间","价格"};
		OutputStream out = null;
		try {
			File xlsxFile = new File(filePath);
			Workbook workBook = getWorkbok(xlsxFile);
			Sheet sheet = workBook.createSheet("BookDetails");
			createHeadRow(headData, sheet);			
			createBodyRow(dataList, sheet);
			out =  new FileOutputStream(xlsxFile);
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(out != null){
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("数据导出成功");
	}

//	 创建表格主体
	
	private static void createBodyRow(List<Book> dataList, Sheet sheet) {
		for (int j = 0; j < (dataList.size() > SysConfig.BOOK_COUNT ? SysConfig.BOOK_COUNT : dataList.size()); j++) {
			Row row = sheet.createRow(j + 1);
			Book book = dataList.get(j);
			row.createCell(0).setCellValue(j + 1);
			row.createCell(1).setCellValue(book.getBookName());
			row.createCell(2).setCellValue(book.getThoughtGrade());
			row.createCell(3).setCellValue(book.getThoughtCount());
			row.createCell(4).setCellValue(book.getBookAuthor());
			row.createCell(5).setCellValue(book.getPublishingHouse());
			row.createCell(6).setCellValue(book.getPublishingDate());
			row.createCell(7).setCellValue(book.getBookPrice()+book.getBookPriceUnit());
		}
	}

	
//	创建表格头
	private static void createHeadRow(String[] headData, Sheet sheet) {
		Row headRow = sheet.createRow(0);
		for(int i=0;i<headData.length;i++){
			Cell cell = headRow.createCell(i);
			cell.setCellValue(headData[i]);				
		}
	}


	private static Workbook getWorkbok(File file) throws IOException{
		Workbook wb = null;
		if(file.getName().endsWith("xls")){
			wb = new HSSFWorkbook();
		}else{
			System.out.println("请指定xls格式的文件");
		}
		return wb;
	}
}