package com.manage.project.system.supply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.supply.domain.SupplyVorder;
import com.manage.project.system.supply.mapper.SupplyVorderMapper;
import com.manage.project.system.supply.vo.SupplyOrderTmpVo;

@Service
public class SupplyVorderServiceImpl implements ISupplyVorderService {

	@Autowired
	private SupplyVorderMapper SupplyVorderMapper;
	
	@Override
	public List<SupplyVorder> selectSupplyVorderList(SupplyVorder supplyVorder) {
		return SupplyVorderMapper.selectSupplyVorderList(supplyVorder);
	}

	@Override
	public List<SupplyOrderTmpVo> selectSupplyVorderBySiteId(String siteId, String supplyFTime, String supplierId) {
		return SupplyVorderMapper.selectSupplyVorderBySiteId(siteId, supplyFTime, supplierId);
	}

}
