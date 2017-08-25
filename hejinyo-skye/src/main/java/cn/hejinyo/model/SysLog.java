package cn.hejinyo.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/6/12 22:19
 * @Description : 系统日志实体类
 */
@Data
public class SysLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String userName;//用户名
    private String operation;//用户操作
    private String method;//请求方法
    private String params;//请求参数
    private String ip;//IP地址
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

}
