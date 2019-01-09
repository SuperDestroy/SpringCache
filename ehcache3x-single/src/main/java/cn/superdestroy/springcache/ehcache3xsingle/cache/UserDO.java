package cn.superdestroy.springcache.ehcache3xsingle.cache;

import java.io.Serializable;

/**
 * 2019-01-09 17:18
 * 缓存中放置的对象 需要实现序列化接口
 * @author zhangningwei
 */
public class UserDO implements Serializable {
    /**
     * 登陆Id
     */
    private String loginId;
    /**
     * 用户名称
     */
    private String name;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "loginId='" + loginId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
