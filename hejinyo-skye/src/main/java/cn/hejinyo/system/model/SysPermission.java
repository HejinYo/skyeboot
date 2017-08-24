package cn.hejinyo.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:11
 * @Description :权限实体类
 */
@Data
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 1L;

    private String permId;//权限编号
    private String resCode;//资源编码
    private String permCode;//权限编码
    private String permName;//权限名称
    private String permUrl;//资源URL
    private Integer state; //状态  0：正常；1：锁定；-1：禁用(删除)
    private Date createTime; //创建时间
    private Integer createId;//创建人员ID

}