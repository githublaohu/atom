package com.lampup.atom.service.reasoning.consumers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import com.reasoing.atom.service.reasoning.entity.ReasoningRequest;

public class ReasoningController {

	@Autowired
	private RedisTemplate<String, String> redisTempleate;
	
	public List<Object> recommend(@RequestBody ReasoningRequest reasoningRequest){
		// 通过场景id，获得任务编排 
		// 请求用户相关信息
		redisTempleate.opsForHash().entries(reasoningRequest.getCustomerName() + "-" + reasoningRequest.getUnique());
		// 请求milvus
		
		// 请求热品
		redisTempleate.opsForHash().entries(reasoningRequest.getCustomerName() + "-" + "hot");
		// 请求商品
		redisTempleate.opsForHash().multiGet(reasoningRequest.getCustomerName() + "-goods", null);
		// 请求推理
		
		// 新品
		redisTempleate.opsForHash().entries(reasoningRequest.getCustomerName() + "-new");
		
		// 排序
		
		
		return null;
	}
}
