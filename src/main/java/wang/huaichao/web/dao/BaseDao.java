package wang.huaichao.web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2015/5/15.
 */
@Repository
public class BaseDao {
    @Autowired
    @Qualifier(value = "sessionFactory")
    protected SessionFactory sessionFactory;
}
