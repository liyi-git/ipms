package com.langnatech.ipms.service.impl;

import java.io.IOException;
import java.util.List;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.langnatech.ipms.dao.IPArchiveInfoDao;
import com.langnatech.ipms.dao.SubNetResDao;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.service.IPArchiveInfoService;
import com.langnatech.util.IpUtils;
import com.langnatech.util.convert.JsonConvertUtil;

@Service
public class IPArchiveInfoServiceImpl implements IPArchiveInfoService
{

    @Autowired
    private IPArchiveInfoDao archiveInfoDao;
    @Autowired
    private SubNetResDao subnetResDao;
    
    public IPArchiveInfoEntity getArchiveInfoBySubnetId(String subnetId)
    {
        return archiveInfoDao.selectBySubNetId(subnetId);
    }

	@Override
	public String applyAdd(String invokJSON) {
		//通过地市，地址数定位出地址信息
		try {
			ObjectMapper objectMapper=new ObjectMapper();
			IPArchiveInfoEntity archiveInfo=objectMapper.readValue(invokJSON, IPArchiveInfoEntity.class);
			List<SubNetResEntity> usableList=subnetResDao.getUsableSubnetList(archiveInfo.getAreaId());
			//TODO 获取地市下的可用地址段进行拆分
			//TODO 拆分后的地址段保存
			//TODO 拆分后的父地址段的处理
		} catch (JsonParseException e) {
			e.printStackTrace();
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		//返回JSON字符串信息
		return null;
		
	}
	 
	@Override
	public String cancleAppalyAdd(String invokJSON) {
		//TODO 根据申请单ID删除申请的备案信息
		//TODO 更新网段信息（拆分的标记状态为空闲，网段合并）
		return null;
	}

	 
	@Override
	public String appalyUseAdd(String invokJSON) {
		//TODO 根据申请单ID找到网段信息标记成使用状态
		return null;
	}
	 
	@Override
	public String appalyRecycleAdd(String invokJSON) {
		//TODO 根据申请单ID删除申请备案信息
		//TODO 回收网段（标记，合并）
		return null;
		
	}

}
