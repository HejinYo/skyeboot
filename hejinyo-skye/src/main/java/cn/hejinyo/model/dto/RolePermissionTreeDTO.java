package cn.hejinyo.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : HejinYo   hejinyo@gmail.com     2017/9/27 23:32
 * @apiNote :
 */
@Data
public class RolePermissionTreeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer resId;//资源编号
    private String resCode;//资源编码
    private String resName;//资源名称
    private Integer permId;//权限编号
    private String permCode;//权限编码
    private String permName;//权限名称
    private String type;//权限名称
    @JSONField(deserialize = false)
    private List<RolePermissionTreeDTO> childrenRes;//子资源
}
