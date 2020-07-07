package com.springboot.app;

import com.springboot.model.IcdDisease;
import com.springboot.orm.user.IcdDiseaseDao;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileInputStream;
import com.yuntai.med.support.util.HzUtils;

/**
 * @Description:
 * @Package: com.springboot.app
 * @Author: denglt
 * @Date: 2020/6/8 14:59
 * @Copyright: 版权归 HSYUNTAI 所有
 */


@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class IcdDiseasImpDataTest {

    @Autowired
    private IcdDiseaseDao icdDiseaseDao;

    @Test
    public void impDdata() throws Exception{
      //  Workbook wb = new HSSFWorkbook(new FileInputStream("D:\\mywork\\文档\\广东省病案统计系统中医编码.xls"));
        Workbook wb = new XSSFWorkbook(new FileInputStream("D:\\mywork\\文档\\中医病证分类及代码-95版.xlsx"));
        Sheet sheet = wb.getSheetAt(0);
        Row header = sheet.getRow(0);

        header.forEach(cell -> {
          //  if (!StringUtils.isEmpty(cell.getStringCellValue()))
                System.out.println(cell.getColumnIndex() + " ->" + cell.getStringCellValue());
        });

        System.out.println(sheet.getFirstRowNum());
        System.out.println(sheet.getLastRowNum());
        Row tail = sheet.getRow(sheet.getLastRowNum() -1 );
        tail.forEach(cell -> {
           // if (!StringUtils.isEmpty(cell.getStringCellValue()))
                System.out.println(cell.getColumnIndex() + " ->" + cell.getStringCellValue());
        });
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row hssfRow = sheet.getRow(i);
            IcdDisease icdDisease = new IcdDisease();
            icdDisease.setIcdCode(hssfRow.getCell(1).getStringCellValue());
            icdDisease.setIcdName(hssfRow.getCell(0).getStringCellValue());
            icdDisease.setDiseaseCategory(2);
            icdDisease.setIcdPinyin(HzUtils.getPinyinCap(icdDisease.getIcdName(), HzUtils.CaseType.UPPERCASE));
            icdDisease.setIcdWb(HzUtils.getWbCap(icdDisease.getIcdName(),HzUtils.CaseType.UPPERCASE));
            icdDiseaseDao.insert(icdDisease);
        }

    }
}
