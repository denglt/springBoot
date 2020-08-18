package com.springboot.app;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.model.DrugCatalogue;

import com.springboot.orm.user.DrugCatalogueDao;
import com.yuntai.med.support.util.HzUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Package: com.springboot.app
 * @Author: denglt
 * @Date: 2020/8/17 14:47
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DrugCatalogueDaoTest {

    @Autowired
    private DrugCatalogueDao drugCatalogueDao;

    @Test
    public void updateDrugPinYinAndWb() {
        QueryWrapper<DrugCatalogue> wrapper = new QueryWrapper<>();
        wrapper.eq("dp_id", 217);//.last("limit 1");
        List<DrugCatalogue> drugCatalogues = drugCatalogueDao.selectList(wrapper);

        drugCatalogues.forEach(drug -> {
            String drugName = drug.getDrugChemName();
            String pinyin = HzUtils.getPinyinCap(drugName, HzUtils.CaseType.UPPERCASE);
            String wb = HzUtils.getWbCap(drugName, HzUtils.CaseType.UPPERCASE);
            DrugCatalogue drugCatalogue = new DrugCatalogue();
            drugCatalogue.setDrugPinyin(pinyin);
            drugCatalogue.setDrugWb(wb);
            drugCatalogue.setDrugId(drug.getDrugId());
            int i = drugCatalogueDao.updateById(drugCatalogue);
            System.out.println("update count " + i);
        });
    }

    /**
     * UpdateWrapper 可以直接 set 想要更新的字段，支持null
     */
    @Test
    public void update() {
        UpdateWrapper<DrugCatalogue> wrapper = new UpdateWrapper<>();
        wrapper.eq("dp_id", 217).like("drug_code", "denglt%");
        DrugCatalogue drugCatalogue = new DrugCatalogue();
        drugCatalogue.setDrugCode("dengltxxxx");
        wrapper.set("drug_wb", null); // drug_wb = null
        // wrapper.set("drug_code",null);
        int update = drugCatalogueDao.update(drugCatalogue, wrapper);
        System.out.println("update count " + update);

    }

    @Test
    public void selectPage() {
        QueryWrapper<DrugCatalogue> wrapper = new QueryWrapper<>();
        wrapper.eq("dp_id", 217);//.last("limit 1");
        Page<DrugCatalogue> page = new Page<>();
        page.setCurrent(3);
        page.setSize(15);
        Page<DrugCatalogue> pageData = drugCatalogueDao.selectPage(page, wrapper);
        List<DrugCatalogue> data = pageData.getRecords();

        String pageInfo = "current:" + page.getCurrent() + "; size:" + page.getSize() + "; total:" + page.getTotal() + "; pages:" + page.getPages();
        System.out.println(pageInfo);

    }
}
