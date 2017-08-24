package cn.hejinyo.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果SaveGroup组失败，则UpdateGroup组不会再校验
 *
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/4/9 14:48
 * @Description :
 */
@GroupSequence({SaveGroup.class, UpdateGroup.class})
public interface Group {

}
