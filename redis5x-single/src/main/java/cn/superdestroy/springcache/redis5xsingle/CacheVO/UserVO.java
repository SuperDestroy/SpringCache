package cn.superdestroy.springcache.redis5xsingle.CacheVO;

import java.io.Serializable;

/**
 * 2019-01-10 16:27
 *
 * @author zhangningwei
 */
public class UserVO implements Serializable {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static UserVO create(String id){
        UserVO userVO = new UserVO();
        userVO.setId(id);
        userVO.setName("用户" + id);
        return userVO;
    }
}
