package com.pzybrick.learnblockchain.supplychain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class SupplierTransaction {
	private String supplierTransationUuid;
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
	
	
	public String toJson( SupplierTransaction supplierTransaction) throws Exception {
		return BlockchainUtils.objectMapper.writeValueAsString(supplierTransaction);
	}
	
	
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
		return "SupplierTransaction [supplierTransationUuid=" + supplierTransationUuid + ", dunsNumber=" + dunsNumber + ", supplierName=" + supplierName
				+ ", supplierCategory=" + supplierCategory + ", supplierSubCategory=" + supplierSubCategory + ", supplierLotNumber=" + supplierLotNumber
				+ ", itemNumber=" + itemNumber + ", description=" + description + ", qty=" + qty + ", units=" + units + ", shippedDateIso8601="
				+ shippedDateIso8601 + ", rcvdDateIso8601=" + rcvdDateIso8601 + "]";
	}


	public String getSupplierTransationUuid() {
		return supplierTransationUuid;
	}


	public SupplierTransaction setSupplierTransationUuid(String supplierTransationUuid) {
		this.supplierTransationUuid = supplierTransationUuid;
		return this;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dunsNumber == null) ? 0 : dunsNumber.hashCode());
		result = prime * result + ((itemNumber == null) ? 0 : itemNumber.hashCode());
		result = prime * result + qty;
		result = prime * result + ((rcvdDateIso8601 == null) ? 0 : rcvdDateIso8601.hashCode());
		result = prime * result + ((shippedDateIso8601 == null) ? 0 : shippedDateIso8601.hashCode());
		result = prime * result + ((supplierCategory == null) ? 0 : supplierCategory.hashCode());
		result = prime * result + ((supplierLotNumber == null) ? 0 : supplierLotNumber.hashCode());
		result = prime * result + ((supplierName == null) ? 0 : supplierName.hashCode());
		result = prime * result + ((supplierSubCategory == null) ? 0 : supplierSubCategory.hashCode());
		result = prime * result + ((supplierTransationUuid == null) ? 0 : supplierTransationUuid.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplierTransaction other = (SupplierTransaction) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dunsNumber == null) {
			if (other.dunsNumber != null)
				return false;
		} else if (!dunsNumber.equals(other.dunsNumber))
			return false;
		if (itemNumber == null) {
			if (other.itemNumber != null)
				return false;
		} else if (!itemNumber.equals(other.itemNumber))
			return false;
		if (qty != other.qty)
			return false;
		if (rcvdDateIso8601 == null) {
			if (other.rcvdDateIso8601 != null)
				return false;
		} else if (!rcvdDateIso8601.equals(other.rcvdDateIso8601))
			return false;
		if (shippedDateIso8601 == null) {
			if (other.shippedDateIso8601 != null)
				return false;
		} else if (!shippedDateIso8601.equals(other.shippedDateIso8601))
			return false;
		if (supplierCategory == null) {
			if (other.supplierCategory != null)
				return false;
		} else if (!supplierCategory.equals(other.supplierCategory))
			return false;
		if (supplierLotNumber == null) {
			if (other.supplierLotNumber != null)
				return false;
		} else if (!supplierLotNumber.equals(other.supplierLotNumber))
			return false;
		if (supplierName == null) {
			if (other.supplierName != null)
				return false;
		} else if (!supplierName.equals(other.supplierName))
			return false;
		if (supplierSubCategory == null) {
			if (other.supplierSubCategory != null)
				return false;
		} else if (!supplierSubCategory.equals(other.supplierSubCategory))
			return false;
		if (supplierTransationUuid == null) {
			if (other.supplierTransationUuid != null)
				return false;
		} else if (!supplierTransationUuid.equals(other.supplierTransationUuid))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}
	
}
