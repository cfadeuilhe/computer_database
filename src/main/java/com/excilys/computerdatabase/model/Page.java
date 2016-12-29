package com.excilys.computerdatabase.model;

import java.util.ArrayList;
import java.util.List;

public class Page {
	private int currentPage;
	private long pageSize;
	private long pageCount;
	private String search=null;
	private List<Entity> computerList = new ArrayList<Entity>();
	
	public Page() {
	}

	public Page(int currentPage, long pageSize, long pageCount) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.pageCount = pageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int pageNumber) {
		this.currentPage = pageNumber;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long last) {
		this.pageCount = last;
	}
	
	public List<Entity> getComputerList() {
        return computerList;
    }

    public void setComputerList(List<Entity> computerList) {
        this.computerList = computerList;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public long getOffset() {
		return ((this.currentPage - 1) * this.pageSize);
	}

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", pageSize=" + pageSize + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (pageSize ^ (pageSize >>> 32));
		result = prime * result + (int) (currentPage ^ (currentPage >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (pageSize != other.pageSize)
			return false;
		if (currentPage != other.currentPage)
			return false;
		return true;
	}
}
