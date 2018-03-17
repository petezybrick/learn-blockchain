package com.pzybrick.learnblockchain.supplychain;

import java.util.List;

public class LotCanineNutrition {
	private String lotNumber;
	private List<SupplierBlockchain> supplierBlockchains;
	private List<String> ingredientNames;
	
	
	public String getLotNumber() {
		return lotNumber;
	}
	public List<SupplierBlockchain> getSupplierBlockchains() {
		return supplierBlockchains;
	}
	public List<String> getIngredientNames() {
		return ingredientNames;
	}
	public LotCanineNutrition setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
		return this;
	}
	public LotCanineNutrition setSupplierBlockchains(List<SupplierBlockchain> supplierBlockchains) {
		this.supplierBlockchains = supplierBlockchains;
		return this;
	}
	public LotCanineNutrition setIngredientNames(List<String> ingredientNames) {
		this.ingredientNames = ingredientNames;
		return this;
	}
	@Override
	public String toString() {
		return "LotCanineNutrition [lotNumber=" + lotNumber + ", supplierBlockchains=" + supplierBlockchains + ", ingredientNames=" + ingredientNames + "]";
	}
}
