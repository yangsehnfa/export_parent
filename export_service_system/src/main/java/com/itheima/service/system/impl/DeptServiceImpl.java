package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.DeptDao;
import com.itheima.domian.system.Dept;
import com.itheima.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;
    //保存
    public void save(Dept dept) {
        dept.setId(UtilFuns.getUUID());
        deptDao.save(dept);
    }
    //更新
    public void update(Dept dept) {
        deptDao.update(dept);
    }
    //删除
    public void delete(String id) {
        deptDao.delete(id);
    }
    //根据 id 查询
    public Dept findById(String id) {
        Dept dept = deptDao.findById(id);
        System.out.println(dept);
        return dept;
    }
    //分页查询
    public PageInfo findPage(String companyId, int page, int size) {
        PageHelper.startPage(page, size);
        List<Dept> depts = deptDao.findAll(companyId);
        return new PageInfo(depts);
    }
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }
}
