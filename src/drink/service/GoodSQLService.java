package drink.service;

import java.util.List;

import drink.dao.GoodsDao;
import drink.model.Good;
import drink.util.Util;

//處理有關SQL的動作
public class GoodSQLService {
	
	//singleton pattern (單一實例化模式)
	//建立一個自己的物件
	private static GoodSQLService goodSQLService = new GoodSQLService();
	
	//讓該物件不會被實例化
	private GoodSQLService(){}
	
	//取得實例化物件
	public static GoodSQLService getInstance(){
		return goodSQLService;
	}
	
	//需要使用到 DrinkDao 的物件
	private GoodsDao goodsDao = GoodsDao.getInstance();
	
	
	//執行全部查詢動作
	public List<Good> queryAllGoodsService(){
		return goodsDao.queryAllGoodsSQL();
	}
	
	//前後端執行商品維護動作 
	public boolean updateGoodsService(List<Good> goods, String Servlet){
		List <Good> SQLgoods = returnDBGoods(goods);
		boolean result = false;
		for (int i = 0; i < SQLgoods.size(); i ++){
			switch (Servlet){
			case "Backend":
				/*
				 * 1. 更新價錢
				 * 2. 更新數量
				 * 3. 更新狀態
				 */
				goods.get(i).setGoodsQuantity(SQLgoods.get(i).getGoodsQuantity() + goods.get(i).getGoodsQuantity());
				result = goodsDao.updateGoodsSQL(goods);
				break;
			case "Frontend":
				/*
				 * 1. 更新數量
				 */
				SQLgoods.get(i).setGoodsQuantity(SQLgoods.get(i).getGoodsQuantity() - goods.get(i).getGoodsQuantity());
				result = goodsDao.updateGoodsSQL(SQLgoods);
				break;
			}
		}
		
		return result;
	}

	
	//執行新增一筆資料的動作
	public boolean creatGoodService (Good good){
		return goodsDao.createGoodSQL(good);
	}
	
	//刪除一筆資料
	public boolean deleGoodService (String deleID){
		return goodsDao.deleteGoodSQL(deleID);
	}
		
	//根據原本的List回傳資料庫內所對應的List
	public List<Good> returnDBGoods(List<Good> goods){
		String ID = Util.totalIDs(goods);
		List<Good> SQLgoods = goodsDao.ｕseIDFindGoodsSQL(ID);
		return SQLgoods;
	}
	
	//回傳前端需要顯示的資料
	public List<Good> returnShowGoodsService (int page){
		return goodsDao.returnSelectPage(page);
	}

	//回傳有效的資料筆數
	public int countGoodService(){
		return goodsDao.countGoodSQL();
	}
}
