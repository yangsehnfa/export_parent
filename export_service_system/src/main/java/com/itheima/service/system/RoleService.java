package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.system.Role;

import java.util.List;

public interface RoleService {
    //保存用户
    public void save(Role role);
    //更新用户
    public void update(Role role);
    //删除用户
    public void delete(String id);
    //根据 id 查询用户
    public Role findById(String id);
    //查询所有角色
    public List<Role> findAll(String companyId);
    //分页查询
    public PageInfo findPage(String companyId, int page, int size);

    void changeRoleModule(String roleid, String[] moduleIds2);
}
