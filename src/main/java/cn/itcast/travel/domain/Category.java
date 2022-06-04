package cn.itcast.travel.domain;

import cn.itcast.travel.anotation.Id;
import cn.itcast.travel.anotation.Table;

import java.io.Serializable;

/**
 * 分类实体类
 */
@Table(name = "tab_category")
public class Category implements Serializable {
    @Id
    private int cid;//分类id
    private String cname;//分类名称

    public Category() {
    }

    public Category(int cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
