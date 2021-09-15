package drink.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import drink.model.Account;
import drink.model.Good;
import drink.service.CalService;
import drink.service.GoodSQLService;
import drink.service.OrderService;
import drink.service.PageService;
import drink.util.Util;
import drink.vo.FrontformData;

public class FrontendAction extends DispatchAction {
	
	private CalService calService = CalService.getInstance();
	private GoodSQLService goodSQLService = GoodSQLService.getInstance();
	private OrderService orderService = OrderService.getInstance();
	private PageService pageService = PageService.getInstance();

	//商品列表的方法
    public ActionForward buyGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("=============== Frontend Action ===============");
    	
    	FrontformData formData = (FrontformData) form;
    	//投入金額
		int RealMoney = formData.getInputMoney();
		//取 session，並新增新的值
		HttpSession session = request.getSession();
		Map<Integer, String> tempBuyMap = (Map<Integer, String>) session.getAttribute("tempBuyMap");
		for (int i = 0; i <formData.getGoodsID().length; i++){
    		tempBuyMap.put(Integer.parseInt(formData.getGoodsID()[i]), formData.getBuyQuantity()[i]);
    	}
		
		//將ID與數量組合，並移除數量為0的組別
		List<Good> buyGoodsList = Util.buyGoodsList(tempBuyMap);
		//計算總共花費
		List<Good> buyGoodsListMoney = calService.BuyRealGoodsMoney(buyGoodsList);

		String message ="";
		if (RealMoney >= calService.calCustomerBuy(buyGoodsListMoney)){
			//查詢真實庫存數量
			List<Good> BuyRealGoods = calService.BuyRealGoods(buyGoodsList);
			int costMoney = calService.calCustomerBuy(BuyRealGoods);
			//Util.buyGoodsSuccess(RealMoney, costMoney, BuyRealGoods);
			session.setAttribute("returnMoney", (RealMoney - costMoney));
			message += "購買成功<br/>";
			message += Util.showBuyGoods(BuyRealGoods);
			
			//更新資料庫 addBatch
			boolean resultGoodsDB = goodSQLService.updateGoodsService(BuyRealGoods, "Frontend");
			message += resultGoodsDB ? "庫存數量更新成功<br/>":"庫存數量更新失敗<br/>";
			
			//新增訂單至ORDER的DB
			Account account = (Account) session.getAttribute("account");
			String Customer_ID = account.getId();
			
			boolean resultOrdersDB = orderService.updateOrdersService(
					orderService.GoodsToOrders(BuyRealGoods, Customer_ID));
			message += resultOrdersDB ? "歷史訂單更新成功<br/>":"歷史訂單更新失敗<br/>";
			session.removeAttribute("tempBuyMap");
			session.removeAttribute("countPage");
		}else{
			message ="您所投入的金額不足喔 !";
			session.setAttribute("returnMoney", RealMoney);
			tempBuyMap = new HashMap<Integer, String>();
	    	session.setAttribute("tempBuyMap", tempBuyMap);
			
		}
		session.setAttribute("message", message);
		//清空資料
		
		
		return mapping.findForward("VendingMachine");
	}
    
    /*
     * request, page
     * sesssion, countPage
     * request, next
     * request, rowList
     * request, showgoods
     * session, tempBuyMap
     */
    public ActionForward buyGoodsView(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) {
    	
    	//分頁的頁數值，解決 js 產生的問題 !
//    	page = 1;
    	String pageString = request.getParameter("page");
    	String hiddenpage = request.getParameter("hiddenpage");
    	String search = request.getParameter("search");
    	//System.out.println(pageString);
    	
    	//移動到gageService進行處理
    	int page = pageService.pageService(pageString, hiddenpage);
    	
    	request.setAttribute("page", page);
    	
    	//取出總共有效資料的筆數(session)
    	HttpSession session = request.getSession();
    	int countPage = 0;
    	if (session.getAttribute("countPage")!= null){
    		countPage = (int) session.getAttribute("countPage");
    	}else{
    		int count = goodSQLService.countGoodService();
        	countPage = (count%6 == 0)?count/6:(count/6)+1;
        	session.setAttribute("countPage", countPage);
    	}
    	
    	//是否可以選擇下一頁的判斷
    	int next = (countPage > page)? 1: 0;
    	request.setAttribute("next", next);
    		
    	//傳出分頁的編排,移動到gageService進行處理
    	List<Integer> rowList = pageService.rowListService(page, countPage);
    	
    	//System.out.println("request, rowList, "+ rowList);
    	request.setAttribute("rowList", rowList);
    	
    	//與資料庫連線，找出該分頁的內容，並補滿六筆資料
    	List<Good> showgoods = goodSQLService.returnShowGoodsService(page);
    	// 避免前端 JSTL 在顯示時，產生 IndexOutOfBoundsException 
    	while (showgoods.size() !=6){
    		showgoods.add(null);
    	}
    	request.setAttribute("showgoods", showgoods);
    	
    	//擷取暫時的購買清單(session)
    	FrontformData formData = (FrontformData) form;
    	
    	Map<Integer, String> tempBuyMap ;
    	if (formData.getGoodsID() !=null){
	    	if (session.getAttribute("tempBuyMap") != null){
	    		tempBuyMap =  (Map<Integer, String>) session.getAttribute("tempBuyMap");
	    		for (int i = 0; i <formData.getGoodsID().length; i++){
		    		tempBuyMap.put(Integer.parseInt(formData.getGoodsID()[i]), formData.getBuyQuantity()[i]);
		    	}
	    	}else{
	    		tempBuyMap = new HashMap<Integer, String>();
	    		System.out.println("暫時購物清單的 Map 已經被建立");
	    	}
	    	System.out.println("tempBuyMap : " + tempBuyMap);
	    	session.setAttribute("tempBuyMap", tempBuyMap);
    	}
    	
    	session.removeAttribute("goods");
    	
    	return mapping.findForward("BuyGoodsView");
    }

    public ActionForward initial(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	HttpSession session = request.getSession();
    	Map<Integer, String> tempBuyMap = new HashMap<Integer, String>();
    	System.out.println("暫時購物清單的 Map 已經被建立");
    	session.setAttribute("tempBuyMap", tempBuyMap);
    	
    	int countPage = pageService.countPageService(goodSQLService);
    	session.setAttribute("countPage", countPage);
    	  	
    	return mapping.findForward("initial");
    }
}
