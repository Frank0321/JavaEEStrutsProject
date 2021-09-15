package drink.service;

import java.util.ArrayList;
import java.util.List;

import drink.dao.OrderDao;
import drink.model.Good;
import drink.model.Order;
import drink.model.SaleReport;

public class OrderService {
	
	private static OrderService orderService = new OrderService();
	
	private OrderService(){}
	
	public static OrderService getInstance(){
		return orderService;
	}
	
	private OrderDao orderDao = OrderDao.getInstance();
	
	//新增多筆資料到 order DB
	public boolean updateOrdersService (List<Order> orders){
		return orderDao.createOrdersSQL(orders);
	}
	
	
	//將 List<Good> 轉換為 List<Order>
	public List<Order> GoodsToOrders(List<Good> Goods, String Customer_ID){
		List<Order> orders = new ArrayList<Order>();
		for (Good good : Goods){
			Order order = new Order();
			order.setCustomer_ID(Customer_ID);
			order.setGoods_ID(good.getGoodsID());
			order.setGoods_buy_price(good.getGoodsPrice());
			order.setBuys_quantity(good.getGoodsQuantity());
			orders.add(order);
		}
		return orders;
	}
	
	public List<SaleReport> HistoryOrder (String StartDate, String EndDate){
		return orderDao.historyOrder(StartDate, EndDate); 
	}
}
