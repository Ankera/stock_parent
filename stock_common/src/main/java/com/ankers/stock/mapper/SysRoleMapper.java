package com.ankers.stock.mapper;

import com.ankers.stock.pojo.entity.SysRole;

/**
* @author yuyayong
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:17
* @Entity com.ankers.stock.pojo.entity.SysRole
*/
public interface SysRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

}
