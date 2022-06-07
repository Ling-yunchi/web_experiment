package cn.itcast.travel.domain;

import cn.itcast.travel.anotation.Id;
import cn.itcast.travel.anotation.Table;

import java.io.Serializable;

/**
 * 收藏实体类
 */
@Table(name = "tab_favorite")
public class Favorite implements Serializable {
    // FIXME: 暂不支持多主键
    @Id
    private Integer rid;
    private Integer uid;
    private String date;//收藏时间
//    private Route route;//旅游线路对象
//    private User user;//所属用户

    /**
     * 无参构造方法
     */
    public Favorite() {
    }

    /**
     * 有参构造方法
     *
     * @param rid
     * @param date
     * @param uid
     */
    public Favorite(Integer rid, String date, Integer uid) {
        this.rid = rid;
        this.date = date;
        this.uid = uid;
    }

//    public Route getRoute() {

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
//        return route;
//    }
//
//    public void setRoute(Route route) {
//        this.route = route;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
