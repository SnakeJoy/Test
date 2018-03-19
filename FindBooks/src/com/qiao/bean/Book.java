package com.qiao.bean;

import java.io.Serializable;

public class Book implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4616419320901002384L;
	/**
	 * ID
	 */
	private String id;
	private int no;
	private String bookName;
	private float bookPrice;
	private String bookPriceUnit;
	private float lowestPrice;
	private String lowestPriceUnit;
	private String bookAuthor;
	private String bookTranslator;
	private String bookPictureURL;
	private String publishingHouse;
	private String publishingDate;
	private float thoughtGrade;
	private long thoughtCount;
	private String remarks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}

	public float getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(float lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookTranslator() {
		return bookTranslator;
	}

	public void setBookTranslator(String bookTranslator) {
		this.bookTranslator = bookTranslator;
	}

	public String getBookPictureURL() {
		return bookPictureURL;
	}

	public void setBookPictureURL(String bookPictureURL) {
		this.bookPictureURL = bookPictureURL;
	}

	public String getPublishingHouse() {
		return publishingHouse;
	}

	public void setPublishingHouse(String publishingHouse) {
		this.publishingHouse = publishingHouse;
	}

	public String getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(String publishingDate) {
		this.publishingDate = publishingDate;
	}

	public float getThoughtGrade() {
		return thoughtGrade;
	}

	public void setThoughtGrade(float thoughtGrade) {
		this.thoughtGrade = thoughtGrade;
	}

	public long getThoughtCount() {
		return thoughtCount;
	}

	public void setThoughtCount(long thoughtCount) {
		this.thoughtCount = thoughtCount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBookPriceUnit() {
		return bookPriceUnit;
	}

	public void setBookPriceUnit(String bookPriceUnit) {
		this.bookPriceUnit = bookPriceUnit;
	}

	public String getLowestPriceUnit() {
		return lowestPriceUnit;
	}

	public void setLowestPriceUnit(String lowestPriceUnit) {
		this.lowestPriceUnit = lowestPriceUnit;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", no=" + no + ", bookName=" + bookName + ", bookPrice=" + bookPrice
				+ ", bookPriceUnit=" + bookPriceUnit + ", lowestPrice=" + lowestPrice + ", lowestPriceUnit="
				+ lowestPriceUnit + ", bookAuthor=" + bookAuthor + ", bookTranslator=" + bookTranslator
				+ ", bookPictureURL=" + bookPictureURL + ", publishingHouse=" + publishingHouse + ", publishingDate="
				+ publishingDate + ", thoughtGrade=" + thoughtGrade + ", thoughtCount=" + thoughtCount + ", remarks="
				+ remarks + "]";
	}

}
