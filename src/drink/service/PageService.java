package drink.service;

import java.util.ArrayList;
import java.util.List;

public class PageService {
	
	private static PageService pageService = new PageService();
	
	private PageService(){};
	
	public static PageService getInstance(){
		return pageService;
	}
	
	/*
	 * 將有關 page 相關的處理都放在這裡
	 * pageString hiddenpage
	 * countPage
	 * next
	 * rowList
	 * 
	 */
	
	public int pageService (String pageString, String hiddenpage){
		int page = 1;
		if(pageString != null && !"".equals(pageString)){
    		page = Integer.parseInt(pageString);
    	}else if(hiddenpage != null && !"".equals(hiddenpage)){
    		page = Integer.parseInt(hiddenpage);
    	}
		
		return page;
	}
	
	public int countPageService(GoodSQLService goodSQLService){
		
		int count = goodSQLService.countGoodService();
    	int countPage = (count%6 == 0)?count/6:(count/6)+1;
		
    	return countPage;
	}
	
	public List<Integer> rowListService (int page, int countPage){
		int row = (page-1)/3;
    	List<Integer> rowList = new ArrayList<>();
    	int endPage = ((row+1)*3 <= countPage)?(row+1)*3:countPage;
    	for (int i = row*3+1; i<= endPage ; i ++){
    		rowList.add(i);
    	}
    	
    	return rowList;
	}
	
}
