package cn.hejinyo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/13 11:46
 * @Description :
 */
@XmlRootElement(name = "xml")
public class TextMessage {


    private String ToUserName; //  开发者微信号
    private String FromUserName; //  发送方帐号（一个OpenID）
    private String CreateTime; //  消息创建时间 （整型）
    private String MsgType; //  text
    private String Content; //  文本消息内容
    private String MsgId; // 消息id，64位整型
    private String name;
    private String userName;
    private Integer age;

    public String getToUserName() {
        return ToUserName;
    }

    @XmlElement(name = "ToUserName")
    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    @XmlElement(name = "FromUserName")
    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    @XmlElement(name = "CreateTime")
    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    @XmlElement(name = "MsgType")
    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    @XmlElement(name = "Content")
    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    @XmlElement(name = "MsgId")
    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    @XmlElement
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    @XmlElement
    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", MsgType='" + MsgType + '\'' +
                ", Content='" + Content + '\'' +
                ", MsgId='" + MsgId + '\'' +
                '}';
    }
}
