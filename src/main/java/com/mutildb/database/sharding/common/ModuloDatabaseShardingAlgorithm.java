package com.mutildb.database.sharding.common;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;
/**
 * 分库策略
 * @author liukun
 *
 */
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Integer> {
	private static Logger log = LoggerFactory.getLogger(ModuloDatabaseShardingAlgorithm.class);
	/**
	 * availableTargetNames 数据库的名称集合
	 * shardingValue 分库分表的依据值
	 */
	@Override
	public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Integer> shardingValue) {
		for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {//数据库的后缀以分裤策略字段与2的余数相等
                log.info("ModuloDatabaseShardingAlgorithm shardingValue.value={}: dataSourceNames={} " , shardingValue.getValue(),each);
                return each;
            }
        }
        log.warn("根据shardingValue={}找不到对应的数据源",shardingValue);
        throw new IllegalArgumentException();
	}

	@Override
	public Collection<String> doInSharding(Collection<String> availableTargetNames,
			ShardingValue<Integer> shardingValue) {
		//存储 数据库名称
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
		//验证分库分表的正确性
		for (Integer value : shardingValue.getValues()) {
			for(String dataSourceName : availableTargetNames) {
				if(dataSourceName.endsWith(value % 2 + "")) {
					result.add(dataSourceName);
				}
			}
			
		}
		return result;
	}

	@Override
	public Collection<String> doBetweenSharding(Collection<String> availableTargetNames,
			ShardingValue<Integer> shardingValue) {
		Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : availableTargetNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
	}

}
