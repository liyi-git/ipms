package com.langnatech.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Test;

import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.ipms.enums.SubNetPlanStatusEnum;
import com.langnatech.ipms.enums.SubNetUseStatusEnum;
import com.langnatech.util.IpUtils;

public class ImportInitIP {
	@Test
	public void test() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(
					new File("/Users/liyi/Desktop/IP地址数据导入模板.xlsx")));
			FileWriter fileWriter=new FileWriter(new File("/Users/liyi/Desktop/sql.sql"));
			XSSFSheet sheet = workbook.getSheetAt(0);
			int totalRow=sheet.getLastRowNum();
			Map<String,String> subnetMap=new HashMap<String,String>();
			for(int i=1;i<totalRow;i++){
				XSSFRow row = sheet.getRow(i);
				String ip1=row.getCell(0)!=null?String.valueOf(row.getCell(0)):null;
				String ip2=row.getCell(0)!=null?String.valueOf(row.getCell(1)):null;
				String ip3=row.getCell(0)!=null?String.valueOf(row.getCell(2)):null;
				String ip4=row.getCell(0)!=null?String.valueOf(row.getCell(3)):null;
				String ip5=row.getCell(0)!=null?String.valueOf(row.getCell(4)):null;
				String planStatus=row.getCell(5)!=null?row.getCell(5).getStringCellValue():null;
				Date useDate=row.getCell(6)!=null?row.getCell(6).getDateCellValue():null;
				String poolId,cityId;
				if(row.getCell(8).getCellType()==Cell.CELL_TYPE_FORMULA||row.getCell(8).getCellType()==Cell.CELL_TYPE_NUMERIC){
					poolId=NumberFormat.getIntegerInstance().format(row.getCell(8).getNumericCellValue());
				}else{
					poolId=row.getCell(8).getStringCellValue();
				}
				if(row.getCell(10).getCellType()==Cell.CELL_TYPE_FORMULA||row.getCell(10).getCellType()==Cell.CELL_TYPE_NUMERIC){
					cityId=NumberFormat.getIntegerInstance().format(row.getCell(10).getNumericCellValue());
				}else{
					cityId=row.getCell(10).getStringCellValue();
				}
				String _ip,_pIp,_pPIp;
				if(ip5!=null&&!ip5.equals("null")){
					_ip=ip5;
					_pIp=ip4;
					_pPIp=ip3;
				}else if(ip4!=null&&!ip4.equals("null")){
					_ip=ip4;
					_pIp=ip3;
					_pPIp=ip2;
				}else if(ip3!=null&&!ip3.equals("null")){
					_ip=ip3;
					_pIp=ip2;
					_pPIp=ip1;
				}else if(ip2!=null&&!ip2.equals("null")){
					_ip=ip2;
					_pIp=ip1;
					_pPIp="-1";
				}else{
					_ip=ip1;
					_pIp="-1";
					_pPIp="-1";
				}
				String subnetPid=subnetMap.get(_pIp);
				if(subnetPid==null&&!_pIp.equals("-1.0")){
					String[] ipary=_pIp.split("\\/");
					String beginIp=ipary[0];
					int bit=Integer.parseInt(ipary[1]);
					String endIp=IpUtils.getBroadcastIp(beginIp, bit);
					String mask=IpUtils.getMask(bit);
					int ipCount=IpUtils.getHostNum(bit)-2;
					String subnetId=IDGeneratorHolder.getId();
					String pid=subnetMap.get(_pPIp);
					pid=(pid==null)?"-1":pid;
					String ps="-1";
					String us="-1";
					String pool="-1";
					String city="-1";
					String sql ="insert into `ipms`.`ip_subnet_res` ( `SUBNET_ID`, `SUBNET_DESC`, `BEGIN_IP`, `BEGIN_IP_DECIMAL`, `END_IP`, `END_IP_DECIMAL`, `MASK_BITS`, `NETMASK`, `IP_COUNT`,"+
					" `SUBNET_PID`, `IS_IPV6`, `PLAN_STATUS`, `USE_STATUS`, `POOL_ID`, `CITY_ID`, `OPERATOR`, `OPERATE_TIME`, `LFT`, `RGT`) values "+
					"( '"+subnetId+"', '"+_pIp+"', '"+beginIp+"', '"+IpUtils.getDecByIp(beginIp)+"', '"+endIp+"', '"+IpUtils.getDecByIp(endIp)+"', '"+bit+"', '"+mask+"', '"+ipCount+
					"', '"+pid+"', '-1', '"+ps+"', '"+us+"', '"+pool+"', '"+city+"', 'ADMIN', now(), null, null);";
					subnetMap.put(_pIp, subnetId);
					subnetPid=subnetId;
					fileWriter.write(sql+"\n");
				}
				subnetPid=subnetPid==null?"-1":subnetPid;
				String[] ipary=_ip.split("\\/");
				String beginIp=ipary[0];
				int bit=Integer.parseInt(ipary[1]);
				String endIp=IpUtils.getBroadcastIp(beginIp, bit);
				String mask=IpUtils.getMask(bit);
				int ipCount=IpUtils.getHostNum(bit)-2;
				String subnetId=IDGeneratorHolder.getId();
				String ps=(planStatus!=null&&planStatus.equals("已规划"))?SubNetPlanStatusEnum.PLANNED.getCode().toString():SubNetPlanStatusEnum.WAIT_PLAN.getCode().toString();
				String us=(planStatus!=null&&planStatus.equals("已规划"))?SubNetUseStatusEnum.USED.getCode().toString():SubNetUseStatusEnum.AVAILABLE.getCode().toString();
				String pool=poolId;
				String city=cityId;
				useDate=useDate==null?DateTime.now().toDate():useDate;
				String operDate=new DateTime(useDate.getTime()).toString("yyyy-MM-dd HH:mm:ss");
				String sql ="insert into `ipms`.`ip_subnet_res` ( `SUBNET_ID`, `SUBNET_DESC`, `BEGIN_IP`, `BEGIN_IP_DECIMAL`, `END_IP`, `END_IP_DECIMAL`, `MASK_BITS`, `NETMASK`, `IP_COUNT`,"+
				" `SUBNET_PID`, `IS_IPV6`, `PLAN_STATUS`, `USE_STATUS`, `POOL_ID`, `CITY_ID`, `OPERATOR`, `OPERATE_TIME`, `LFT`, `RGT`) values "+
				"( '"+subnetId+"', '"+_ip+"', '"+beginIp+"', '"+IpUtils.getDecByIp(beginIp)+"', '"+endIp+"', '"+IpUtils.getDecByIp(endIp)+"', '"+bit+"', '"+mask+"', '"+ipCount+
				"', '"+subnetPid+"', '-1', '"+ps+"', '"+us+"', '"+pool+"', '"+city+"', 'ADMIN', '"+operDate+"', null, null);";
				subnetMap.put(_ip, subnetId);
				fileWriter.write(sql+"\n");			
			}
			fileWriter.flush();
			fileWriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
