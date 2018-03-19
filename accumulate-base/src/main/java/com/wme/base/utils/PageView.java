package com.wme.base.utils;

import java.util.List;

/**
 * 分页
 * @author wangmingne
 *
 */
public class PageView {
	/** 代表最后一页 */
	public static final int LAST_PAGE = Integer.MAX_VALUE;
	/** 代表空的PageView */
	public static final PageView EMPTY_PAGE_VIEW;
	static {
		EMPTY_PAGE_VIEW = new PageView();
		EMPTY_PAGE_VIEW.setCurrentPage(1);
		EMPTY_PAGE_VIEW.setPageSize(10);
		EMPTY_PAGE_VIEW.setTotalPage(1);
		EMPTY_PAGE_VIEW.setStartPageIndex(1);
		EMPTY_PAGE_VIEW.setEndPageIndex(1);
	}

	private int currentPage; // 当前页码 +
	private int totalPage; // 总页码
	private int pageSize; // 每页显示的记录数 +

	private int recordCount;// 总记录数 +
	private List<?> recordList;// 当前页的数据 +

	private int startPageIndex; // 页码显示的开始的页码索引
	private int endPageIndex; // 页码显示的结束的页码索引

	/**
	 * 计算指定页中的第一条记录在所有记录中的索引
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static int calculateFirstResult(int pageNum, int pageSize) {
		return (pageNum - 1) * pageSize;
	}

	private PageView() {
	}

	public PageView(int currentPage, int pageSize, int recordCount, List<?> recordList) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.recordList = recordList;

		// 计算总页码
		totalPage = (recordCount + pageSize - 1) / pageSize;

		// 计算页码索引
		startPageIndex = 1; // 页码显示的开始的页码索引
		endPageIndex = totalPage; // 页码显示的结束的页码索引

		// 分页信息中要显示的页码的数量
		int dispalyPageNumberCount = 5;

		if (totalPage > dispalyPageNumberCount) {
			// 显示当前页附近的共dispalyPageNumberCount个页码
			if (dispalyPageNumberCount % 2 == 0) {// viewPageCount 是偶数
				startPageIndex = currentPage - dispalyPageNumberCount / 2 + 1;
				endPageIndex = currentPage + dispalyPageNumberCount / 2;
			} //
			else { // viewPageCount 是奇数
				startPageIndex = currentPage - dispalyPageNumberCount / 2;
				endPageIndex = currentPage + dispalyPageNumberCount / 2;
			}

			if (startPageIndex < 1) { // 显示前 viewPageCount 个页码
				startPageIndex = 1;
				endPageIndex = dispalyPageNumberCount;
			}

			if (endPageIndex > totalPage) { // 显示后 viewPageCount 个页码
				endPageIndex = totalPage;
				startPageIndex = totalPage - dispalyPageNumberCount + 1;
			}
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public List<?> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<?> recordList) {
		this.recordList = recordList;
	}

	public int getStartPageIndex() {
		return startPageIndex;
	}

	public void setStartPageIndex(int startPageIndex) {
		this.startPageIndex = startPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

}
