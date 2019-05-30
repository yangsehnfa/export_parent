package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.RoleDao;
import com.itheima.domian.system.Role;
import com.itheima.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    public void save(Role role) {
        role.setId(UUID.randomUUID().toString());
        roleDao.save(role);
    }

    public void update(Role role) {
        roleDao.update(role);
    }

    public void delete(String id) {
        roleDao.delete(id);
    }

    public Role findById(String id) {
        return roleDao.findById(id);
    }

    public PageInfo findPage(String companyId, int page, int size) {
        PageHelper.startPage(page, size);
        List<Role> roles = roleDao.findAll(companyId);
        return new PageInfo(roles);
    }

    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public void changeRoleModule(String roleid, String[] moduleIds2) {
        for (String moduleid : moduleIds2) {
            roleDao.changeRoleModule(roleid,moduleid);
        }

    }
}
