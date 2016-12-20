package main.java.com.computerdatabase.model;

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
		return ((this.pageNumber - 1) * this.nbElementsPerPage);
	}

	public void setNbElementsPerPage(int nbElementsPerPage) {
		this.nbElementsPerPage = nbElementsPerPage;
	}

	@Override
	public String toString() {
		return "Page [pageNumber=" + pageNumber + ", nbElementsPerPage=" + nbElementsPerPage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nbElementsPerPage;
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
