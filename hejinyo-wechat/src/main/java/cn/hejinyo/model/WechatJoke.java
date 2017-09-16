package cn.hejinyo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WechatJoke implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private Integer typeid;

    private Integer hits;

    private Date dateandtime;

    private String content;

}