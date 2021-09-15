package drink.vo;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class FrontformData extends ActionForm{
	/*
	 * ref :　https://www.twblogs.net/a/5b8b1b3c2b717718832d5508
	 * １. ActionForm 必須擴展自org.apache.struts.action.ActionForm。
	 * 	      基類ActionForm 是不能實例化的。
	 * 	  (ActionForm)只能在 JavaEE 使用，JavaSE無法使用
	 * 2. ActionForm 必須爲每個應該從請求中收集的HTML輸入控件定義一個公共屬性。
	 * 	  ActionForm還可能要符合一些可選的要求
	 * 3. 如果你要求ActionForm 在傳遞屬性到Action之前校驗它們，你就必須實現
	 * 	  validate方法
	 * 4. 如果想在組裝前初始化屬性，必須實現 reset , 它在ActionForm 組裝前被調用
	 */
	private String[] goodsID;
	private String[] buyQuantity;
	//名稱要以小寫為主
	private String customerID;
	private int inputMoney;

	
	public String[] getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(String[] goodsID) {
		this.goodsID = goodsID;
	}
	public String[] getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(String[] buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public int getInputMoney() {
		return inputMoney;
	}
	public void setInputMoney(int inputMoney) {
		this.inputMoney = inputMoney;
	}
	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.goodsID = null;
		this.buyQuantity = null;
		//this.customerID ="";
		//this.inputMoney = 0;
		
	}
//	
//	@Override
//	public ActionErrors validate (ActionMapping mapping, HttpServletRequest request){
//		ActionErrors actionErrors = new ActionErrors();
//		return actionErrors;
//	}
	
	
	
	

}
