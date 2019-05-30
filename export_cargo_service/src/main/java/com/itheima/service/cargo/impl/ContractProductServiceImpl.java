package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ContractProductDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domian.cargo.*;
import com.itheima.service.cargo.service.ContractProductService;
import com.itheima.service.cargo.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ContractProductServiceImpl implements ContractProductService {
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private FactoryService factoryService;
    @Override
    public void save(ContractProduct contractProduct) {
        //设置货物的id
        contractProduct.setId(UtilFuns.getUUID());
        Integer cnumber = contractProduct.getCnumber();
        Double price = contractProduct.getPrice();
        double amount=0d;
        if(!StringUtils.isEmpty(cnumber)&&!StringUtils.isEmpty(price)){
            amount=cnumber*price;
        }
        //设置货物的金额
        contractProduct.setAmount(amount);
        //保存货物
        contractProductDao.insertSelective(contractProduct);
        //根据合同id查询合同
        String contractId = contractProduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //设置合同货物的数量
        contract.setProNum(contract.getProNum()+1);
        contract.setTotalAmount(contract.getTotalAmount()+amount);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public void update(ContractProduct contractProduct) {
        //根据货物查询数据库的货物
        ContractProduct dbproduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        //更新的数据没有用数据库的
         //BeanUtils.copyProperties(dbproduct,contractProduct,new String[]{"id"});
        //获取货物原来的金额
        Double dbamount = dbproduct.getAmount();
        Double amount =0d;
        if(!StringUtils.isEmpty(contractProduct.getPrice())&&!StringUtils.isEmpty(contractProduct.getCnumber())){
            amount=contractProduct.getPrice()*contractProduct.getCnumber();
        }

        //查询合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //更新合同的金额
        contract.setTotalAmount(contract.getTotalAmount()+amount-dbamount);
       //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //更新货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);


    }

    @Override
    public void delete(String id) {
        //根据id查询货物
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        //获取货物的总金额
        Double amount = contractProduct.getAmount();
        //获取货物下的附件
        List<ExtCproduct> extCproducts = contractProduct.getExtCproducts();
        //遍历附件,获取附件的总金额
        double amount2=0d;
        for (ExtCproduct extCproduct : extCproducts) {
            amount2 += extCproduct.getAmount();
            //删除货物下的附件
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }
        //根据合同id查询合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-amount-amount2);
        contract.setProNum(contract.getProNum()-1);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //删除货物
        contractProductDao.deleteByPrimaryKey(id);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id) ;
    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(example);
        return new PageInfo(contractProducts);
    }

    @Override
    public void save(List<ContractProduct> cps) {
        for (ContractProduct contractProduct : cps) {
            //根据厂家名称查询厂家
            Factory factory= factoryService.findByName(contractProduct.getFactoryName());
            contractProduct.setFactoryId(factory.getId());
            this.save(contractProduct);
        }
    }
}
