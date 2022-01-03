package com.lwm.dataservice.mapper;

import com.lwm.common.model.User;
import com.lwm.common.vo.UserAccountBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

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

    /**
     * 根据手机号和密码查询user
     */
    User selectByPhoneAndPwd(@Param("phone") String phone, @Param("password") String password);

    /**
     * 根据用户id查询用户和账户信息
     */
    UserAccountBO selectUserAndAccountInfoByUid(Integer uid);

    /**
     * 更新最后一次登录时间
     */
    void updateLastLoginTime(@Param("date") Date date, @Param("id") Integer id);
}
