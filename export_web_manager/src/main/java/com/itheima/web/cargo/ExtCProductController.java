package com.itheima.web.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.service.cargo.service.ExtCproductService;
import com.itheima.service.cargo.service.FactoryService;
import com.itheima.domian.cargo.ExtCproduct;
import com.itheima.domian.cargo.ExtCproductExample;
import com.itheima.domian.cargo.Factory;
import com.itheima.domian.cargo.FactoryExample;
import com.itheima.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCProductController extends BaseController {
    @Reference
    private ExtCproductService extCproductService;
    @Reference
    private FactoryService factoryService;
        @RequestMapping("list")
        public String list(String contractId,String contractProductId, @RequestParam(defaultValue = "1")int page , @RequestParam(defaultValue = "5")int size){
            //根据合同id查询商品
            ExtCproductExample example = new ExtCproductExample();
            example.createCriteria().andContractIdEqualTo(contractId);
            example.createCriteria().andContractProductIdEqualTo(contractProductId);
            PageInfo pageInfo = extCproductService.findAll(example,page,size);
            //查询货物生产货物的工厂
            FactoryExample factoryExample = new FactoryExample();
            factoryExample.createCriteria().andCtypeEqualTo("附件");

            List<Factory> factoryList = factoryService.findAll(factoryExample);
            request.setAttribute("page",pageInfo);
            request.setAttribute("factoryList",factoryList);
            request.setAttribute("contractId",contractId);
            request.setAttribute("contractProductId",contractProductId);
            return "cargo/extc/extc-list";
        }

        @RequestMapping("toUpdate")
        public String toUpdate(String id,String contractId,String contractProductId){
            ExtCproduct extCproduct = extCproductService.findById(id);
            request.setAttribute("extCproduct",extCproduct);
            //查询货物生产货物的工厂
            FactoryExample factoryExample = new FactoryExample();
            factoryExample.createCriteria().andCtypeEqualTo("附件");
            List<Factory> factoryList = factoryService.findAll(factoryExample);
            request.setAttribute("factoryList",factoryList);
            return "cargo/extc/extc-update?contractId="+contractId+"&contractProductId="+contractProductId;
        }
        @RequestMapping("/edit")
        public String edit(ExtCproduct extCproduct){
            extCproduct.setCompanyId(super.companyId);
            extCproduct.setCompanyName(super.companyName);
            if (StringUtils.isEmpty(extCproduct.getId())){
                extCproductService.save(extCproduct);
            }else{
                extCproductService.update(extCproduct);
            }
            return "redirect:/cargo/extCproduct/list.do?contractId="+extCproduct.getContractId()+"&contractProductId="+extCproduct.getContractProductId();
        }
        @RequestMapping("delete")
        public String delete(String id){
            ExtCproduct extCproduct = extCproductService.findById(id);
            extCproductService.delete(id);

            return "redirect:/cargo/extCproduct/list.do?contractId="+extCproduct.getContractId()+"&contractProductId="+extCproduct.getContractProductId();
        }
    }

