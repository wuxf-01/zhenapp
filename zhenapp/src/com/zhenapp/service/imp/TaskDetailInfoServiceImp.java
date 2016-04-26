package com.zhenapp.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhenapp.mapper.Custom.TTaskDetailinfoCustomMapper;
import com.zhenapp.po.Custom.TTaskDetailInfoCustom;
import com.zhenapp.service.TaskDetailInfoService;

@Service
public class TaskDetailInfoServiceImp implements TaskDetailInfoService {

	@Autowired
	private TTaskDetailinfoCustomMapper tTaskDetailinfoCustomMapper;
	
	@Override
	public int insertDetailinfo(TTaskDetailInfoCustom tTaskDetailInfoCustom)
			throws Exception {
		return tTaskDetailinfoCustomMapper.insertDetailinfo(tTaskDetailInfoCustom);
	}

}