import cn.itcast.travel.dao.BaseDao;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.BaseDaoImpl;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import org.junit.Test;

public class BaseDaoTest {
//    private BaseDao<User> baseDao = new BaseDaoImpl<User>();
    private UserDao userDao = new UserDaoImpl();

    @Test
    public void testFindOne() {
        User user = userDao.findById(1);
        System.out.println(user);
    }

}
