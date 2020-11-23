package com.hdax.dm.controller;

import com.hdax.dm.service.DmOrderService;
import com.hdax.dm.vo.CommoResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description:
 * @author: 5G狼狗
 * @create: 2020-11-13 21:36
 **/
@RestController
public class DmOrderController {
	@Resource
	private DmOrderService dmOrderService;

//	@PostMapping("/submitorder")
//	public CommoResponse<Object> submitOrder(@RequestBody Map<String,String> map){
//		return dmOrderService.submitOrder(map);
//	}
//
//
//	@PostMapping("/confirmpay")
//	public CommoResponse<Object> confirmpay(@RequestBody Map<String,String> map){
//		return dmOrderService.submitOrder(map);
//	}


}
