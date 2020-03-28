package com.nikijulHypixel.bazaar;

public enum AllCategories {
	FARMING(1),
	ANIMALS(2),
	MINING(3),
	COMBAT(4),
	WOOD(5),
	FISHING(6),
	HYPIXEL(7);	
	
	
	private int buttonID;
	
	private AllCategories(int buttonID) {
		this.buttonID = buttonID;
	}
	
	public int getButtonID() {
		return buttonID;
	}
}
