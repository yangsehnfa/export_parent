package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.system.Module;
import com.itheima.domian.system.User;

import java.util.List;

public interface UserService {
    //保存用户
    public void save(User user);
    //更新用户
    public void update(User user);
    //删除用户
    public void delete(String id);
    //根据 id 查询用户
    public User findById(String id);
    //分页查询
    public PageInfo findPage(String companyId, int page, int size);

    List<String> findRoleByUserId(String id);

    void changeRole(String userid, String[] roleIds);

    User findByEmail(String email);

    List<Module> findModuleByUserId(String userid);
}
