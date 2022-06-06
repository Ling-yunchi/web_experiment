package cn.itcast.travel.module;

import cn.itcast.travel.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangrong
 * @date 2022/6/6 10:01
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResult {
    private Integer uid;//用户id
    private String username;//用户名，账号
    private String name;//真实姓名
    private String birthday;//出生日期
    private String sex;//男或女
    private String telephone;//手机号
    private String email;//邮箱

    public UserResult(User u) {
        this.uid = u.getUid();
        this.username = u.getUsername();
        this.name = u.getName();
        this.birthday = u.getBirthday();
        this.sex = u.getSex();
        this.telephone = u.getTelephone();
        this.email = u.getEmail();
    }
}
