package com.mutildb.database.sharding.common;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
/**
 * 分表策略
 * 单分片
 * @author liukun
 *
 */
public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {
	private static Logger log = LoggerFactory.getLogger(ModuloTableShardingAlgorithm.class);
	/**
	 * select * from t_order from t_order where order_id = 11
     *          └── SELECT *  FROM t_order_1 WHERE order_id = 11
     *  select * from t_order from t_order where order_id = 44
     *          └── SELECT *  FROM t_order_0 WHERE order_id = 44
	 */
	@Override
	public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
		for (String dataName : availableTargetNames) {
			if(dataName.endsWith(shardingValue.getValue() % 2 + "")) {
				log.info("ModuloTableShardingAlgorithm shardingValue.value={}: dataSourceNames={} " , shardingValue.getValue(), dataName);
				return dataName;
			}
		}
		log.warn("根据shardingValue={}找不到对应的表", shardingValue);
        throw new UnsupportedOperationException();
	}
	/**
	 * select * from t_order from t_order where order_id in (11,44)
     *          ├── SELECT *  FROM t_order_0 WHERE order_id IN (11,44)
     *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,44)
     *  select * from t_order from t_order where order_id in (11,13,15)
     *          └── SELECT *  FROM t_order_1 WHERE order_id IN (11,13,15)
     *  select * from t_order from t_order where order_id in (22,24,26)
     *          └──SELECT *  FROM t_order_0 WHERE order_id IN (22,24,26)
	 */
	@Override
	public Collection<String> doInSharding(Collection<String> availableTargetNames,
			ShardingValue<Integer> shardingValue) {
		
		Collection<String> result = new LinkedHashSet<>();
		for(Integer value : shardingValue.getValues()) {
			for(String dataSourceName : availableTargetNames) {
				if(dataSourceName.endsWith(value % 2 + "")) {
					result.add(dataSourceName);
				}
			}
		}
		return result;
	}
	/**
	 * select * from t_order from t_order where order_id between 10 and 20
     *          ├── SELECT *  FROM t_order_0 WHERE order_id BETWEEN 10 AND 20
     *          └── SELECT *  FROM t_order_1 WHERE order_id BETWEEN 10 AND 20
	 */
	@Override
	public Collection<String> doBetweenSharding(Collection<String> availableTargetNames,
			ShardingValue<Integer> shardingValue) {
		Collection<String> result = new LinkedHashSet<>();
		
		Range<Integer> rang = shardingValue.getValueRange();
		
		for(Integer i = rang.lowerEndpoint(); i <= rang.upperEndpoint(); i++) {
			for(String dataSourceName : availableTargetNames) {
				if(dataSourceName.endsWith(i % 2 + "")) {
					result.add(dataSourceName);
				}
			}
		}
		return result;
	}

}
