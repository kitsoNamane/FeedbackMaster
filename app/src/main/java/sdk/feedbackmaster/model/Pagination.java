package sdk.feedbackmaster.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pagination implements Serializable {

	@SerializedName("total")
	private int total;

	@SerializedName("count")
	private int count;

	@SerializedName("per_page")
	private int perPage;

	@SerializedName("current_page")
	private int currentPage;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("links")
	@Expose
	private Links links;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setPerPage(int perPage){
		this.perPage = perPage;
	}

	public int getPerPage(){
		return perPage;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	@Override
 	public String toString(){
		return 
			"Pagination{" + 
			"total = '" + total + '\'' + 
			",count = '" + count + '\'' + 
			",per_page = '" + perPage + '\'' + 
			",current_page = '" + currentPage + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",links = '" + links + '\'' + 
			"}";
		}
}