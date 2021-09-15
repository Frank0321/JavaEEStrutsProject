package drink.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import drink.model.Good;
import drink.model.Order;
import drink.model.SaleReport;


//連結至訂單的資料表
public class OrderDao {
	
	//singleton pattern (單一實例化模式)
	//建立一個自己的物件
	public static OrderDao orderDao = new OrderDao();
	
	//讓該物件不會被實例化
	private OrderDao (){}
	
	//取的實例化物件
	public static OrderDao getInstance(){
		return orderDao;
	}
	
	//新增 Order 的資料
	public boolean createOrdersSQL(List<Order> orders){
		boolean updateresult = true;
		String updateSQL = "INSERT INTO BEVERAGE_ORDER "
				+ "VALUES (BEVERAGE_GOODS_SEQ.NEXTVAL,?,?,?,?,?)"; 
		try(Connection conn = DBConnectionFactory.getOracleDBConnection();){
			conn.setAutoCommit(false);
			try(PreparedStatement stmt = conn.prepareStatement(updateSQL)){
				for(Order order : orders){
					stmt.setTimestamp(1, java.sql.Timestamp.from(java.time.Instant.now()));
					stmt.setString(2, order.getCustomer_ID());
					stmt.setInt(3, order.getGoods_ID());
					stmt.setInt(4, order.getGoods_buy_price());
					stmt.setInt(5, order.getBuys_quantity());
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
	
	//查詢時間範圍內的訂單資訊
	public List<SaleReport> historyOrder (String StartDate, String EndDate){
		List<SaleReport> saleReports = new ArrayList<>();
		//SQL 語法
		String querySQL = "select O.ORDER_ID, O.ORDER_DATE, O.BUY_QUANTITY, O.GOODS_BUY_PRICE," 
		 + " M.CUSTOMER_NAME, G.GOODS_NAME,"
		 + " O.BUY_QUANTITY*O.GOODS_BUY_PRICE AS COST"
		 + " from BEVERAGE_ORDER O"
		 + " inner JOIN BEVERAGE_MEMBER M"
		 + " on O.CUSTOMER_ID = M.IDENTIFICATION_NO" 
		 + " inner JOIN BEVERAGE_GOODS G"
		 + " on O.GOODS_ID = G.GOODS_ID"
		 //+ " where O.ORDER_DATE BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD ') AND TO_TIMESTAMP(?, 'YYYY-MM-DD')";
		 + " where O.ORDER_DATE BETWEEN TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')"
		 + " AND TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')"
		 + "ORDER BY O.ORDER_ID";
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
				 PreparedStatement stmt = conn.prepareStatement(querySQL)){
			     StartDate += " 00:00:00";
			     EndDate += " 23:59:59";
				 stmt.setString(1, StartDate);
				 stmt.setString(2, EndDate);
				 try(ResultSet rs = stmt.executeQuery()){
					 while(rs.next()){
						 SaleReport saleReport = new SaleReport();
						 saleReport.setOrder_ID(rs.getInt("ORDER_ID"));
						 saleReport.setOrder_Date(rs.getTimestamp("ORDER_DATE"));
						 saleReport.setBuy_Quantity(rs.getInt("BUY_QUANTITY"));
						 saleReport.setGoods_Buy_Price(rs.getInt("GOODS_BUY_PRICE"));
						 saleReport.setCustomer_Name(rs.getString("CUSTOMER_NAME"));
						 saleReport.setGoods_Name(rs.getString("GOODS_NAME"));
						 saleReport.setCost(rs.getInt("COST"));
						 saleReports.add(saleReport);
					 }
				 }catch (SQLException e){
					 e.printStackTrace();
				 }
			}catch (SQLException e){
				e.printStackTrace();
			}
		return saleReports;
	}
	
	
	//執行查詢訂單是否有該產品販售紀錄
	public boolean checkGoodIdInOrderSQL(String deleID){
		boolean check = false;
		//SQL 語法
		//Select * from BEVERAGE_ORDER where GOODS_ID in 7;
		//Select * from BEVERAGE_ORDER where GOODS_ID  7;;
		String findIDSQL = "Select * from BEVERAGE_ORDER WHERE GOODS_ID IN ";
		//補上最後的字串
		findIDSQL += deleID;
		//取得Connection
		try (Connection conn = DBConnectionFactory.getOracleDBConnection();
			// Step2:建立 prepareStatement 
			PreparedStatement stmt = conn.prepareStatement(findIDSQL);
			ResultSet rs = stmt.executeQuery()){
			while(rs.next()){
				check = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
					
		return check;
	
	}
}
