package drink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import drink.model.Good;

//連結至商品的資料表
public class GoodsDao {
	
	//singleton pattern (單一實例化模式)
	//建立一個自己的物件
	private static GoodsDao drinkDao = new GoodsDao();
	
	//讓該物件不會被實例化
	private GoodsDao(){}
	
	//取得實例化物件
	public static GoodsDao getInstance(){
		return drinkDao;
	}
	
	//執行全部查詢的SQL 
	public List<Good> queryAllGoodsSQL(){
		List<Good> Goods = new ArrayList<Good>();
		//SQL 語法
		String querySQL = "Select * from BEVERAGE_GOODS";
		//取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
			// Step2:建立 prepareStatement 
			PreparedStatement stmt = conn.prepareStatement(querySQL);
			ResultSet rs = stmt.executeQuery()){
			while(rs.next()){
				Good good = new Good();
				good.setGoodsID(rs.getInt("GOODS_ID"));
				good.setGoodsName(rs.getString("GOODS_NAME"));
				good.setGoodsPrice(rs.getInt("PRICE"));
				good.setGoodsQuantity(rs.getInt("QUANTITY"));
				good.setGoodsImage(rs.getString("IMAGE_NAME"));
				good.setStatus(rs.getBoolean("STATUS"));
				Goods.add(good);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}	
		return Goods;
	}
	
	//執行商品維護的SQL (利用批次更新資料)
	public boolean updateGoodsSQL (List<Good> goods){
		boolean updateresult = true;
		String updateSQL = "UPDATE BEVERAGE_GOODS SET "
				+ "QUANTITY = ? "
				+ ",PRICE = ? "
				+ ",STATUS = ? "
				+ "WHERE GOODS_ID = ?" ; 
		try(Connection conn = DBConnectionFactory.getOracleDBConnection();){
			conn.setAutoCommit(false);				
			try(PreparedStatement stmt = conn.prepareStatement(updateSQL)){
				for (Good good : goods){
					stmt.setInt(1, good.getGoodsQuantity());
					stmt.setInt(2, good.getGoodsPrice());
					stmt.setBoolean(3, good.isStatus());
					stmt.setInt(4, good.getGoodsID());
					stmt.addBatch();
				}
				int[] insertCounts = stmt.executeBatch();
				//確認回傳直接為正確
				for (int item : insertCounts){
					if (item != -2){
						updateresult = false;
					}
				}
				if (updateresult){
					conn.commit();
				}
			}catch (SQLException e){
				conn.rollback();
				throw e;
			}
		}catch (SQLException e){
			updateresult = false;
			e.printStackTrace();
		}
		return updateresult;
	}	
		
	//執行商品上架的SQL
	public boolean createGoodSQL (Good good){
		//因為執行完不會回傳參數，因此用true/false 決定是否成功
		boolean updateresult = false;
		//此為DML(資料操作語言)，需要先關閉自動提交功能
		//SQL 語法 : UPDATE "表格" SET "欄位1" = 值1, "欄位2" = 值2 WHRER {條件}
		String updateSQL = "INSERT INTO BEVERAGE_GOODS "
				+ "VALUES (BEVERAGE_GOODS_SEQ.NEXTVAL,?,?,?,?,?)";
		try(Connection conn = DBConnectionFactory.getOracleDBConnection();){
			//關閉自動提供功能
			conn.setAutoCommit(false);
			//prepareStatement For SQL
			try(PreparedStatement stmt = conn.prepareStatement(updateSQL)){
				//將值塞進去，順序回對應 "?"的順序
				stmt.setString(1, good.getGoodsName());
				stmt.setInt(2, good.getGoodsPrice());
				stmt.setInt(3, good.getGoodsQuantity());
				stmt.setString(4, good.getGoodsImage());
				stmt.setBoolean(5, good.isStatus());
				//回傳異動的筆數
				int recordCount = stmt.executeUpdate();
				//如果有大於0(大於0筆以上)，則表示成功，並傳成功的訊息出去
				updateresult = (recordCount >0)? true: false;
				//沒有問題，則commit
				conn.commit();
			}catch (SQLException e){
				//若有錯誤，則資料倒回(rollback)
				conn.rollback();
				throw e;
			}
		}catch (SQLException e){
			//如果 conn有Exception，則取消交易
			updateresult = false;
			e.printStackTrace();
		}
		return updateresult;
	}
	
	//執行查詢特定ID的SQL
	/*
	 * 因為已經額外將ID 組合成 totalID，(id, id, id)
	 * 要改的話，要改成 addBatch
	 */
	public List<Good> ｕseIDFindGoodsSQL(String TotalID){
		List<Good> Goods = new ArrayList<Good>();
		//SQL 語法 : select * from BEVERAGE_GOODS where GOODS_NAME = 'coke_original';
		String findIDSQL = "Select * from BEVERAGE_GOODS WHERE GOODS_ID in ";
		//補上最後的字串
		findIDSQL += TotalID;
		//取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
			// Step2:建立 prepareStatement 
			PreparedStatement stmt = conn.prepareStatement(findIDSQL);
			ResultSet rs = stmt.executeQuery()){
			while(rs.next()){
				Good good = new Good();
				good.setGoodsID(rs.getInt("GOODS_ID"));
				good.setGoodsName(rs.getString("GOODS_NAME"));
				good.setGoodsPrice(rs.getInt("PRICE"));
				good.setGoodsQuantity(rs.getInt("QUANTITY"));
				good.setGoodsImage(rs.getString("IMAGE_NAME"));
				good.setStatus(rs.getBoolean("STATUS"));
				Goods.add(good);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
					
		return Goods;
	
	}
	
	//查詢名稱，並回傳以分頁的方式回傳
	public List<Good> returnSelectPage(int page){
		List<Good> Goods = new ArrayList<Good>();
		//SQL 語法
		String SQLcommand = "select * from"
				+ " (select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME,"
				+ " PRICE, QUANTITY,IMAGE_NAME, STATUS"
				+ " from BEVERAGE_GOODS WHERE STATUS = 1"
				+ " and QUANTITY >0)"
				+ " where ROW_NUM between ? and ?";
		
		//取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
			 PreparedStatement stmt = conn.prepareStatement(SQLcommand)){
			 stmt.setInt(1, (page-1)*6+1);
			 stmt.setInt(2, page*6);
			 try(ResultSet rs = stmt.executeQuery()){
				 while(rs.next()){
					 Good good = new Good();
					 good.setGoodsID(rs.getInt("GOODS_ID"));
					 good.setGoodsName(rs.getString("GOODS_NAME"));
					 good.setGoodsPrice(rs.getInt("PRICE"));
					 good.setGoodsQuantity(rs.getInt("QUANTITY"));
					 good.setGoodsImage("DrinksImage/" + rs.getString("IMAGE_NAME"));
					 good.setStatus(rs.getBoolean("STATUS"));
					 Goods.add(good);
				 }
			 }catch (SQLException e){
				 e.printStackTrace();
			 }
		}catch (SQLException e){
			e.printStackTrace();
		}
		return Goods;
	}
	
	//模糊搜尋，將輸入的字轉成大寫，SQL回傳的字，也轉成大寫
	public List<Good> fuzzySelect(String word){
		List<Good> Goods = new ArrayList<Good>();
		//SQL 語法
		String SQLcommand = "select * from BEVERAGE_GOODS where UPPER(GOODS_NAME) like ?";
		//取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
			 PreparedStatement stmt = conn.prepareStatement(SQLcommand)){
			word = "%" + word.toUpperCase() + "%";
			 stmt.setString(1, word);
			 try(ResultSet rs = stmt.executeQuery()){
				 while(rs.next()){
					 Good good = new Good();
					 /*
					  * .....setter
					  */
					 Goods.add(good);
				 }
			 }catch (SQLException e){
				 e.printStackTrace();
			 }
		}catch (SQLException e){
			e.printStackTrace();
		}
		return Goods;
	}
	
	//刪除單一筆資料
	public boolean deleteGoodSQL(String deleID){
		boolean updateresult = true;
		//SQL 語法 : UPDATE "表格" SET "欄位1" = 值1, "欄位2" = 值2 WHRER {條件}
		String deleSQL = "DELETE BEVERAGE_GOODS where GOODS_ID = ?";
		try(Connection conn = DBConnectionFactory.getOracleDBConnection();){
			conn.setAutoCommit(false);
			try(PreparedStatement stmt = conn.prepareStatement(deleSQL)){
				//將值塞進去，順序回對應 "?"的順序
				stmt.setInt(1, Integer.parseInt(deleID));
				int recordCount = stmt.executeUpdate();
				updateresult = (recordCount >0)? true: false;
				conn.commit();
			}catch (SQLException e){
				conn.rollback();
				throw e;
			}
		}catch (SQLException e){
			updateresult = false;
			e.printStackTrace();
		}
		return updateresult;
	}
	
	//回傳有效資料的數量
	public int countGoodSQL(){
		String countSQL = "select COUNT(*)count from BEVERAGE_GOODS"
				+ " where STATUS = 1 and QUANTITY >0";
		int count = 0;
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
			 PreparedStatement stmt = conn.prepareStatement(countSQL)){
			 try(ResultSet rs = stmt.executeQuery()){
				 while(rs.next()){
					 count = rs.getInt("count");
				 }
			 }catch (SQLException e){
				 e.printStackTrace();
			 }
		}catch (SQLException e){
			e.printStackTrace();
		}
		return count;
	}
	
	
}
