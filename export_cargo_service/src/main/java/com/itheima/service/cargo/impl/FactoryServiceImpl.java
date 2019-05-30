package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.service.cargo.service.FactoryService;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.FactoryDao;
import com.itheima.domian.cargo.Factory;
import com.itheima.domian.cargo.FactoryExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
@Service
public class FactoryServiceImpl implements FactoryService {
    @Autowired
    private FactoryDao factoryDao;
    @Override
    public void save(Factory factory) {
        factory.setId(UtilFuns.getUUID());
        factory.setCreateTime(new Date());
        factoryDao.insertSelective(factory);
    }

    @Override
    public void update(Factory factory) {
        factory.setUpdateTime(new Date());
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    @Override
    public void delete(String id) {
        factoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Factory> findAll(FactoryExample example) {

        return factoryDao.selectByExample(example);
    }

    @Override
    public Factory findByName(String factoryName) {
        FactoryExample example = new FactoryExample();
        example.createCriteria().andFactoryNameEqualTo(factoryName);
        return factoryDao.selectByExample(example).get(0);
    }
}
