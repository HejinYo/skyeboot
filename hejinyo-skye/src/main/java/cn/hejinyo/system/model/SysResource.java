package cn.hejinyo.system.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:32
 * @Description : 资源实体类
 */
@Data
public class SysResource implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer resId; //资源编号
    private String resType;//资源类型
    private String resCode;//资源编码
    private String resName;//资源名称
    private Integer resPid;//父资源ID
    private Integer resLevel;//资源级别
    private String resIcon;//显示图标
    private Integer seq;//排序号
    private Integer state;//状态 0：正常；1：锁定；-1：禁用(删除)
    private Date createTime;//创建时间
    private Integer createId;//创建人员ID
    private List<SysPermission> function;//资源对应的权限信息
}
