package cn.hejinyo.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:48
 * @Description : 用户实体类
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer userId;//用户编号
    private String userName;//用户名称
    private String userPwd;//密码
    private String userSalt;//盐
    private String email;//邮箱
    private String phone;//手机号
    private String loginIp;//最后登录IP
    private Date loginTime;//最后登录时间
    private Integer state;//用户状态 0：正常；1：锁定；-1：禁用(删除)
    private Date createTime; //创建时间
    private Integer createId;//创建人员ID
}
