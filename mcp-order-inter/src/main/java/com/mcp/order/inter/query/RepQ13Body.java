package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Promotion;

import java.util.List;

public class RepQ13Body extends RepBody {
	
	private List<Promotion> promotions;

	public List<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}
}
