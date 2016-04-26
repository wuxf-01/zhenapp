package com.zhenapp.service.imp;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhenapp.mapper.TTaskInfoMapper;
import com.zhenapp.mapper.Custom.TTaskInfoCustomMapper;
import com.zhenapp.po.Custom.TTaskInfoCustom;
import com.zhenapp.service.TaskInfoService;
@Service
public class TaskInfoServiceImp implements TaskInfoService {
	@Autowired
	private TTaskInfoMapper tTaskInfoMapper; 
	@Autowired
	private TTaskInfoCustomMapper tTaskInfoCustomMapper;
	
	@Override
	public int insertTaskInfo(TTaskInfoCustom tTaskInfoCustom) throws Exception {
		return tTaskInfoCustomMapper.insertTaskInfo(tTaskInfoCustom);
	}

	@Override
	public List<TTaskInfoCustom> findTaskBypage(HashMap<String, Object> hashmap)
			throws Exception {
		return tTaskInfoCustomMapper.findTaskBypage(hashmap);
	}

	@Override
	public List<TTaskInfoCustom> findAllTaskBypage(
			HashMap<String, Object> hashmap) throws Exception {
		return tTaskInfoCustomMapper.findAllTaskBypage(hashmap);
	}

	@Override
	public void deleteTaskBypk(String taskpk) throws Exception {
		tTaskInfoCustomMapper.deleteTaskBypk(taskpk);
	}

	@Override
	public int insertTaskByapi(TTaskInfoCustom tTaskInfoCustom)
			throws Exception {
		return tTaskInfoCustomMapper.insertTaskByapi(tTaskInfoCustom);
	}

	@Override
	public List<TTaskInfoCustom> findTaskallocation(
			HashMap<String, Object> hashmap) throws Exception {
		// TODO Auto-generated method stub
		return tTaskInfoCustomMapper.findTaskallocation(hashmap);
	}

	@Override
	public List<TTaskInfoCustom> findIsFirst(String taskkeynum) throws Exception {
		// TODO Auto-generated method stub
		return tTaskInfoCustomMapper.findIsFirst(taskkeynum);
	}

}