package drink.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import drink.model.Good;

public class Util {
	
	//將讀取到的兩個陣列，組合到一個 List<Good> 裡面
	public static List<Good> buyGoodsList (Map<Integer, String> tempBuyMap){
		List<Good> Goods = new ArrayList<>();
		for(int key : tempBuyMap.keySet()){
			Good good = new Good();
			if (Integer.parseInt(tempBuyMap.get(key))!= 0){
				good.setGoodsID(key);
				good.setGoodsQuantity(Integer.parseInt(tempBuyMap.get(key)));
				Goods.add(good);
			}
		}
		
		
		return Goods;
	}
	
	//組合成SQL 內的查詢文字
	public static String totalIDs (List<Good> CustomerOrderGoods){
		String useInWordSQL ="(";
		for(int i =0; i< CustomerOrderGoods.size(); i++){
			useInWordSQL += CustomerOrderGoods.get(i).getGoodsID();
			useInWordSQL += ",";
		}
		//把最後一個","拿掉
		useInWordSQL = useInWordSQL.substring(0, useInWordSQL.length()-1);
		useInWordSQL +=")";
		return useInWordSQL;
	}
	
	public static void buyGoodsSuccess (int RealMoney, int costMoney, List<Good> BuyRealGoods){
		System.out.println("您所投入的金額 :" + RealMoney);
		System.out.println("您這次花的費用 :" + costMoney);
		System.out.println("找回的零錢 : " + (RealMoney - costMoney));
		//showBuyGoods(BuyRealGoods);
	}
	
	public static void buyGoodsFail (int RealMoney){
		System.out.println("您所投入的金額為 : " + RealMoney);
		System.out.println("您這次花的費用 : 0");
		System.out.println("找回的零錢 : " + RealMoney);
	}
	
	
	//列印購買的清單
	public static String showBuyGoods (List<Good> NewOrder){
		String message = "";
		message += "=========================<br/>";
		message += "您所購買的商品如下<br/>";
		for (Good good : NewOrder){
			message += "商品名稱 : " + good.getGoodsName()+",";
			message += "商品數量 : " + good.getGoodsQuantity()+"<br/>";
		}
		return message;
	}
	
	public static boolean compareTime (String StartDate, String EndDate){
		String time1 = StartDate.replaceAll("-", "");
		String time2 = EndDate.replaceAll("-", "");
		if (Integer.parseInt(time1) <= Integer.parseInt(time2)){
			return true;
		}else{
			return false;
		}
	}
	
}
