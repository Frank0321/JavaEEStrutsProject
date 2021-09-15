package drink.model;

import java.io.Serializable;

public class Good implements Serializable{
	
	private int goodsID;
	private String goodsName;
	private int goodsPrice;
	private int goodsQuantity;
	private String goodsImage;
	private boolean status;
	public int getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public int getGoodsQuantity() {
		return goodsQuantity;
	}
	public void setGoodsQuantity(int goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Good ["
				+ "goodsID=" + goodsID 
				+ ", goodsName=" + goodsName
				+ ", goodsPrice=" + goodsPrice 
				+ ", goodsQuantity="+ goodsQuantity 
				+ ", goodsImage=" + goodsImage + 
				", status="+ status 
				+ "]";
	}
	
	
}
