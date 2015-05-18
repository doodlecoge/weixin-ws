package wang.huaichao.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.huaichao.misc.WxWsException;
import wang.huaichao.web.dao.UserDao;
import wang.huaichao.web.model.User;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/15.
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;

    public void create(String wxId,
                       String wbxUsername, String wbxPassword,
                       String wbxSiteUrl) {
        final User user = userDao.retrive(wxId);
        if (user != null) {
            throw new WxWsException(
                    "create failed, user already exists: " + wxId);
        }


        final User u = new User();

        u.setWxId(wxId);
        u.setWbxUsername(wbxUsername);
        u.setWbxPassword(wbxPassword);
        u.setWbxSiteUrl(wbxSiteUrl);
        final Date now = Calendar.getInstance().getTime();
        u.setCreatedAt(now);
        u.setUpdatedAt(now);

        userDao.create(u);
    }

    public User retrive(String wxId) {
        return userDao.retrive(wxId);
    }

    public void update(String wxId, String fullName,
                       String wbxUsername, String wbxPassword,
                       String wbxSiteUrl) {
        final User user = userDao.retrive(wxId);
        if (user == null) {
            throw new WxWsException(
                    "update failed, user does not exists: " + wxId);
        }

        user.setWbxUsername(wbxUsername);
        user.setWbxPassword(wbxPassword);
        user.setWbxSiteUrl(wbxSiteUrl);
        user.setUpdatedAt(Calendar.getInstance().getTime());

        userDao.update(user);
    }

    public void delete(String wxId) {
        final User user = userDao.retrive(wxId);
        if (user == null) {
            throw new WxWsException(
                    "delete failed, user does not exists: " + wxId);
        }

        userDao.delete(user);
    }
}
