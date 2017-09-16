package cn.hejinyo.cloudstorage;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 云存储配置信息
 */
@Data
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    //类型 1：七牛  2：阿里云  3：腾讯云
    @Range(min = 1, max = 3, message = "类型错误")
    private Integer type;

    //七牛绑定的域名
    private String qiniuDomain;
    //七牛路径前缀
    private String qiniuPrefix;
    //七牛ACCESS_KEY
    private String qiniuAccessKey;
    //七牛SECRET_KEY
    private String qiniuSecretKey;
    //七牛存储空间名
    private String qiniuBucketName;
    //覆盖上传的key
    private String key;

}
