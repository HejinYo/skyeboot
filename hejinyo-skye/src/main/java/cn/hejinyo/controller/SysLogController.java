package cn.hejinyo.controller;

import cn.hejinyo.annotation.SysLogger;
import cn.hejinyo.model.SysLog;
import cn.hejinyo.service.SysLogService;
import cn.hejinyo.utils.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/16 22:14
 * @Description :
 */

@RestController
@RequestMapping("/")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @SysLogger("获得日志列表")
    @GetMapping(value = "/syslog")
    @RequiresPermissions("user:create")
    public Result get() {
        PageHelper.startPage(1, 10);
        Map<String, Object> map = new HashMap<>();
        List<SysLog> sysLogList = sysLogService.list();
        map.put("syslog", sysLogList);
        PageInfo<SysLog> userPageInfo = new PageInfo<>(sysLogList, 3);
        map.put("userPageInfo", userPageInfo);
        return Result.ok(userPageInfo);
    }
}
