package com.ankers.stock.mapper;

import com.ankers.stock.pojo.entity.SysPermission;

/**
* @author yuyayong
* @description 针对表【sys_permission(权限表（菜单）)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:17
* @Entity com.ankers.stock.pojo.entity.SysPermission
*/
public interface SysPermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

}
