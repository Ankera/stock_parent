package com.ankers.stock.mapper;

import com.ankers.stock.pojo.entity.SysLog;

/**
* @author yuyayong
* @description 针对表【sys_log(系统日志)】的数据库操作Mapper
* @createDate 2024-09-10 22:24:16
* @Entity com.ankers.stock.pojo.entity.SysLog
*/
public interface SysLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

}
