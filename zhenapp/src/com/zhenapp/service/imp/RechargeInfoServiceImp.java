package com.zhenapp.service.imp;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhenapp.mapper.Custom.TRechargeInfoCustomMapper;
import com.zhenapp.po.Custom.TRechargeInfoCustom;
import com.zhenapp.po.Vo.TRechargeInfoVo;
import com.zhenapp.service.RechargeInfoService;

@Service
public class RechargeInfoServiceImp implements RechargeInfoService{
	@Autowired
	private TRechargeInfoCustomMapper tRechargeInfoCustomMapper;
	
	@Override
	public List<TRechargeInfoCustom> findRechargeinfoByUser(HashMap<String, Object> hashmap)
			throws Exception {
		// TODO Auto-generated method stub
		return tRechargeInfoCustomMapper.findRechargeinfoByUser(hashmap);
	}

	@Override
	public int insertRechargeinfo(TRechargeInfoVo tRechargeInfoVo)
			throws Exception {
		// TODO Auto-generated method stub
		return tRechargeInfoCustomMapper.insertRechargeinfo(tRechargeInfoVo);
	}

	@Override
	public int deleteRechargeinfoByid(String rechargeids) throws Exception {
		// TODO Auto-generated method stub
		return tRechargeInfoCustomMapper.deleteRechargeinfoByid(rechargeids);
	}

	@Override
	public List<TRechargeInfoCustom> findRechargeinfoByUserAndpage(
			HashMap<String, Object> hashmap) throws Exception {
		// TODO Auto-generated method stub
		return tRechargeInfoCustomMapper.findRechargeinfoByUserAndpage(hashmap);
	}

}