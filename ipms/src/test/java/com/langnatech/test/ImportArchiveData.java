package com.langnatech.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ImportArchiveData {
	@Test
	public void test() {
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
			for(String[] ary:list){
				System.out.println(ary[1]+"----------"+ary[2]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/**
 * 
 * 0 ID 1 起始IP 2 终止IP 3 分配方式 4 使用单位名称 5 创建时间 6 核查状态 7 状态 8 移动/铁通 9 所属地 10 操作人 11
 * 分配对象 12 使用方式 13 分配日期 14 域名跳转 15 单位性质 16 单位所属分类 17 单位行业分类 18 单位详细地址 19 联系人姓名
 * 20 联系人电话 21 联系人电子邮件 22 网关IP地址 23 网关物理地址 24 备注 25 省系统id 26 上传时间 27 通过时间 28
 * 经营许可证编号 29 单位行政级别 30 单位所在地区
 */
