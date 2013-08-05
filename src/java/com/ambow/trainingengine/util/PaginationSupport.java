/**
 * 
 */
package com.ambow.trainingengine.util;



/**
 * @author yuanjunqi
 *
 */
/**
 * @author yuan_junqi
 *
 */
public class PaginationSupport {
	
	// 总记录数
    private int totalCount =0;
    
    // 总页数
    private int totalPage =0;
	
    // 开始页面
    private int beginPage = 1;
    
    // 结束页面
    private int endPage =1;
    
    // 当前页面
    private int page = 1;
    
	public PaginationSupport(int totalCount,int page){
		this.setTotalCount(totalCount);
		this.setPage(page);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
        if (totalCount > 0){
            this.totalCount = totalCount;
            int count = totalCount / 10;
            if (totalCount % 10 > 0){
                count++;
            }
        } else{
            this.totalCount = 0;
        }
	}

	public int getTotalPage() {
        if (getTotalCount() == 0)
            return 0;
        else
            return (getTotalCount()-1) / 10 + 1;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBeginPage() {
		if(this.getPage()%10==0){
			beginPage = (this.getPage()-1)/10*10+1;
		}else{
			beginPage = this.getPage()/10*10+1;
		}
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		endPage = beginPage +9;
		if(endPage>this.getTotalPage()){
			endPage = this.getTotalPage();
		}
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
    
}
