package cn.hejinyo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/13 12:39
 * @Description :
 */
@XmlRootElement(name = "xml")
public class ReplyMessage {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String MediaId;

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

    public String getMediaId() {
        return MediaId;
    }

    @XmlElement(name = "MediaId")
    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    @Override
    public String toString() {
        return "<xml>" +
                "<ToUserName>" + ToUserName + "</ToUserName>" +
                "<FromUserName>" + FromUserName + "</FromUserName>" +
                "<CreateTime>" + CreateTime + "</CreateTime>" +
                "<MsgType>" + MsgType + "</MsgType>" +
                "<Content>" + Content + "</Content>" +
                "<MediaId>" + MediaId + "</MediaId>" +
                "</xml>";
    }
}
