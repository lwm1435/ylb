package com.lwm.dataservice.mapper;

import com.lwm.common.model.User;

/**
* @author Administrator
* @description 针对表【u_user(用户表)】的数据库操作Mapper
* @createDate 2022-01-01 21:22:13
* @Entity com.lwm.common.model.User
*/
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 查询用户总量
     */
    Long selectTotalUser();

    /**
     * 根据phone查询user
     */
    User selectByPhone(String phone);




}
