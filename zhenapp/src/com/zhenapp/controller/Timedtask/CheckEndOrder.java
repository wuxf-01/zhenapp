package com.zhenapp.controller.Timedtask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhenapp.po.Custom.MsgInfoCustom;
import com.zhenapp.po.Custom.TPointsInfoCustom;
import com.zhenapp.po.Custom.TPriceInfoCustom;
import com.zhenapp.po.Custom.TTaskDetailInfoFlowCustom;
import com.zhenapp.po.Custom.TTaskInfoCustom;
import com.zhenapp.po.Custom.TUserInfoCustom;
import com.zhenapp.service.DateInfoService;
import com.zhenapp.service.PointsInfoService;
import com.zhenapp.service.PriceInfoService;
import com.zhenapp.service.RechargeInfoService;
import com.zhenapp.service.SysconfInfoService;
import com.zhenapp.service.TaskDetailInfoFlowService;
import com.zhenapp.service.TaskDetailInfoService;
import com.zhenapp.service.TaskInfoService;
import com.zhenapp.service.UserInfoService;
import com.zhenapp.util.StringUtilWxf;
@Controller
public class CheckEndOrder {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	private static Logger logger = Logger.getLogger(CheckEndOrder.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SysconfInfoService sysconfInfoService;
	@Autowired
	private TaskInfoService taskInfoService;
	@Autowired
	private RechargeInfoService rechargeInfoService;
	@Autowired
	private TaskDetailInfoService taskDetailInfoService;
	@Autowired
	private PointsInfoService pointsInfoService;
	@Autowired
	private DateInfoService dateInfoService;
	@Autowired
	private TaskDetailInfoFlowService taskDetailInfoFlowService;
	@Autowired
	private PriceInfoService priceInfoService;
	
	@Value("${secret}")
	private String secret;
	/*
	 * 每一分钟执行一次 查询任务状态为终止中的任务,检查是否所有详情任务都已返回，如果都已处理修改为已终止，结束任务，返回积分
	 */
	@RequestMapping("/api/platform/updateTaskstateByTiming")
	public @ResponseBody ModelMap updateTaskstateByTiming() throws Exception{
		ModelMap map = new ModelMap();
		//查询任务状态为终止中的任务
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("taskstate", "18");
		List<TTaskInfoCustom> tTaskInfoCustomlist = taskInfoService.findTaskInfoByTaskstate(hashmap);
		if(tTaskInfoCustomlist!=null && tTaskInfoCustomlist.size()>0){
			for (int i = 0; i < tTaskInfoCustomlist.size(); i++) {
				TTaskInfoCustom tTaskInfoCustom = tTaskInfoCustomlist.get(i);
				TUserInfoCustom tUserInfoCustom = userInfoService.findUserByuserid(tTaskInfoCustom.getCreateuser());
				TPriceInfoCustom tPriceInfoCustom = priceInfoService.findPriceByAgentid(tUserInfoCustom.getAgentid());
				//查询该任务 执行中的详情任务条数
				hashmap.put("taskstate", "20");
				hashmap.put("taskid", tTaskInfoCustom.getTaskid());
				int tempcount = taskDetailInfoService.findTaskDetailInfoByIdAndTaskstate(hashmap);
				if(tempcount==0){
					hashmap.clear();
					hashmap.put("taskid", tTaskInfoCustom.getTaskid());
					hashmap.put("taskstate", 19);
					hashmap.put("updatetime", sdf.format(new Date()));
					hashmap.put("updateuser", "sys");
					taskInfoService.updateTaskstate(hashmap);
					//hashmap.put("taskstate", "23");
					//taskDetailInfoService.deleteTaskBystate(hashmap);
					hashmap.clear();
					hashmap.put("taskid", tTaskInfoCustom.getTaskid());
					int points = taskDetailInfoService.findPointsByteterminationstate(tTaskInfoCustom.getTaskid());
					//收藏，加购失败需要返回的积分
		        	hashmap.put("taskid", tTaskInfoCustom.getTaskid());
					hashmap.put("taskstate", "22");
					int collectcount = taskDetailInfoService.findcollectioncount(hashmap);
					int shoppingcount = taskDetailInfoService.findshoppingcount(hashmap);
					int pointscollect = collectcount*Integer.parseInt(tPriceInfoCustom.getPricecounts2());
					int pointsshopping = shoppingcount*Integer.parseInt(tPriceInfoCustom.getPricecounts3());
					int pointserror=pointscollect+pointsshopping;
					hashmap.put("taskid", tTaskInfoCustom.getTaskid());
					hashmap.put("taskstate", "21");
					int tempcollectcount = taskDetailInfoService.findcollectioncount(hashmap);
					int tempshoppingcount = taskDetailInfoService.findshoppingcount(hashmap);
					int maxcount=tempcollectcount;
					if(maxcount < tempshoppingcount){
						maxcount = tempshoppingcount;
					}
					//查询完成了多少个流量任务
					int flowpoints=0;
					TTaskDetailInfoFlowCustom tTaskDetailInfoFlowCustom = taskDetailInfoFlowService.findTaskdetailInfo(hashmap);//根据任务id查询出流量详情信息
					HttpClient httpClient = new HttpClient();
					String result="";
			        GetMethod getMethod = new GetMethod("http://liuliangapp.com/api/tasks/"+tTaskDetailInfoFlowCustom.getTaskdetailid()+"/total");
			        getMethod.setRequestHeader("secret", secret);
			        int statusCode =  httpClient.executeMethod(getMethod);
			        if(statusCode == 200) {
			            result = getMethod.getResponseBodyAsString();
			            if(result.indexOf("total")==-1){
			            	result = StringUtilWxf.translat(result);
			            }else{
			            	ObjectMapper obj = new ObjectMapper();
			 	    		MsgInfoCustom msgInfoCustom = obj.readValue(result, MsgInfoCustom.class);
			 	    		result=msgInfoCustom.getTotal()+"";
			 	    		//更新完成数
			 	    		hashmap.put("finishcount", msgInfoCustom.getTotal());
			 	    		taskDetailInfoFlowService.updatefinishcount(hashmap);
			 	    		if(maxcount < Integer.parseInt(msgInfoCustom.getTotal())){
			 	    			maxcount = Integer.parseInt(msgInfoCustom.getTotal());
			 	    		}
			 	    		flowpoints =(tTaskInfoCustom.getFlowcount() - maxcount) * Integer.parseInt(tPriceInfoCustom.getPricecounts1());
			            }
			            map.put("msg", result);
			        }
			        else {
			            map.put("msg", "失败错误码" + statusCode);
			        }
					//添加终止任务所返回的积分
					tUserInfoCustom.setPoints(tUserInfoCustom.getPoints() + points + flowpoints + pointserror);
					tUserInfoCustom.setUpdatetime(sdf.format(new Date()));
					tUserInfoCustom.setUpdateuser(tUserInfoCustom.getUserid());
					userInfoService.updateUserinfoPointByUserid(tUserInfoCustom);
					//添加积分明细记录
					TPointsInfoCustom tPointsInfoCustom =new TPointsInfoCustom();
					tPointsInfoCustom.setCreateuser(tUserInfoCustom.getUserid());
					tPointsInfoCustom.setCreatetime(sdf.format(new Date()));
					tPointsInfoCustom.setUpdatetime(sdf.format(new Date()));
					tPointsInfoCustom.setUpdateuser("sys");
					tPointsInfoCustom.setPointreason("终止任务" + tTaskInfoCustom.getTaskpk() + "返回积分"+(points+flowpoints+pointserror));
					tPointsInfoCustom.setPointsid(UUID.randomUUID().toString().replace("-", ""));
					tPointsInfoCustom.setPoints(tUserInfoCustom.getPoints());
					tPointsInfoCustom.setPointstype("28");
					tPointsInfoCustom.setPointsupdate((points+flowpoints+pointserror));
					tPointsInfoCustom.setTaskpk(tTaskInfoCustom.getTaskpk());
					tPointsInfoCustom.setUserid(tUserInfoCustom.getUserid());
					pointsInfoService.savePoints(tPointsInfoCustom);
					map.put("data", "success");
					logger.info("该订单已成功终止!");
				}else{
					logger.info("该订单还有正在执行的任务!");
					map.put("data", "error");
				}
			}
		}
		logger.info("检查终止中的订单完成!");
		return map;
	}
}
