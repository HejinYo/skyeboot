package cn.hejinyo.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:11
 * @Description :角色实体类
 */
@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer roleId;//角色编号
    private String roleCode;//角色编码
    private String roleName;//角色名称
    private String roleDescription;//角色描述
    private Integer seq;//排序号
    private Integer state; //状态  0：正常；1：锁定；-1：禁用(删除)
    private Date createTime; //创建时间
    private Integer createId;//创建人员ID
}
