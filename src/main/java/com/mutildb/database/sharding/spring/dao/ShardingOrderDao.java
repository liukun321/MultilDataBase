package com.mutildb.database.sharding.spring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mutildb.database.sharding.spring.bean.ShardingOrder;

public interface ShardingOrderDao extends JpaRepository<ShardingOrder, Integer>{

	List<ShardingOrder> findByUserIdAndOrderId(int userId, int orderId);

}
