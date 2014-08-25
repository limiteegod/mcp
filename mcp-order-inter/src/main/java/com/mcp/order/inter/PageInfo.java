/**
 * 
 */
package com.mcp.order.inter;

/**
 * @author ming.li
 *
 */
public class PageInfo {

    public PageInfo()
    {
        this(0, 0, 0, 0, 0L, false, false, false, false);
    }
	
	public PageInfo(int number, int size, int totalPages, int numberOfElements, 
			long totalElements, boolean hasPreviousPage, boolean firstPage, 
			boolean hasNextPage, boolean lastPage)
	{
		this.number = number;
		this.size = size;
		this.totalPages = totalPages;
		this.numberOfElements = numberOfElements;
		this.totalElements = totalElements;
		this.hasPreviousPage = hasPreviousPage;
		this.firstPage = firstPage;
		this.hasNextPage = hasNextPage;
		this.lastPage = lastPage;
	}
	
	/**
	 * 当前页号，从0开始
	 */
	private int number;
	
	/**
	 * 每页记录条数
	 */
	private int size;
	
	/**
	 * 总共有多少页
	 */
	private int totalPages;
	
	/**
	 * 当前页记录条数
	 */
	private int numberOfElements;
	
	/**
	 * 所有记录条数
	 */
	private long totalElements;
	
	/**
	 * 是否有前一页
	 */
	private boolean hasPreviousPage;
	
	/**
	 * 是否是第一页
	 */
	private boolean firstPage;
	
	/**
	 * 是否有后一页
	 */
	private boolean hasNextPage;
	
	/**
	 * 是否是最后一页
	 */
	private boolean lastPage;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}
}
