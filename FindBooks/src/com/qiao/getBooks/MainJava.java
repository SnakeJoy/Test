package com.qiao.getBooks;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.qiao.bean.Book;
import com.qiao.config.SysConfig;

public class MainJava {
public static List<Book> result = Collections.synchronizedList(new ArrayList<Book>());
	
	public static void main(String[] args) throws IOException, InterruptedException {
		//测试
		(new MainJava()).getCurrentPageData("https://book.douban.com/tag/%E7%AE%97%E6%B3%95?type=S");
		Thread.sleep(5000);
		Collections.sort(MainJava.result,new Comparator<Book>() {

			@Override
			public int compare(Book o1, Book o2) {
				String s1 = String.valueOf(o1.getThoughtGrade());
				String s2 = String.valueOf(o2.getThoughtGrade());
				return s2.compareTo(s1);
			}
		});
		WriteToExcel.writeExcel(result, SysConfig.EXCEL_FILE_PATH);
	}

	public void getCurrentPageData(String url) throws MalformedURLException {
		List<Book> pageList = outputDatas(getResponseFromURL(new URL(url)));
		for(Book b : pageList){
			System.out.println(b.toString());
		}
		result.addAll(pageList);
	}
	
	public int getTotalPageNumber(String url) throws IOException {
		String responseStr = getResponseFromURL(new URL(url));
		Document document = null;
		try {
			document = Jsoup.parse(new String(responseStr.getBytes(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Element divA = document.select("div.paginator > a").last();
		return Integer.valueOf(divA.text());

	}

	private String getResponseFromURL(URL url) {
		try {
			return getResultFromInputStream(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getResultFromInputStream(InputStream in) {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[1024*100];
		int len = 0;
		try {
			while ((len = in.read(b)) != -1) {
				out.append(new String(b, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out.toString();
	}

	private List<Book> outputDatas(String responseStr) {
		if(null == responseStr || "" == responseStr || responseStr.length() < 1024){
			Thread.interrupted();
		}
		List<Book> resultList = new ArrayList<Book>();
		Document document = null;
		try {
			document = Jsoup.parse(new String(responseStr.getBytes(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Elements ul = document.select("ul.subject-list");
		Iterator<Element> ulIter = ul.iterator();
		while (ulIter.hasNext()) {
			List<Book> bookList = new ArrayList<Book>();
			Book book;
			Element element = ulIter.next();
			Elements eleLi = element.select("li.subject-item");
			Iterator<Element> liIter = eleLi.iterator();
			while (liIter.hasNext()) {
				Element liElement = liIter.next();
				if(getThoughtCountFromLi(liElement) < SysConfig.THOUGHT_COUNT){
					continue;
				}
				book = new Book();
				book.setId(UUID.randomUUID().toString());
				book.setBookName(getBookNameFromLi(liElement));
				book.setBookPrice(getBookPriceFromLi(liElement));
				book.setBookPriceUnit(getBookPriceUnitFromLi(liElement));
				book.setPublishingDate(getPublishingDateFromLi(liElement));
				book.setPublishingHouse(getPublishingHouseFromLi(liElement));
				book.setBookAuthor(getBookAuthorFromLi(liElement));
				book.setThoughtGrade(getThoughtGradeFromLi(liElement));
				book.setThoughtCount(getThoughtCountFromLi(liElement));
				bookList.add(book);
			}
			resultList.addAll(bookList);
		}
		return resultList;
	}
	
	private String getBookAuthorFromLi(Element liElement) {
		String[] datas = getPubStringArr(liElement);
		return datas.length == 4 ? datas[datas.length-4].trim() : datas[datas.length-5].trim();
	}
	
	private String getPublishingHouseFromLi(Element liElement) {
		String[] datas = getPubStringArr(liElement);
		return datas[datas.length-3].trim();
	}
	
	private String getPublishingDateFromLi(Element liElement) {
		String[] datas = getPubStringArr(liElement);
		try{
			return datas[datas.length-2].trim();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "";
	}
	
	private String getBookPriceUnitFromLi(Element liElement) {
		String[] datas = getPubStringArr(liElement);
		try{			
			return datas[datas.length-1].replaceAll("[0-9.]", "");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "";
	}

	private Float getBookPriceFromLi(Element liElement) {
		String[] datas = getPubStringArr(liElement);
		try{
			return Float.valueOf(datas[datas.length-1].replaceAll("[a-zA-Z$\\u4e00-\\u9fa5]", "").trim());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return 0.00F;
	}
	
	private Float getThoughtGradeFromLi(Element liElement) {
		Elements eleDivDivDivSpan = liElement.select("div.info").select("div.star").select("span.rating_nums");
		try{
			return Float.valueOf(eleDivDivDivSpan != null && eleDivDivDivSpan.hasText() ? eleDivDivDivSpan.text().trim() : "");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return 0.00F;
	}
	
	private Long getThoughtCountFromLi(Element liElement) {
		Elements eleDivDivDivSpan = liElement.select("div.info").select("div.star").select("span.pl");
		return Long.valueOf(eleDivDivDivSpan.text().replaceAll("[()\\u4e00-\\u9fa5]", ""));
	}
	
	private String getBookNameFromLi(Element liElement) {
		Elements eleDivH2A = liElement.select("div.info").select("h2").select("a[href]");
		return (SysConfig.SWITCH_USE_SUBHEAD && eleDivH2A.select("span") != null)
				? eleDivH2A.attr("title") + eleDivH2A.select("span").text() : eleDivH2A.attr("title");
	}

	private String[] getPubStringArr(Element liElement) {
		Elements eleDivDiv = liElement.select("div.info").select("div.pub");
		String[] datas = eleDivDiv.text().replaceAll(" ", "").split("/");
		if(datas.length <= 3){
			datas = new String[]{"","","",""};
		}
		return datas;
	}

}
