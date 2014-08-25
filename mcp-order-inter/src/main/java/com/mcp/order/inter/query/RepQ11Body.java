package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Game;

import java.util.List;


public class RepQ11Body extends RepBody {
	
	private List<Game> games;

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
}
