package com.ankers.stock.mapper;

import com.ankers.stock.pojo.entity.SysRolePermission;

/**
* @author yuyayong
* @description 针对表【sys_role_permission(角色权限表)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:17
* @Entity com.ankers.stock.pojo.entity.SysRolePermission
*/
public interface SysRolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

}
