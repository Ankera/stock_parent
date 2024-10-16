package com.ankers.stock.mapper;

import com.ankers.stock.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author yuyayong
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:17
* @Entity com.ankers.stock.pojo.entity.SysUser
*/
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findUserInfoByUsername(@Param("username") String username);

    List<SysUser> findAll();
}
