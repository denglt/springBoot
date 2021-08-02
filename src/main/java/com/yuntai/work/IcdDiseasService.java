package com.yuntai.work;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.springboot.model.IcdDisease;
import com.springboot.orm.user.IcdDiseaseDao;
import dlt.utils.HzUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileInputStream;

/**
 * @Description:
 * @Package: com.yuntai.work
 * @Author: denglt
 * @Date: 2020/10/27 15:32
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Service
public class IcdDiseasService {

    @Resource
    private IcdDiseaseDao icdDiseaseDao;

    @Transactional
    public void impData() {
        try {
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
            Row tail = sheet.getRow(sheet.getLastRowNum() - 1);
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
                icdDisease.setIcdWb(HzUtils.getWbCap(icdDisease.getIcdName(), HzUtils.CaseType.UPPERCASE));
                icdDiseaseDao.insert(icdDisease);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(){
        QueryWrapper<IcdDisease> where = new QueryWrapper<>();
        where.eq("disease_category",2);
        icdDiseaseDao.delete(where);
    }
}
