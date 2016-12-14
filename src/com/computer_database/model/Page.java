package com.computer_database.model;

public class Page {
	private long pageNumber;
	private int nbElementsPerPage;

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getNbElementsPerPage() {
		return nbElementsPerPage;
	}

	public long getOffset() {
		return ((this.pageNumber-1) * this.nbElementsPerPage);
	}

	public void setNbElementsPerPage(int nbElementsPerPage) {
		this.nbElementsPerPage = nbElementsPerPage;
	}

}
