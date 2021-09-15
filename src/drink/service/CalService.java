package drink.service;

import java.util.ArrayList;
import java.util.List;

import drink.model.Good;

public class CalService {
	
	private static CalService calService = new CalService();
	
	private CalService() {}
	
	public static CalService getInstance(){
		return calService;
	}
	
	private GoodSQLService goodSQLService = GoodSQLService.getInstance();
	
	
	//利用購買清單與SQL回傳資料比對，推論實際應買的品項與數量
	public List<Good> BuyRealGoods (List<Good> buyGoodsList){
		//先用購買的數量，求出實際購買的數量
		List<Good> SQLGoodsList = goodSQLService.returnDBGoods(buyGoodsList);
		List<Good> newSQLGoodsList =  new ArrayList<Good>();
		//預計修改SQL回傳的List，做為整個方法的回傳值
		for (int i = 0; i< SQLGoodsList.size(); i ++){
			//如果商品目前為下架的狀態，則不給客戶購買 (將購買數量歸零)
			//或是庫存沒有大於 0 
			if (SQLGoodsList.get(i).isStatus()){
				if (SQLGoodsList.get(i).getGoodsQuantity()>0){
					if (SQLGoodsList.get(i).getGoodsQuantity() > buyGoodsList.get(i).getGoodsQuantity()){
						SQLGoodsList.get(i).setGoodsQuantity(buyGoodsList.get(i).getGoodsQuantity());
					}
					newSQLGoodsList.add(SQLGoodsList.get(i));
				}
			}
		}		
		return newSQLGoodsList;
	}			

	
	//計算購買所需要花費的金額 (單價 * 數量)
	public int calCustomerBuy(List<Good> buyRealGoods){
		int calBuyMoney = 0;
		for (Good good : buyRealGoods){
			calBuyMoney += good.getGoodsPrice() * good.getGoodsQuantity();
		}
		return calBuyMoney;
	}
	
	

	public List<Good> BuyRealGoodsMoney (List<Good> buyGoodsList){
		//先用購買的數量，求出實際購買的數量
		List<Good> SQLGoodsList = goodSQLService.returnDBGoods(buyGoodsList);
		for (int i = 0; i< buyGoodsList.size(); i++){
			buyGoodsList.get(i).setGoodsPrice(SQLGoodsList.get(i).getGoodsPrice());
		}
		return buyGoodsList;
	}
}
