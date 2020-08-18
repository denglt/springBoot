package com.springboot.orm.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.model.DrugCatalogue;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Package: com.springboot.orm.user
 * @Author: denglt
 * @Date: 2020/8/17 14:43
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Repository
public interface DrugCatalogueDao extends BaseMapper<DrugCatalogue> {

    @Select("select * from olt_dp_drug_catalogue where dp_id = #{dpId}")
    List<DrugCatalogue> mySelectPage(IPage<DrugCatalogue> page, Long dpId);

    @Select("select * from olt_dp_drug_catalogue where dp_id = #{dpId}")
    Page<DrugCatalogue> mySelectPage2(IPage<DrugCatalogue> page, Long dpId);

}
