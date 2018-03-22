package com.pzybrick.learnblockchain.supplychain;

import java.util.List;

import com.pzybrick.learnblockchain.supplychain.database.SupplierBlockchainVo;

public class LotCanineNutrition {
	private String lotNumber;
	private List<SupplierBlockchainVo> supplierBlockchains;
	private List<String> ingredientNames;
	
	
	public String getLotNumber() {
		return lotNumber;
	}
	public List<SupplierBlockchainVo> getSupplierBlockchains() {
		return supplierBlockchains;
	}
	public List<String> getIngredientNames() {
		return ingredientNames;
	}
	public LotCanineNutrition setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
		return this;
	}
	public LotCanineNutrition setSupplierBlockchains(List<SupplierBlockchainVo> supplierBlockchains) {
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
