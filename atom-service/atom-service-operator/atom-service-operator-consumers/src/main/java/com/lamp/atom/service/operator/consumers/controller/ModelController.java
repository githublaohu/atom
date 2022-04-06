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
package com.lamp.atom.service.operator.consumers.controller;

import com.lamp.atom.service.operator.entity.ModelEntity;
import com.lamp.atom.service.operator.service.ModelService;
import com.lamp.atom.service.operator.consumers.utils.ResultObjectEnums;
import com.lamp.decoration.core.result.ResultObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@RequestMapping("/model")
@RestController("modelController")
@Api(tags = { "模型操作接口" })
public class ModelController {

	@Reference
	private ModelService modelService;

	/**
	 * 添加模型
	 * 
	 * @param modelEntity
	 */
	@PostMapping("/insertModel")
	@ApiOperation(value = "添加模型")
	public ResultObject<String> insertModel(@RequestBody ModelEntity modelEntity) {
		// 字段判空
		if (Objects.isNull(modelEntity.getSpaceId())) {
			log.info("参数校验失败 {}", modelEntity);
			return ResultObjectEnums.CHECK_PARAMETERS_FAIL.getResultObject();
		}
		try {
			modelService.insertModelEntity(modelEntity);
		} catch (Exception e) {
			log.warn("模型插入失败 {}", e);
			return ResultObjectEnums.FAIL.getResultObject();
		}
		return ResultObjectEnums.SUCCESS.getResultObject();
	}

	/**
	 * 修改模型
	 * 
	 * @param modelEntity
	 * @return
	 */
	@PostMapping("/updateModel")
	@ApiOperation(value = "修改模型")
	@ApiImplicitParams({
			@ApiImplicitParam(name="id",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long"),
			@ApiImplicitParam(name="deleteFlag",dataTypeClass = java.lang.Long.class,paramType="body" ,dataType = "Long")
	})
	public ResultObject<String> updateModel(@ApiIgnore @RequestBody ModelEntity modelEntity) {
		try {
			modelService.updateModelEntity(modelEntity);
		} catch (Exception e) {
			log.warn("模型修改失败 {}", e);
			return ResultObjectEnums.FAIL.getResultObject();
		}
		return ResultObjectEnums.SUCCESS.getResultObject();
	}

	/**
	 * 模糊查询多个模型
	 * 
	 * @param params
	 * @return
	 */
	@PostMapping("/queryModelsByKeyword")
	@ApiOperation(value = "模糊查询多个模型")
	public List<ModelEntity> queryModelsByKeyword(@RequestBody HashMap<String, String> params) {
		try {
			return modelService.queryModelEntitysByKeyword(params.get("keyword"));
		} catch (Exception e) {
			log.warn("模型查询失败 {}", e);
			return null;
		}
	}

	/**
	 * 查询多个模型
	 *
	 * @param modelEntity
	 * @return
	 */
	@PostMapping("/queryModels")
	@ApiOperation(value = "查询多个模型")
	public List<ModelEntity> queryModels(@RequestBody(required = false) ModelEntity modelEntity) {
		try {
			return modelService.queryModelEntitys(modelEntity);
		} catch (Exception e) {
			log.warn("模型查询失败 {}", e);
			return null;
		}
	}

	/**
	 * 查询单个模型
	 * 
	 * @param modelEntity
	 * @return
	 */
	@PostMapping("/queryModel")
	@ApiOperation(value = "查询单个模型")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", paramType = "body" ,dataType = "Long", dataTypeClass = java.lang.Long.class, defaultValue = "1")
	})
	public ModelEntity queryModel(@ApiIgnore @RequestBody ModelEntity modelEntity) {
		try {
			ModelEntity modelEntity1 = modelService.queryModelEntity(modelEntity);
			return modelEntity1;
		} catch (Exception e) {
			log.warn("模型查询失败 {}", e);
			return null;
		}
	}
}
