package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.ModuleDao;
import com.itheima.domian.system.Module;
import com.itheima.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModulServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    public void save(Module module) {
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    public void update(Module module) {
        moduleDao.update(module);
    }
    public void delete(String id) {
        moduleDao.delete(id);
    }

    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    public List<Module> findAll() {
        return moduleDao.findAll();
    }

/*    public List<Module> findAllMenus() {
        return moduleDao.findAllMenus();
    }*/


    public PageInfo findPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Module> modules = moduleDao.findAll();
        return new PageInfo(modules);
    }

    @Override
    public List<Module> findModulesByRoleId(String id) {
        return moduleDao.findModulesByRoleId(id);
    }
}
