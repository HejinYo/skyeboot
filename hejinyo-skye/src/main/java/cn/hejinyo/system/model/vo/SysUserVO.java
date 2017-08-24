package cn.hejinyo.system.model.vo;

import cn.hejinyo.validator.group.SaveGroup;
import cn.hejinyo.validator.group.UpdateGroup;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:48
 * @Description : 用户VO类
 */
@Data
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer userId;//用户编号

    @NotBlank(message = "用户名不能为空", groups = {SaveGroup.class})
    private String userName;//用户名称

    //密码相关不允许序列化
    @JSONField(serialize = false)
    @NotBlank(message = "密码不能为空", groups = {SaveGroup.class})
    private transient String userPwd;//密码

    @JSONField(serialize = false)
    private transient String userSalt;//盐

    @Email(message = "邮箱格式不正确", groups = {SaveGroup.class, UpdateGroup.class})
    private String email;//邮箱

    @Pattern(regexp = "^$|^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机格式不正确", groups = {SaveGroup.class, UpdateGroup.class})
    private String phone;//手机号

    private String loginIp;//最后登录IP
    private Date loginTime;//最后登录时间
    private Integer state;//用户状态 0：正常；1：锁定；-1：禁用(删除)
    private Date createTime; //创建时间
    private Integer createId;//创建人员ID


}
