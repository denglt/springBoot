package com.springboot.orm.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.model.IcdDisease;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Package: com.springboot.orm.user
 * @Author: denglt
 * @Date: 2020/6/8 14:51
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@Repository
public interface IcdDiseaseDao extends BaseMapper<IcdDisease> {
}
