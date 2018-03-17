package com.pzybrick.learnblockchain.supplychain.database;

public class SupplierVo {
	private String supplierUuid;
	private String dunsNumber;
	private String supplierName;
	private String supplierCategory;
	private String supplierSubCategory;
	private String stateProvince;
	private String country;
	private String encodedPublicKey;
	
	public String getSupplierUuid() {
		return supplierUuid;
	}
	public String getDunsNumber() {
		return dunsNumber;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplierCategory() {
		return supplierCategory;
	}
	public String getSupplierSubCategory() {
		return supplierSubCategory;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public String getCountry() {
		return country;
	}
	public SupplierVo setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
		return this;
	}
	public SupplierVo setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
		return this;
	}
	public SupplierVo setSupplierName(String supplierName) {
		this.supplierName = supplierName;
		return this;
	}
	public SupplierVo setSupplierCategory(String supplierCategory) {
		this.supplierCategory = supplierCategory;
		return this;
	}
	public SupplierVo setSupplierSubCategory(String supplierSubCategory) {
		this.supplierSubCategory = supplierSubCategory;
		return this;
	}
	public SupplierVo setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
		return this;
	}
	public SupplierVo setCountry(String country) {
		this.country = country;
		return this;
	}
	@Override
	public String toString() {
		return "SupplierVo [supplierUuid=" + supplierUuid + ", dunsNumber=" + dunsNumber + ", supplierName="
				+ supplierName + ", supplierCategory=" + supplierCategory + ", supplierSubCategory="
				+ supplierSubCategory + ", stateProvince=" + stateProvince + ", country=" + country + "]";
	}
	public String getEncodedPublicKey() {
		return encodedPublicKey;
	}
	public SupplierVo setEncodedPublicKey(String encodedPublicKey) {
		this.encodedPublicKey = encodedPublicKey;
		return this;
	}
	
}
