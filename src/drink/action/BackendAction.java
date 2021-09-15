package drink.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import drink.model.Good;
import drink.model.SaleReport;
import drink.service.GoodSQLService;
import drink.service.OrderService;
import drink.util.Util;
import drink.vo.BackendformData;

public class BackendAction extends DispatchAction {
	
	private GoodSQLService goodSQLService = GoodSQLService.getInstance();
	private OrderService orderService = OrderService.getInstance();

	//商品列表的方法
    public ActionForward queryGoods(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) {
    	
    	System.out.println("=============== QueryGoods Action ===============");
    	
    	HttpSession session = request.getSession();
    	session.removeAttribute("tempBuyMap");
    	System.out.println("暫時購物清單的 Map 已經被移除");
    	session.removeAttribute("countPage");
		
    	return mapping.findForward("GoodListView");
	}
    
    //商品維護作業的方法
    public ActionForward goodsReplenishment(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	System.out.println("=============== UpdateGoods Action ===============");
    	
    	// 將表單資料使用 struts ActionForm 方式自動綁定，省去多次由 request getParameter 取表單資料的工作
    	BackendformData dynaForm = (BackendformData) form; 
    	Good good = new Good();
    	
    	//會有 Exception
    	BeanUtils.copyProperties(good, dynaForm);
    	
    	String message ="";
    	
    	List <Good> goods = new ArrayList<>(Arrays.asList(good));
		boolean updateServiceResult = goodSQLService.updateGoodsService(goods, "Backend");
		message = updateServiceResult ? "Goods_DB已更新" : "Goods_DB更新失敗";
	
		HttpSession session = request.getSession();
		session.setAttribute("message", message);
		session.removeAttribute("goods");
		
		return mapping.findForward("GoodReplenishment");
	}
    
    //商品維護作業的頁面
    public ActionForward goodsReplenishmentView(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	// 被選擇要修改的帳號資料
    	String id = request.getParameter("goodsID");
    	if (id != null){
    		//將 id 塞進 Good 的 List，找尋資料庫的方法
    		List<Good> showGoods = new ArrayList<>();
    		Good good = new Good();
    		good.setGoodsID(Integer.parseInt(id));
    		showGoods.add(good);
    		Good replenishment = goodSQLService.returnDBGoods(showGoods).get(0);
    		request.setAttribute("Replenishment", replenishment);
    	}
    	
    	return mapping.findForward("GoodReplenishmentView");
    }
    
    //商品新增上架的方法
    public ActionForward goodsCreate(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	System.out.println("=============== AddGoods Action ===============");
    	// 將表單資料使用 struts ActionForm 方式自動綁定，省去多次由 request getParameter 取表單資料的工作
    	BackendformData dynaForm = (BackendformData) form; 
    	Good good = new Good();
    	
    	//會有 Exception
    	BeanUtils.copyProperties(good, dynaForm);
    	
    	boolean Creatresult = goodSQLService.creatGoodService(good);
    	   		
    	//上傳圖片
    	boolean updateImageResult = false;
    	FormFile myfile =(FormFile) PropertyUtils.getSimpleProperty(form, "goodsImage");
    	FileOutputStream fos = null;
        try{
            ServletContext application = this.getServlet().getServletContext();
            String realPath = application.getRealPath("/DrinksImage/");
            byte [] data = myfile.getFileData();
            fos = new FileOutputStream(realPath+"/"+myfile.getFileName());
            fos.write(data);
            updateImageResult = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fos.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        
    	String message = (Creatresult && updateImageResult) ? "已新增加一筆商品資訊" : "失敗，尚未新增";
    	
    	HttpSession session = request.getSession();
		session.setAttribute("message", message);
		session.removeAttribute("goods");
		
		return mapping.findForward("GoodsCreate");
	}
    
    //商品新增上架的頁面
    public ActionForward goodsCreateView(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	return mapping.findForward("GoodsCreateView");
    }
    
    //商品刪除的方法
    public ActionForward goodsdelete(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//有不存在的資料，如何 debug
    	String deleID = request.getParameter("deleID");
    	boolean deleResult = goodSQLService.deleGoodService(deleID);
    	//boolean deleResult = goodSQLService.deleGoodService(deleID);
    	String message = (deleResult) ? "已刪除一筆資料" : "To be confirm";
    	
    	
    	HttpSession session = request.getSession();
		session.setAttribute("message", message);
		session.removeAttribute("goods");
    	
    	return mapping.findForward("afterdeleteShow");
    }
    
    //銷售報表的方法
    public ActionForward goodsSaleReport(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) {
    	
    	System.out.println("=============== querySalesReport Action ===============");
    	/*
		 * 1. 擷取從 html 傳遞過來的參數 (時間格式)
		 * 2. 將參數傳引到處理 SQL 的 service 裡面 (查詢時間範圍內的訂單)
		 * 3. 回傳資料(List)並印出來
		 * 4. 重導到顯示的 html 頁面
		 */
    	BackendformData dynaForm = (BackendformData) form;
		String startDate = dynaForm.getQueryStartDate();
		String endDate = dynaForm.getQueryEndDate();
		String message ="";
		if (!"".equals(startDate) && !"".equals(endDate)){
			//要判斷大到小
			if (Util.compareTime(startDate, endDate)){
				List<SaleReport>saleReports = orderService.HistoryOrder(startDate, endDate);
				if (saleReports.isEmpty()){
					message = "您選的這段範圍內沒有任何報表資料喔 !";
				}else{
					request.setAttribute("saleReports", saleReports);
				}
			}
			else {
				message = "'迄'的時間不能早於'起'的時間喔 !";
			}
			
		}else{
			message = "請確實選擇一個時間區間";
		}
		HttpSession session = request.getSession();
		session.setAttribute("message", message);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		
		return mapping.findForward("GoodsSaleReportView");
	}
    
    //銷售報表的頁面
    public ActionForward goodsSaleReportView(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) {
    	
    	return mapping.findForward("GoodsSaleReportView");
    }
	
}
