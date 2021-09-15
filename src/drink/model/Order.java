package drink.model;

import java.io.Serializable;

public class Order implements Serializable{
	
	private int Order_id;
	private String Order_date;
	private String Customer_ID;
	private int Goods_ID;
	private int Goods_buy_price;
	private int buys_quantity;
	public int getOrder_id() {
		return Order_id;
	}
	public void setOrder_id(int order_id) {
		Order_id = order_id;
	}
	public String getOrder_date() {
		return Order_date;
	}
	public void setOrder_date(String order_date) {
		Order_date = order_date;
	}
	public String getCustomer_ID() {
		return Customer_ID;
	}
	public void setCustomer_ID(String customer_ID) {
		Customer_ID = customer_ID;
	}
	public int getGoods_ID() {
		return Goods_ID;
	}
	public void setGoods_ID(int goods_ID) {
		Goods_ID = goods_ID;
	}
	public int getGoods_buy_price() {
		return Goods_buy_price;
	}
	public void setGoods_buy_price(int goods_buy_price) {
		Goods_buy_price = goods_buy_price;
	}
	public int getBuys_quantity() {
		return buys_quantity;
	}
	public void setBuys_quantity(int buys_quantity) {
		this.buys_quantity = buys_quantity;
	}
	
	
}
