package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.system.Module;

import java.util.List;

public interface ModuleService {
    //保存
    public void save(Module module);
    //更新
    public void update(Module module);
    //删除
    public void delete(String id);
    //根据 id 查询
    public Module findById(String id);
    //分页查询
    public PageInfo findPage(int page, int size);
    //查询所有菜单
//    public List<Module> findAllMenus();
    //查询所有
    public List<Module> findAll();

    List<Module> findModulesByRoleId(String id);
}
