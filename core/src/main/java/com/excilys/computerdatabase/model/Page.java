package com.excilys.computerdatabase.model;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private int currentPage;
    private int pageSize;
    private int pageCount;
    private String search;
    private String order;
    private List<Computer> computerList = new ArrayList<Computer>();

    public Page() {
    }

    public Page(int currentPage, int pageSize, int pageCount) {
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
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int last) {
        this.pageCount = last;
    }
    public List<Computer> getComputerList() {
        return computerList;
    }
    public void setComputerList(List<Computer> list) {
        this.computerList = list;
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
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Page [currentPage=" + currentPage + ", pageSize=" + pageSize + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (pageSize ^ (pageSize >>> 32));
        result = prime * result + (currentPage ^ (currentPage >>> 32));
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
