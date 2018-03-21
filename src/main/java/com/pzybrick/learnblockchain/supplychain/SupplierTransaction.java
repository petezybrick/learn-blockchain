package com.pzybrick.learnblockchain.supplychain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class SupplierTransaction {
	private String dunsNumber;
	private String supplierName;
	private String supplierCategory;
	private String supplierSubCategory;
	private String supplierLotNumber;
	private String itemNumber;
	private String description;
	private int qty;
	private String units;
	private String shippedDateIso8601;
	private String rcvdDateIso8601;
	
	
	public String getDunsNumber() {
		return dunsNumber;
	}
	public String getSupplierCategory() {
		return supplierCategory;
	}
	public String getSupplierSubCategory() {
		return supplierSubCategory;
	}
	public String getSupplierLotNumber() {
		return supplierLotNumber;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public String getDescription() {
		return description;
	}
	public int getQty() {
		return qty;
	}
	public String getUnits() {
		return units;
	}
	public String getShippedDateIso8601() {
		return shippedDateIso8601;
	}
	public SupplierTransaction setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
		return this;
	}
	public SupplierTransaction setSupplierCategory(String supplierCategory) {
		this.supplierCategory = supplierCategory;
		return this;
	}
	public SupplierTransaction setSupplierSubCategory(String supplierSubCategory) {
		this.supplierSubCategory = supplierSubCategory;
		return this;
	}
	public SupplierTransaction setSupplierLotNumber(String supplierLotNumber) {
		this.supplierLotNumber = supplierLotNumber;
		return this;
	}
	public SupplierTransaction setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
		return this;
	}
	public SupplierTransaction setDescription(String description) {
		this.description = description;
		return this;
	}
	public SupplierTransaction setQty(int qty) {
		this.qty = qty;
		return this;
	}
	public SupplierTransaction setUnits(String units) {
		this.units = units;
		return this;
	}
	public SupplierTransaction setShippedDateIso8601(String shippedDateIso8601) {
		this.shippedDateIso8601 = shippedDateIso8601;
		return this;
	}
	public String getRcvdDateIso8601() {
		return rcvdDateIso8601;
	}
	public SupplierTransaction setRcvdDateIso8601(String rcvdDateIso8601) {
		this.rcvdDateIso8601 = rcvdDateIso8601;
		return this;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public SupplierTransaction setSupplierName(String supplierName) {
		this.supplierName = supplierName;
		return this;
	}
	@Override
	public String toString() {
		return "SupplierTransaction [dunsNumber=" + dunsNumber + ", supplierName=" + supplierName
				+ ", supplierCategory=" + supplierCategory + ", supplierSubCategory=" + supplierSubCategory
				+ ", lotNumber=" + supplierLotNumber + ", itemNumber=" + itemNumber + ", description=" + description + ", qty="
				+ qty + ", units=" + units + ", shippedDateIso8601=" + shippedDateIso8601 + ", rcvdDateIso8601="
				+ rcvdDateIso8601 + "]";
	}
	
}
