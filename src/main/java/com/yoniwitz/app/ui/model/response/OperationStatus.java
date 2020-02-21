package com.yoniwitz.app.ui.model.response;

public class OperationStatus {
	private String operationName;
	private String operationStatus;

	public OperationStatus() {
	}

	public OperationStatus(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}

}
