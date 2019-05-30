package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domian.system.Dept;

import java.util.List;

public interface DeptService {
    //保存
    public void save(Dept dept);
    //更新
    public void update(Dept dept);
    //删除
    public void delete(String id);
    //根据 id 查询
    public Dept findById(String id);
    //查询全部
    public List<Dept> findAll(String companyId);
    //分页查询
    public PageInfo findPage(String companyId, int page, int size);
}
