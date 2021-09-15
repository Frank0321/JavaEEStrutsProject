package drink.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SaleReport implements Serializable{
	
	private int order_ID;
	private String customer_Name;
	private Timestamp order_Date;
	private String goods_Name;
	private int buy_Quantity;
	private int goods_Buy_Price;
	private int cost;
	
	public int getOrder_ID() {
		return order_ID;
	}

	public void setOrder_ID(int order_ID) {
		this.order_ID = order_ID;
	}

	public String getCustomer_Name() {
		return customer_Name;
	}

	public void setCustomer_Name(String customer_Name) {
		this.customer_Name = customer_Name;
	}

	public Timestamp getOrder_Date() {
		return order_Date;
	}

	public void setOrder_Date(Timestamp order_Date) {
		this.order_Date = order_Date;
	}

	public String getGoods_Name() {
		return goods_Name;
	}

	public void setGoods_Name(String goods_Name) {
		this.goods_Name = goods_Name;
	}

	public int getBuy_Quantity() {
		return buy_Quantity;
	}

	public void setBuy_Quantity(int buy_Quantity) {
		this.buy_Quantity = buy_Quantity;
	}

	public int getGoods_Buy_Price() {
		return goods_Buy_Price;
	}

	public void setGoods_Buy_Price(int goods_Buy_Price) {
		this.goods_Buy_Price = goods_Buy_Price;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
//	@Override
//	public String toString() {
//		return "SaleReport ["
//				+ "Order_ID=" + Order_ID 
//				+ ", Customer_Name="+ Customer_Name 
//				+ ", Order_Date=" + new SimpleDateFormat("yyyy/MM/dd").format(Order_Date)
//				+ ", Goods_Name=" + Goods_Name 
//				+ ", Buy_Quantity="+ Buy_Quantity 
//				+ ", Goods_Buy_Price=" + Goods_Buy_Price
//				+ ", Cost=" + Cost 
//				+ "]";
//	}
	
	

}
