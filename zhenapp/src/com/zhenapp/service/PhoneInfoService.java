package com.zhenapp.service;

import java.util.HashMap;
import java.util.List;

import com.zhenapp.po.Custom.TPhoneInfoCustom;

public interface PhoneInfoService {
	/*
	 * 查询当前执行任务的手机中任务状态未完成
	 * 并且手机状态正常
	 * 并且当天没有执行该宝贝id的
	 * 手机编号
	 */
	public TPhoneInfoCustom findTPhoneByTask(HashMap<String, Object> hashmap) throws Exception;
	
	/*
	 * 查询当前执行任务的手机中任务状态未完成
	 * 并且手机状态正常
	 * 并且当天没有执行该宝贝id的
	 * 手机编号
	 */
	public List<TPhoneInfoCustom> findAllPhoneBykeynum(String keynum) throws Exception;
	
}