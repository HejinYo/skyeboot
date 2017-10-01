package cn.hejinyo.controller;

import cn.hejinyo.model.ReplyMessage;
import cn.hejinyo.model.TextMessage;
import cn.hejinyo.service.WechatJokeService;
import cn.hejinyo.utils.Result;
import cn.hejinyo.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/13 10:48
 * @Description :
 */
@RestController
@RequestMapping(value = "/wechat")
public class WechatController {
    private static final String myToken = "hejinyo";

    @Autowired
    private WechatJokeService wechatJokeService;

    @RequestMapping(method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
    public String joinWechat(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
        String requeststr = "signature:" + signature + ";timestamp" + timestamp + ";nonce:" + nonce + ";echostr:" + echostr;
        System.out.println("微信验证参数：" + requeststr);
        //字典序排序
        ArrayList<String> list = new ArrayList<String>();
        list.add(nonce);
        list.add(timestamp);
        list.add(myToken);
        Collections.sort(list);

        String localsignature = DigestUtils.sha1Hex(list.get(0) + list.get(1) + list.get(2));
        if (signature.equals(localsignature)) {
            System.out.println("微信认证通过：" + localsignature);
            return echostr;
        }
        return echostr;
    }

    @RequestMapping(method = {RequestMethod.POST}, produces = "application/xml;charset=UTF-8")
    public String replyMessage(@RequestBody TextMessage message) {
        System.out.println(message);
        ReplyMessage replyMessage = new ReplyMessage();
        replyMessage.setFromUserName(message.getToUserName());
        replyMessage.setToUserName(message.getFromUserName());
        replyMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
        replyMessage.setMsgType(message.getMsgType());
        String mess = message.getContent();
        if (mess.contains("天气")) {
            String citys = mess.replace("天气", "").replace(" ", "");
            if (StringUtils.isEmpty(citys.trim())) {
                citys = "北京";
            }
            replyMessage.setContent(wechatJokeService.weater(citys.trim()));
        } else if (mess.contains("笑话")) {
            replyMessage.setContent(wechatJokeService.getRandomWechatJoke().getContent());
        } else {
            replyMessage.setFromUserName(message.getToUserName());
            replyMessage.setToUserName(message.getFromUserName());
            replyMessage.setCreateTime(String.valueOf(System.currentTimeMillis()));
            replyMessage.setMsgType(message.getMsgType());
            replyMessage.setMediaId("100000003");
            replyMessage.setContent(mess);
        }

        System.out.println(replyMessage);
        return replyMessage.toString();
    }

    @RequestMapping("/findOne")
    public Result joke() {
        return Result.ok(wechatJokeService.getRandomWechatJoke());
    }

}
