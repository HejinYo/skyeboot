package cn.hejinyo.service.impl;

import cn.hejinyo.annotation.SysLogger;
import cn.hejinyo.dao.SysUserDao;
import cn.hejinyo.exception.InfoException;
import cn.hejinyo.model.SysUser;
import cn.hejinyo.model.dto.CurrentUserDTO;
import cn.hejinyo.service.SysUserService;
import cn.hejinyo.shiro.utils.ShiroUtils;
import cn.hejinyo.utils.PageQuery;
import cn.hejinyo.utils.PojoConvertUtil;
import cn.hejinyo.utils.StringUtils;
import com.alibaba.druid.sql.visitor.functions.Now;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public CurrentUserDTO getCurrentUser(String userName) {
        return sysUserDao.getCurrentUser(userName);
    }

    @Override
    public SysUser get(int userID) {
        return sysUserDao.get(userID);
        //return PojoConvertUtil.convert(sysUser, SysUser.class);
    }

    @Override
    public List<SysUser> listPage(PageQuery pageQuery) {
        return sysUserDao.listPage(pageQuery);
    }

    @Override
    public int save(SysUser sysUser) {
        //创建新的 PO
        SysUser newUser = new SysUser();
        //用户名小写
        newUser.setUserName(StringUtils.toLowerCase(sysUser.getUserName()));
        //用户盐,随机数
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        newUser.setUserSalt(salt);
        //加密密码
        newUser.setUserPwd(ShiroUtils.userPassword(sysUser.getUserPwd(), salt));
        //邮箱
        newUser.setEmail(sysUser.getEmail());
        //手机
        newUser.setPhone(sysUser.getPhone());
        //创建人员
        newUser.setCreateId(ShiroUtils.getUserId());
        //创建时间
        newUser.setCreateTime(new Date());
        //默认状态：正常
        newUser.setState(0);
        return sysUserDao.save(newUser);
    }

    @Override
    public int isExistUserName(String userName) {
        //查询用户名是否存在
        return sysUserDao.isExistUserName(StringUtils.toLowerCase(userName));
    }

    @Override
    public int update(SysUser sysUser) {
        //用户原来信息
        SysUser sysUserOld = sysUserDao.get(sysUser.getUserId());
        if (null == sysUserOld) {
            throw new InfoException("用户不存在");
        }
        //修改标志
        boolean flag = Boolean.FALSE;
        //新的PO
        SysUser newUser = new SysUser();
        newUser.setUserId(sysUser.getUserId());

        String userStr = sysUser.getUserName();
        String pwdStr = sysUser.getUserPwd();
        String email = sysUser.getEmail();
        String phone = sysUser.getPhone();
        String loginIp = sysUser.getLoginIp();
        Date loginTime = sysUser.getLoginTime();
        Integer state = sysUser.getState();

        //校验用户名是否修改
        if (StringUtils.isNotNull(userStr)) {
            String userName = StringUtils.toLowerCase(userStr);
            if (!userName.equals(sysUserOld.getUserName())) {
                if (sysUserDao.isExistUserName(userName) > 0) {
                    //新的用户名已经存在
                    throw new InfoException("用户名已经存在");
                }
                newUser.setUserName(userName);
                flag = true;
            }
        }
        //密码是否修改
        if (StringUtils.isNotNull(pwdStr)) {
            //加密新密码
            String userPwd = ShiroUtils.userPassword(sysUser.getUserPwd(), sysUserOld.getUserSalt());
            if (!userPwd.equals(sysUserOld.getUserPwd())) {
                //用户盐,随机数
                String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
                newUser.setUserSalt(salt);
                //加密密码
                newUser.setUserPwd(ShiroUtils.userPassword(sysUser.getUserPwd(), salt));
                flag = true;
            }
        }
        //邮箱是否修改
        if (StringUtils.isNotNull(email)) {
            if (!email.equals(sysUserOld.getEmail())) {
                newUser.setEmail(email);
                flag = true;
            }
        }
        //手机是否修改
        if (StringUtils.isNotNull(phone)) {
            if (!phone.equals(sysUserOld.getPhone())) {
                newUser.setPhone(phone);
                flag = true;
            }
        }
        //登录IP是否修改
        if (StringUtils.isNotNull(loginIp)) {
            if (!loginIp.equals(sysUserOld.getLoginIp())) {
                newUser.setLoginIp(loginIp);
                flag = true;
            }
        }
        //登录时间是否修改
        if (null != loginTime) {
            if (!loginTime.equals(sysUserOld.getLoginTime())) {
                newUser.setLoginTime(loginTime);
                flag = true;
            }
        }
        //状态是否修改
        if (null != state) {
            if (!state.equals(sysUserOld.getState())) {
                newUser.setState(state);
                flag = true;
            }
        }
        if (flag) {
            return sysUserDao.update(newUser);
        }
        return 0;
    }

    @Override
    public int delete(SysUser userVO) {
        return sysUserDao.delete(userVO.getUserId());
    }

    @Override
    @SysLogger("用户登录")
    public int updateUserLoginInfo(CurrentUserDTO userDTO) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginTime(new Date());
        sysUser.setUserId(userDTO.getUserId());
        sysUser.setLoginIp(userDTO.getLoginIp());
        return sysUserDao.update(sysUser);
    }
}
