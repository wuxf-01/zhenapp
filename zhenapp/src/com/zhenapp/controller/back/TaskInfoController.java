package com.zhenapp.controller.back;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhenapp.po.Custom.TTaskInfoCustom;
import com.zhenapp.service.TaskInfoService;

@Controller
@RequestMapping(value="/task")
public class TaskInfoController {
	
	@Autowired
	private TaskInfoService taskInfoService;

	/*
	 * 查询任务订单信息
	 */
	@RequestMapping(value="/findTaskBypage")
	public @ResponseBody ModelMap findTaskBypage(Integer page, Integer rows,String taskid,String datefrom,String dateto) throws Exception{
		ModelMap map=new ModelMap();
		HashMap<String,Object> pagemap=new HashMap<String,Object>();
		datefrom=datefrom!=null?datefrom.replace("-", ""):"";
		dateto=dateto!=null?dateto.replace("-", ""):"";
		pagemap.put("taskid", taskid);
		pagemap.put("datefrom", datefrom);
		pagemap.put("dateto", dateto);
		if (page == null || page == null) {
			pagemap.put("page", 0);
			pagemap.put("rows", 10);
		} else {
			pagemap.put("page", page-1);
			pagemap.put("rows", rows);
		}
		List<TTaskInfoCustom> tTaskInfoCustomlist=taskInfoService.findTaskBypage(pagemap);
		List<TTaskInfoCustom> tTaskInfoCustomAlllist=taskInfoService.findAllTaskBypage(pagemap);
		
		map.put("total",tTaskInfoCustomAlllist.size());
		map.put("rows", tTaskInfoCustomlist);
		
		return map;
	}
	
	/*
	 * 删除任务订单信息
	 */
	@RequestMapping(value="/deleteTaskBypk")
	public @ResponseBody String deleteTaskBypk(String pks) throws Exception{
		taskInfoService.deleteTaskBypk(pks);
		return "removsuccess";
	}
	/*
	 * 发布任务 新增订单信息
	 */
	@RequestMapping(value="/insertTaskInfo")
	public @ResponseBody ModelMap insertTaskInfo(HttpServletRequest request, TTaskInfoCustom tTaskInfoCustom,String taskkeywords) throws Exception{
		ModelMap map=new ModelMap();
		String [] taskkeywordarr=taskkeywords.split("====");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		int counts = 0;
		HttpSession session=request.getSession();
		for (int i = 0; i < taskkeywordarr.length; i++) {
			tTaskInfoCustom.setTaskid(UUID.randomUUID().toString());
			tTaskInfoCustom.setTaskkeyword(taskkeywordarr[i]);
			tTaskInfoCustom.setTaskstate("待分配");
			tTaskInfoCustom.setCreatetime(sdf.format(new Date()));
			tTaskInfoCustom.setUpdatetime(sdf.format(new Date()));
			tTaskInfoCustom.setCreateuser(session.getAttribute("usernick").toString());
			tTaskInfoCustom.setUpdateuser(session.getAttribute("usernick").toString());
			int count=taskInfoService.insertTaskInfo(tTaskInfoCustom);
			counts = counts + count;
		}
		System.out.println("发布"+counts+"新任务成功");
		map.put("data", "insertsuccess");
		return map;
	}
	
}