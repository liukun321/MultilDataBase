package com.mutildb.database.sharding.spring.bean;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author liukun
 *
 */
@Getter
@Setter

public class ShardingOrder {
	
	private int userId;
	
	private int orderId;
	
	private String status;
	
	public int getUserId() {
		return userId;
	}

	public int getOrderId() {
		return orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ShardingOrder{" + "userId=" + userId + ", orderId=" + orderId + ", status='" + status + '\'' + '}';
	}
}
