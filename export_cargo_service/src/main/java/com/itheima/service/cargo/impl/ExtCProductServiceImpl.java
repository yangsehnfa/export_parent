package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.service.cargo.service.ExtCproductService;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ContractProductDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domian.cargo.Contract;
import com.itheima.domian.cargo.ExtCproduct;
import com.itheima.domian.cargo.ExtCproductExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ExtCProductServiceImpl implements ExtCproductService {
    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ContractDao contractDao;
    @Override
    public void save(ExtCproduct extCproduct) {
        //设置附件的id
        extCproduct.setId(UtilFuns.getUUID());
        //设置附件的总金额
        double extcAmount=0;
        if (!StringUtils.isEmpty(extCproduct.getPrice())&&!StringUtils.isEmpty(extCproduct.getCnumber())){
            extcAmount=extCproduct.getPrice()*extCproduct.getCnumber();
        }
        //设置合同的金额
        extCproduct.setAmount(extcAmount);
        //设置合同的更新日期
        extCproduct.setCreateTime(new Date());
        //设置附件的创建时间
        extCproduct.setUpdateTime(new Date());
        //根据货物id查询附件所属的合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //设置合同的附件数
        contract.setExtNum(contract.getExtNum()+1);
        //设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount()+extcAmount);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //保存附件
        extCproductDao.insertSelective(extCproduct);


    }

    @Override
    public void update(ExtCproduct extCproduct) {
        //根据id查询数据库中的附件
        ExtCproduct dbextcProduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //设置更新时间
        extCproduct.setUpdateTime(new Date());
        //获取更新附件的总金额
        double newAmount=0;
        if (!StringUtils.isEmpty(extCproduct.getPrice())&&!StringUtils.isEmpty(extCproduct.getCnumber())){
            newAmount=extCproduct.getPrice()*extCproduct.getCnumber();
        }
        //附件原有的金额
        Double oldAmount = dbextcProduct.getAmount();
        //更新的附件没有的信息,用数据库原有的代替
        BeanUtils.copyProperties(dbextcProduct,extCproduct,new String[]{"id"});
        //获取合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-oldAmount+newAmount);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //更新附件
        extCproductDao.updateByPrimaryKeySelective(extCproduct);


    }

    @Override
    public void delete(String id) {
        //根据id查询附件
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        Double amount = extCproduct.getAmount();
        //根据id查询合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //设置合同的金额
        contract.setTotalAmount(contract.getTotalAmount()-amount);
        //设置合同数量的数量
        contract.setExtNum(contract.getExtNum()-1);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //删除附件
        extCproductDao.deleteByPrimaryKey(id);


    }

    @Override
    public ExtCproduct findById(String id) {
        return  extCproductDao.selectByPrimaryKey(id) ;
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(example);

        return new PageInfo(extCproducts);
    }
}
