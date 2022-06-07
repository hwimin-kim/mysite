package com.douzone.mysite.vo;

public class PagingVo {
	private int startPage;
	private int endPage;
	private int pageCount;
	private int prePage;
	private int nextPage;
	private int currentPage;
	private int listCount;
	private int maxPage;
	private int minPage;
	
	public void calcPage() {
		if(currentPage % pageCount == 0) {
			startPage = currentPage - pageCount + 1;
			endPage = currentPage;
		} else {
			startPage = (currentPage/pageCount) * pageCount + 1;
			endPage = startPage + pageCount - 1;
		}		
	}
	
	public void calcMaxPage() {
		if(listCount == 0)
			maxPage = 1;
		else if(listCount % pageCount != 0)
			maxPage = (listCount/pageCount) + 1;
		else
			maxPage = (listCount/pageCount);
	}
	
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPrePage() {
		return prePage;
	}
	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
		
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
	
	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	
	
	public int getMinPage() {
		return minPage;
	}

	public void setMinPage(int minPage) {
		this.minPage = minPage;
	}

	@Override
	public String toString() {
		return "PagingVo [startPage=" + startPage + ", endPage=" + endPage + ", pageCount=" + pageCount + ", prePage="
				+ prePage + ", nextPage=" + nextPage + ", currentPage=" + currentPage + ", listCount=" + listCount
				+ ", maxPage=" + maxPage + "]";
	}
}
