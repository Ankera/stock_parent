package com.ankers.mapper;

import com.ankers.pojo.entity.SysLog;

/**
* @author yuyayong
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2024-09-10 00:45:52
* @Entity com.ankers.pojo.entity.SysLog
*/
public interface SysLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

}
