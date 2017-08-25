package cn.hejinyo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:17
 * @Description : 用户角色关联类
 */
@Data
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;//用户角色ID
    private Integer userId;//用户ID
    private Integer roleId;//角色ID

}
