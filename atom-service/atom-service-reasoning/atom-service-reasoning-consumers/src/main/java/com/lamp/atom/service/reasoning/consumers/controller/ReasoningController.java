/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.atom.service.reasoning.consumers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import com.lamp.atom.reasoing.service.reasoning.entity.ReasoningRequest;

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
