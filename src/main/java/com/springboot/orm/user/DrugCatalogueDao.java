package com.springboot.orm.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.model.DrugCatalogue;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Package: com.springboot.orm.user
 * @Author: denglt
 * @Date: 2020/8/17 14:43
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Repository
public interface DrugCatalogueDao  extends BaseMapper<DrugCatalogue> {
}
