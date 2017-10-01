package cn.hejinyo.controller;

import cn.hejinyo.cloudstorage.CloudStorageConfig;
import cn.hejinyo.cloudstorage.QiniuCloudStorageService;
import cn.hejinyo.utils.Result;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author : HejinYo   hejinyo@gmail.com     2017/9/25 12:06
 * @apiNote :
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private static final String domain = "http://ow1prafcd.bkt.clouddn.com";

    /**
     * 多文件通过服务器上传
     */
    @PostMapping(value = "/multiFileUpload")
    public Result multiFileUpload(@RequestParam("file") MultipartFile[] files) {
        List<String> list = new ArrayList<>();
        CloudStorageConfig config = new CloudStorageConfig();
        config.setQiniuAccessKey("GqZQG6TvEZGPkCXzm5O7QN1jipLdeI4CXXsR6N3G");
        config.setQiniuSecretKey("qodIX8q2zqaX4eSAiOvcS1YNLeKU_cxyNtSFkWf9");
        config.setQiniuBucketName("skye-user-avatar");
        config.setQiniuDomain(domain);
        for (int i = 0; i < files.length; i++) {
            // 获得原始文件名
            String fileName = files[i].getOriginalFilename();
            // 新文件名
            String newFileName = UUID.randomUUID() + "-" + fileName;
            if (!files[i].isEmpty()) {
                config.setKey(newFileName);
                QiniuCloudStorageService storageService = new QiniuCloudStorageService(config);
                try {
                    String result = storageService.upload(files[i].getInputStream(), newFileName);
                    list.add(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (list.size() > 0) {
            return Result.ok(list);
        }
        return Result.error("上传失败");
    }

    /**
     * 获取云存储文件列表
     */

    @GetMapping("/filelist")
    public Result listCloudFileList() {
        Configuration cfg = new Configuration(Zone.zone0());
        String accessKey = "GqZQG6TvEZGPkCXzm5O7QN1jipLdeI4CXXsR6N3G";
        String secretKey = "qodIX8q2zqaX4eSAiOvcS1YNLeKU_cxyNtSFkWf9";
        String bucket = "skye-user-avatar";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            List<JSONObject> list = new ArrayList<>();
            for (FileInfo item : items) {
               /* System.out.println(item.key);
                System.out.println(item.hash);
                System.out.println(item.fsize);
                System.out.println(item.mimeType);
                System.out.println(item.putTime);
                System.out.println(item.endUser);*/
                JSONObject ob = new JSONObject();
                ob.put("name", item.key);
                ob.put("url", domain + "/" + item.key);
                list.add(ob);
            }
            return Result.ok(list);
        }


        return Result.error();
    }

    /**
     * 删除云存储文件
     */

    @PostMapping("/filedelete")
    public Result deleteFile(@RequestBody Map<String, Object> param) {
        Configuration cfg = new Configuration(Zone.zone0());
        String accessKey = "GqZQG6TvEZGPkCXzm5O7QN1jipLdeI4CXXsR6N3G";
        String secretKey = "qodIX8q2zqaX4eSAiOvcS1YNLeKU_cxyNtSFkWf9";
        String bucket = "skye-user-avatar";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response response = bucketManager.delete(bucket, param.get("name").toString());
            return Result.ok(response.statusCode);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }

        return Result.error();
    }
}
