package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.ModuleDao;
import com.itheima.dao.system.UserDao;
import com.itheima.domian.system.Module;
import com.itheima.domian.system.User;
import com.itheima.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ModuleDao moduleDao;
    public void save(User user) {
        user.setId(UtilFuns.getUUID());
        userDao.save(user);
    }
    public void update(User user) {
        userDao.update(user);
    }
    public void delete(String id) {
        userDao.delete(id);
    }
    public User findById(String id) {
        return userDao.findById(id);
    }
    public PageInfo findPage(String companyId, int page, int size) {
        PageHelper.startPage(page, size);
        List<User> users = userDao.findAll(companyId);
        return new PageInfo(users);
    }

    /**
     * 根据用户id查询所有角色id
     * @param id
     * @return
     */
    @Override
    public List<String> findRoleByUserId(String id) {
        return userDao.RoleByUserId(id);
    }

    /**
     * 改变用户的角色
     * @param userid
     * @param roleIds
     */
    @Override
    public void changeRole(String userid, String[] roleIds) {
        for (String roleid : roleIds) {
            userDao.changeRole(userid,roleid);
        }

    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<Module> findModuleByUserId(String userid) {
        User user = userDao.findById(userid);
        //saas系统管理员
        if(user.getDegree()==0){
            return moduleDao.findModuleByBelong(0);
        }else if(user.getDegree()==1){
            return moduleDao.findModuleByBelong(1);
        }else {
            return moduleDao.findModuleByUserId(userid);
        }
    }
}
