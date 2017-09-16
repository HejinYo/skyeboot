package cn.hejinyo.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class SysUser implements Serializable {
    //用户编号 user_id
    private Integer userId;

    //用户姓名 user_name
    private String userName;

    //密码 user_pwd
    private String userPwd;

    //用户盐 user_salt
    private String userSalt;

    //邮箱 email
    private String email;

    //手机号 phone
    private String phone;

    //最后登录IP login_ip
    private String loginIp;

    //最后登录时间 login_time
    private Date loginTime;

    //用户状态 0：正常；1：锁定；-1：禁用(删除) state
    private Integer state;

    //注册时间 create_time
    private Date createTime;

    //创建人员 create_id
    private Integer createId;

    private static final long serialVersionUID = 1L;
}