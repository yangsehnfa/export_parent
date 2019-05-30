package com.itheima.dao.system;

import com.itheima.domian.system.Module;

import java.util.List;

/**
 */
public interface ModuleDao {

    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    int delete(String moduleId);

    //添加用户
    int save(Module module);

    //更新用户
    int update(Module module);

    //查询全部
    List<Module> findAll();

    List<Module> findModulesByRoleId(String id);

    List<Module> findModuleByBelong(int i);

    List<Module> findModuleByUserId(String userid);
}