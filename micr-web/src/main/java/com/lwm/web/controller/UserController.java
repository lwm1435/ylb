package com.lwm.web.controller;

import com.lwm.common.dto.DubboResult;
import com.lwm.common.dto.WebResult;
import com.lwm.common.enums.Code;
import com.lwm.common.model.User;
import com.lwm.common.utils.JwtUtil;
import com.lwm.common.vo.RealNameVO;
import com.lwm.common.vo.UserVO;
import com.lwm.common.vo.LoginAndRegVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 10:12
 * @description
 */
@Api("用户操作接口")
@RestController
public class UserController extends BaseController {

    @Value("${jwt-secretkey}")
    private String userKey;

    @ApiOperation(value = "给注册用户发送短信验证码")
    @GetMapping("/v1/sms/send/register")
    public WebResult sendSmsCodeRedister(String phone) throws Exception {
        WebResult webResult = WebResult.fail();
        do {
            //校验参数
            if (StringUtils.isBlank(phone)) {
                webResult.setEnumCode(Code.PARAM_NULL);
                break;
            }
            //检查手机号是否已经注册
            if (userService.checkByPhone(phone)) {
                webResult.setEnumCode(Code.USER_PHONE_EXISTING);
                break;
            }
            //发送验证码
            if (userService.sendSmsCode(phone)) {
                webResult.setEnumCode(Code.SUCCESS);
            }
        } while (false);

        return webResult;
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/v1/user/register")
    public WebResult userRegister(LoginAndRegVO userVO) {
        WebResult webResult = WebResult.fail();
        String phone = userVO.getPhone();
        String code = userVO.getCode();
        String password = userVO.getPassword();
        do {
            //校验参数
            if (StringUtils.isAnyBlank(password, phone, code)) {
                webResult.setEnumCode(Code.PARAM_NULL);
                break;
            }
            //检查手机号是否已经注册
            if (userService.checkByPhone(phone)) {
                webResult.setEnumCode(Code.USER_PHONE_EXISTING);
                break;
            }
            //校验验证码是否正确
            if (!(userService.checkCode(phone, code))) {
                webResult.setEnumCode(Code.SMS_CODE_INVALID);
                break;
            }
            //用户注册服务
            DubboResult res = userService.userRegister(phone, password);
            if (!(res.isInvoke())) {
                //调用失败
                webResult.setMsg(res.getMsg());
                break;
            }
            webResult.setEnumCode(Code.SUCCESS);
            webResult.setData(res.getData());
            webResult.setMsg(res.getMsg());
        } while (false);

        return webResult;
    }

    @ApiOperation(value = "给登录用户和实名用户发送短信验证码")
    @GetMapping("/v1/sms/send/login")
    public WebResult sendSmsCodeLogin(String phone) throws Exception {
        WebResult webResult = WebResult.fail();
        //校验参数
        if (StringUtils.isNotBlank(phone)) {
            webResult.setEnumCode(Code.PARAM_NULL);
            //发送验证码
            if (userService.sendSmsCode(phone)) {
                webResult.setEnumCode(Code.SUCCESS);
            }
        }
        return webResult;
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/v1/user/login")
    public WebResult userLogin(LoginAndRegVO loginAndRegVO) {
        WebResult webResult = WebResult.fail();

        do {
            //校验参数
            String code = loginAndRegVO.getCode();
            String phone = loginAndRegVO.getPhone();
            String password = loginAndRegVO.getPassword();
            if (StringUtils.isAnyBlank(code, phone, password)) {
                webResult.setEnumCode(Code.PARAM_NULL);
                break;
            }
            //校验验证码
            if (!(userService.checkCode(phone, code))) {
                webResult.setEnumCode(Code.SMS_CODE_INVALID);
                break;
            }
            //校验用户登录信息
            User user = userService.checkLogin(phone, password);
            if (user == null) {
                webResult.setEnumCode(Code.USER_LOGIN_INVALID);
                break;
            }
            //生成token
            Map<String, Object> map = new HashMap<>(1);
            Integer uid = user.getId();
            map.put("uid", uid);
            String token = JwtUtil.creatJwt(userKey, map, 120);

            //封装数据 1 userVO{ uid ,phone, name} 2 包含了uid信息的token
            UserVO userVO = new UserVO();
            userVO.setUid(uid);
            userVO.setPhone(user.getPhone());
            userVO.setName(user.getName());
            map = new HashMap<>(2);
            map.put("user", userVO);
            map.put("token", token);
            webResult.setEnumCode(Code.SUCCESS);
            webResult.setData(map);
        } while (false);

        return webResult;
    }

    @ApiOperation(value = "用户实名认证")
    @PostMapping("/v1/user/realname")
    public WebResult userRealName(@RequestHeader Integer uid, RealNameVO realNameVO) throws Exception {
        WebResult webResult = WebResult.fail();
        String name = realNameVO.getName();
        String idCard = realNameVO.getIdCard();
        String code = realNameVO.getCode();
        String phone = realNameVO.getPhone();
        do {
            //校验参数
            if (StringUtils.isAnyBlank(name,idCard,code,phone)){
                webResult.setEnumCode(Code.PARAM_NULL);
                break;
            }
            //校验验证码
            if (!(userService.checkCode(phone,code))){
                webResult.setEnumCode(Code.SMS_CODE_INVALID);
                break;
            }
            //检查用户是否存在
            User user = userService.QueryUserById(uid);
            if (user == null){
                webResult.setEnumCode(Code.USER_LOGIN_INVALID);
                break;
            }
            //检查用户实名的手机号和用户注册时的手机号是否匹配
            if (!phone.equals(user.getPhone())){
                webResult.setEnumCode(Code.PHONE_IS_DIFFERENT);
                break;
            }
            //检查用户是否已经实名
            if (StringUtils.isNotBlank(user.getName())){
                webResult.setEnumCode(Code.NOT_RETRY_REALNAME);
                break;
            }
            //调用实名服务
           if (userService.realName(uid, name, idCard)){
               //封装数据
               UserVO userVO = new UserVO();
               userVO.setUid(uid);
               userVO.setName(name);
               userVO.setPhone(phone);
               webResult.setEnumCode(Code.SUCCESS);
               webResult.setData(userVO);
           }
        }while (false);

        return webResult;
    }
}
