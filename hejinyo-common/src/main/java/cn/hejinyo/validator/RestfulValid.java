package cn.hejinyo.validator;

import javax.validation.GroupSequence;

/**
 * @author : HejinYo   hejinyo@gmail.com
 * @date : 2017/8/25 21:47
 * @Description :
 */
public interface RestfulValid {
    public interface GET {
    }

    public interface DELETE {
    }

    public interface PUT {
    }

    public interface POST {
    }

    public interface PATCH {
    }

    /**
     * 定义校验顺序，如果POST组失败，则PUT组不会再校验
     */
    @GroupSequence({POST.class, PUT.class})
    public interface POSTANDPUT {

    }
}
