package com.mutildb.database.sharding.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mutildb.database.sharding.spring.bean.ShardingOrder;
import com.mutildb.database.sharding.spring.dao.ShardingOrderDao;

@Service
@Transactional(readOnly = true)
public class ShardingOrderService {
	@Autowired
	private ShardingOrderDao shardingOrderDao;
	
	public List<ShardingOrder> select(ShardingOrder order) {
		return shardingOrderDao.findByUserIdAndOrderId(order.getUserId(), order.getOrderId());
	}
	
	@Transactional(readOnly = false)
	public void insert(ShardingOrder order) {
		shardingOrderDao.save(order);
	}
	@Transactional(readOnly = false)
	public void delete(ShardingOrder order) {
		shardingOrderDao.delete(order);
	}

}
