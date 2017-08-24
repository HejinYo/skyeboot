package cn.hejinyo.service;

import cn.hejinyo.model.WechatJoke;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/23 22:23
 * @Description :
 */
public interface WechatJokeService {
    WechatJoke getRandomWechatJoke();

    String weater(String citys);
}
