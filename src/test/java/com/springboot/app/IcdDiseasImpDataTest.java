package com.springboot.app;

import com.springboot.model.IcdDisease;
import com.springboot.orm.user.IcdDiseaseDao;
import com.yuntai.work.IcdDiseasService;
import dlt.utils.HzUtils;
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
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileInputStream;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.TransactionManagementConfigurationSelector;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description:
 * @Package: com.springboot.app
 * @Author: denglt
 * @Date: 2020/6/8 14:59
 * @Copyright: 版权归 HSYUNTAI 所有
 */


@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class , webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class IcdDiseasImpDataTest {

    @Autowired
    private IcdDiseaseDao icdDiseaseDao;

    @Resource
    private IcdDiseasService icdDiseasService;

    @Resource
    private ApplicationContext applicationContext;
    
    
    
    @Test
    @Transactional
    @Rollback(false)
    public void impData() throws Exception{
        Object bean = applicationContext.getBean(TransactionManager.class);
        System.out.println(bean);
        bean = applicationContext.getBean("txManager2");
        System.out.println(bean);
        Workbook wb = new XSSFWorkbook(new FileInputStream("D:\\mywork\\文档\\中医病证分类及代码-95版.xlsx"));
        Sheet sheet = wb.getSheetAt(0);

        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row hssfRow = sheet.getRow(i);
            IcdDisease icdDisease = new IcdDisease();
            icdDisease.setIcdCode(hssfRow.getCell(1).getStringCellValue());
            icdDisease.setIcdName(hssfRow.getCell(0).getStringCellValue());
            icdDisease.setDiseaseCategory(2);
            icdDisease.setIcdPinyin(HzUtils.getPinyinCap(icdDisease.getIcdName(), HzUtils.CaseType.UPPERCASE));
            icdDisease.setIcdWb(HzUtils.getWbCap(icdDisease.getIcdName(),HzUtils.CaseType.UPPERCASE));
            icdDiseaseDao.insert(icdDisease);
            break;
        }
    }

    @Test
    @Rollback // 配置了@Rollback也不会回滚
    public void delete(){
        icdDiseasService.delete();
    }
}
