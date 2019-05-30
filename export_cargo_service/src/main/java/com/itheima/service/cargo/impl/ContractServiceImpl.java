package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.service.cargo.service.ContractService;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.domian.cargo.Contract;
import com.itheima.domian.cargo.ContractExample;
import com.itheima.domian.cargo.ContractProductVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractDao contractDao;
    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Contract contract) {
        contract.setId(UtilFuns.getUUID());
        //创建时间
        contract.setCreateTime(new Date());
        //草稿
        contract.setState(0);
        //总金额
        contract.setTotalAmount(0d);
        //更新时间
        contract.setUpdateTime(new Date());
        //货物数量
        contract.setProNum(0);
        //附件数量
        contract.setExtNum(0);
        contractDao.insertSelective(contract);

    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);

    }

    @Override
    public PageInfo findAll(ContractExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Contract> contracts = contractDao.selectByExample(example);
        return new PageInfo(contracts);
    }
    //打印出货表
    @Override
    public List<ContractProductVo> findContractProductVoByShipTime(String companyId, String inputDate) {
        return contractDao.findContractProductVoByShipTime(companyId,inputDate);
    }
}
