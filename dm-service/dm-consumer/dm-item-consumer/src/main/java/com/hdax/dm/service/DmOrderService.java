package com.hdax.dm.service;
import com.hdax.dm.client.ItemClient;
import com.hdax.dm.entity.item.DmItem;
import com.hdax.dm.vo.CommoResponse;
import com.hdax.dm.vo.ProgramMoney;
import com.hdax.dm.vo.VoUtil;



import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @description:
 * @author: 5G狼狗
 * @create: 2020-11-13 21:37
 **/
@Service
public class DmOrderService {

	@Resource
	private ItemClient dmItemClient;


	public CommoResponse<Object> submitOrder(Map<String,String> map){
		Long itemId =  Long.parseLong(map.get("itemId"));
		String seatPositions = map.get("seatPositions");
		ProgramMoney programMoney=new ProgramMoney();
		String[] seatArrays=seatPositions.split(",");
		DmItem item = dmItemClient.findItemById(itemId);
		programMoney.setItemName(item.getItemName());
		programMoney.setSeatCount(seatArrays.length);
		programMoney.setSeatNames(seatPositions);
		programMoney.setTotalAMount(item.getMaxPrice()*seatArrays.length);
		return VoUtil.returnSuccess("计算价格成功",programMoney);
	}

}
