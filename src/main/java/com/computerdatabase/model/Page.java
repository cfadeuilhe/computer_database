package main.java.com.computerdatabase.model;

public class Page {
	private long pageNumber;
	private long nbElementsPerPage;
	private long previous;
	private long next;
	private long last;
	
	public Page() {
	}

	public Page(long pageNumber, long nbElementsPerPage, long last) {
		this.pageNumber = pageNumber;
		this.nbElementsPerPage = nbElementsPerPage;
		this.last = last;
		this.setPrevious((pageNumber!=1)?(pageNumber-1):1);
		this.setNext((pageNumber!=last)?(pageNumber+1):last);
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
		this.setPrevious((pageNumber!=1)?pageNumber:1);
		this.setNext(pageNumber+1);
	}

	public long getNbElementsPerPage() {
		return nbElementsPerPage;
	}

	public void setNbElementsPerPage(long nbElementsPerPage) {
		this.nbElementsPerPage = nbElementsPerPage;
	}

	public long getNext() {
		return next;
	}

	private void setNext(long next) {
		this.next = next;
	}

	public long getPrevious() {
		return previous;
	}

	private void setPrevious(long previous) {
		this.previous = previous;
	}

	public long getLast() {
		return last;
	}

	public void setLast(long last) {
		this.last = last;
	}

	public long getOffset() {
		return ((this.pageNumber - 1) * this.nbElementsPerPage);
	}

	@Override
	public String toString() {
		return "Page [pageNumber=" + pageNumber + ", nbElementsPerPage=" + nbElementsPerPage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (nbElementsPerPage ^ (nbElementsPerPage >>> 32));
		result = prime * result + (int) (pageNumber ^ (pageNumber >>> 32));
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
		if (nbElementsPerPage != other.nbElementsPerPage)
			return false;
		if (pageNumber != other.pageNumber)
			return false;
		return true;
	}
}
