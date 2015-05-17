package wang.huaichao.web.dao;

import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import wang.huaichao.web.model.User;

/**
 * Created by Administrator on 2015/5/15.
 */
@Repository
public class UserDao extends BaseDao {
    public void create(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public User retrive(String wxId) {
        final Object user = sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .add(Restrictions.eq("wxId", wxId))
                .uniqueResult();
        return user == null ? null : (User) user;
    }

    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }
}
