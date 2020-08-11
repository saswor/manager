package com.manage.project.system.vending.vo;

import java.util.ArrayList;
import java.util.List;

import com.manage.project.system.vending.domain.VendingModel;

/**
 * 售卖机新增，生成货道页面时，用于前端查询后的显示
 * @author xufeng
 *
 */
public class VendingModelVo extends VendingModel {
	
	private List<row> rows;
	
	public List<row> getRows() {
		rows = new ArrayList<row>();
		if (super.getRow() == null || super.getRow() == 0) {
			return rows;
		}
		if (super.getCol() == null || super.getCol() == 0) {
			return rows;
		}
		int index = 1;
		for (int i = 0; i < super.getRow(); i++) {
			row r = new row();
			List<col> cols = new ArrayList<col>();
			for (int j = 0; j < super.getCol(); j++) {
				col c = new col();
				c.setLaneId(String.valueOf(index));
				c.setName("货道"+index);
				c.setCol(j+1);
				c.setRow(i+1);
				lanep lanep = new lanep();
				lanep.setLaneSId(index);
				c.setLanep(lanep);
				index++;
				cols.add(c);
			}
			r.setCols(cols);
			rows.add(r);
		}
		return rows;
	}

	class row {
		private List<col> cols;

		public List<col> getCols() {
			return cols;
		}
		public void setCols(List<col> cols) {
			this.cols = cols;
		}
	}
	
	class col{
		private String laneId;
		private String name;
		private String state ="1";
		private int row;
		private int col;
		private int arrange = 1;
		private lanep lanep;
		
		public lanep getLanep() {
			return lanep;
		}
		public void setLanep(lanep lanep) {
			this.lanep = lanep;
		}
		public int getArrange() {
			return arrange;
		}
		public void setArrange(int arrange) {
			this.arrange = arrange;
		}
		public String getLaneId() {
			return laneId;
		}
		public void setLaneId(String laneId) {
			this.laneId = laneId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
	}
	
	class lanep{
		private int laneSId = 1;
		private String productId = "";
		private String productPic="";
		private String salePrice = "";
		private String capacity = "";
		private String warnCap = "";
		private String laneSate = "1";
		public int getLaneSId() {
			return laneSId;
		}
		public void setLaneSId(int laneSId) {
			this.laneSId = laneSId;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public String getProductPic() {
			return productPic;
		}
		public void setProductPic(String productPic) {
			this.productPic = productPic;
		}
		public String getSalePrice() {
			return salePrice;
		}
		public void setSalePrice(String salePrice) {
			this.salePrice = salePrice;
		}
		public String getCapacity() {
			return capacity;
		}
		public void setCapacity(String capacity) {
			this.capacity = capacity;
		}
		public String getWarnCap() {
			return warnCap;
		}
		public void setWarnCap(String warnCap) {
			this.warnCap = warnCap;
		}
		public String getLaneSate() {
			return laneSate;
		}
		public void setLaneSate(String laneSate) {
			this.laneSate = laneSate;
		}
		
		
	}
}
