package com.gdlgxy.ybyyhisserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdlgxy.ybyyhisserver.pojo.Menu;
import com.gdlgxy.ybyyhisserver.pojo.RolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface RolePermMapper extends BaseMapper<RolePerm> {
}
