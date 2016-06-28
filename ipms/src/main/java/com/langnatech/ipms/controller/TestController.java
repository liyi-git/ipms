package com.langnatech.ipms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.ipms.dao.IPArchiveInfoDao;
import com.langnatech.ipms.dao.SubNetResDao;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.enums.IPStatusEnum;
import com.langnatech.ipms.enums.SubNetPlanStatusEnum;
import com.langnatech.ipms.holder.IPArchiveDicHolder;
import com.langnatech.util.IpUtils;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private SubNetResDao subNetResDao;
	@Autowired
	private IPArchiveInfoDao archiveInfoDao;

	@RequestMapping("/init")
	@ResponseBody
	public String init() {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					new File("/media/liyi/Document/Liyi/Documents/Backup/临时文档/新疆移动IP地址管理系统/fpxxList20160409.xls")));
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow firtRow = sheet.getRow(0);
			int colNum = firtRow.getPhysicalNumberOfCells();
			int rowNum = sheet.getLastRowNum();
			List<String[]> list = Lists.newArrayList();
			for (int i = 1; i <= rowNum; i++) {
				String[] ary = new String[colNum];
				HSSFRow row = sheet.getRow(i);
				for (int j = 0; j < colNum; j++) {
					ary[j] = row.getCell(j).toString();
				}
				list.add(ary);
			}
			String[] _ary=null;
			int idx=0;
			for (String[] ary : list) {
				long start = IpUtils.getDecByIp(ary[1]);
				long end = IpUtils.getDecByIp(ary[2]);
				int ipCount = (int) (end - start - 1);
				if (ipCount > 0) {
					_ary=null;
					int bit = IpUtils.getmBitsByIpNum(String.valueOf(start), ipCount);
					SubNetResEntity subNetResBean = new SubNetResEntity();
					subNetResBean.setSubnetId(IDGeneratorHolder.getId());
					subNetResBean.setSubnetDesc(ary[1] + "/" + bit);
					subNetResBean.setBeginIp(ary[1]);
					subNetResBean.setBeginIpDecimal(IpUtils.getDecByIp(ary[1]));
					subNetResBean.setEndIp(ary[2]);
					subNetResBean.setEndIpDecimal(IpUtils.getDecByIp(ary[2]));
					subNetResBean.setMaskBits((short) bit);
					subNetResBean.setNetmask(IpUtils.getMask(bit));
					subNetResBean.setIpCount(ipCount);
					subNetResBean.setSubnetPid("-1");
					subNetResBean.setUseStatus(IPStatusEnum.USED.getCode());
					subNetResBean.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
					subNetResBean.setPoolId("301");
					subNetResBean.setCityId("991");
					subNetResBean.setOperator("ADMIN");
					//subNetResDao.insertSubNet(subNetResBean);
					//saveArchive(subNetResBean, ary);
				} else {
					if(_ary!=null){
						if(!_ary[4].equalsIgnoreCase(ary[4])||(idx==list.size()-1)){
							long _start = IpUtils.getDecByIp(_ary[1]);
							long _end = IpUtils.getDecByIp(_ary[2]);
							int _ipCount = (int) (_end - _start - 1);
							System.out.println(_start+"----"+_end+"---"+_ipCount);
							System.out.println(_ary[1]+"----"+_ary[2]+"---"+_ipCount);
							if(_ipCount<=0) {
								_ary=ary;
								continue;
							}
							int bit = IpUtils.getmBitsByIpNum(String.valueOf(_start), _ipCount);
							SubNetResEntity subNetResBean = new SubNetResEntity();
							subNetResBean.setSubnetId(IDGeneratorHolder.getId());
							subNetResBean.setSubnetDesc(_ary[1] + "/" + bit);
							subNetResBean.setBeginIp(_ary[1]);
							subNetResBean.setBeginIpDecimal(IpUtils.getDecByIp(_ary[1]));
							subNetResBean.setEndIp(_ary[2]);
							subNetResBean.setEndIpDecimal(IpUtils.getDecByIp(_ary[2]));
							subNetResBean.setMaskBits((short) bit);
							subNetResBean.setNetmask(IpUtils.getMask(bit));
							subNetResBean.setIpCount(_ipCount);
							subNetResBean.setSubnetPid("-1");
							subNetResBean.setUseStatus(IPStatusEnum.USED.getCode());
							subNetResBean.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
							subNetResBean.setPoolId("301");
							subNetResBean.setCityId("991");
							subNetResBean.setOperator("ADMIN");
							subNetResDao.insertSubNet(subNetResBean);
							saveArchive(subNetResBean, _ary);
							_ary=ary;
						}else{
							_ary[2]=ary[2];
						}
					}else{
						_ary=ary;
					}
					idx++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private String getCodeByTxt(String groupId, String txt) {
		Map<String, String> map = IPArchiveDicHolder.getDic(groupId);
		Set<Map.Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> entry : set) {
			if (entry.getValue().equalsIgnoreCase(txt)) {
				return entry.getKey();
			}
		}
		return "";
	}

	private String[] getCityCodeByTxt(String txt) {
		String[] resultAry = new String[3];
		resultAry[0] = "650000";
		String[] citys = txt.split(" ");
		Map<String, String> map = IPArchiveDicHolder.INSTANCE.getCitysByPid("650000");
		Set<Map.Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> entry : set) {
			if (entry.getValue().equalsIgnoreCase(citys[1])) {
				resultAry[1] = entry.getKey();
				Map<String, String> map2 = IPArchiveDicHolder.INSTANCE.getCitysByPid(entry.getKey());
				Set<Map.Entry<String, String>> set2 = map2.entrySet();
				if(citys.length>2){
					for (Entry<String, String> entry2 : set2) {
						if (entry2.getValue().equalsIgnoreCase(citys[2])) {
							resultAry[2] = entry2.getKey();
						}
					}
				}
			}
		}
		return resultAry;
	}

	private void saveArchive(SubNetResEntity subNetRes, String[] ary) {
		IPArchiveInfoEntity archiveInfoEntity = new IPArchiveInfoEntity();
		archiveInfoEntity.setArchiveId(IDGeneratorHolder.getId());
		archiveInfoEntity.setSubnetId(subNetRes.getSubnetId());
		archiveInfoEntity.setApplicant("ADMIN");
		archiveInfoEntity.setApplyId("INIT_IMPORT");
		archiveInfoEntity.setPoolId("301");
		archiveInfoEntity.setApplyTime(DateTime.now().toDate());
		archiveInfoEntity.setAreaId("991");
		archiveInfoEntity.setBeginIp(subNetRes.getBeginIp());
		archiveInfoEntity.setIpCount(subNetRes.getIpCount());
		archiveInfoEntity.setApplyReason(ary[24]);
		archiveInfoEntity.setContactEmail(ary[21]);
		archiveInfoEntity.setContactPhone(ary[20]);
		archiveInfoEntity.setEndIp(subNetRes.getEndIp());
		// archiveInfoEntity.setInvalidDate();
		archiveInfoEntity.setGatewayPlace(ary[23]);
		archiveInfoEntity.setGatewayIp(ary[22]);
		archiveInfoEntity.setLicenceCode(ary[28]);
		archiveInfoEntity.setLinkman(ary[19]);
		archiveInfoEntity.setOrgAddress(ary[18]);
		String[] citys=getCityCodeByTxt(ary[30]);
		archiveInfoEntity.setOrgProvId(citys[0]);
		archiveInfoEntity.setOrgCityId(citys[1]);
		archiveInfoEntity.setOrgCountyId(citys[2]);
		archiveInfoEntity.setOrgLevel(getCodeByTxt("ORG_LEVEL", ary[29]));
		archiveInfoEntity.setOrgName(ary[4]);
		archiveInfoEntity.setOrgNature(getCodeByTxt("ORG_NATURE", ary[15]));
		archiveInfoEntity.setOrgTrade(getCodeByTxt("ORG_TRADE", ary[17]));
		archiveInfoEntity.setOrgType(getCodeByTxt("ORG_TYPE", ary[16]));
		archiveInfoEntity.setUseDate(DateTime.parse(ary[13],DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss")).toDate());
		archiveInfoEntity.setUseType(getCodeByTxt("USE_TYPE", ary[12]));
		archiveInfoDao.insert(archiveInfoEntity);
	}
}
/**
 * 
 * 0 ID 1 起始IP 2 终止IP 3 分配方式 4 使用单位名称 5 创建时间 6 核查状态 7 状态 8 移动/铁通 9 所属地 10 操作人 11
 * 分配对象 12 使用方式 13 分配日期 14 域名跳转 15 单位性质 16 单位所属分类 17 单位行业分类 18 单位详细地址 19 联系人姓名
 * 20 联系人电话 21 联系人电子邮件 22 网关IP地址 23 网关物理地址 24 备注 25 省系统id 26 上传时间 27 通过时间 28
 * 经营许可证编号 29 单位行政级别 30 单位所在地区 'ORG_LEVEL' 'ORG_NATURE' 'ORG_TRADE' 'ORG_TYPE'
 * 'USE_TYPE'
 */
