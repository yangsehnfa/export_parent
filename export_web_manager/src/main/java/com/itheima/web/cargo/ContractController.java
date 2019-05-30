package com.itheima.web.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.service.cargo.service.ContractService;
import com.itheima.domian.cargo.Contract;
import com.itheima.domian.cargo.ContractExample;
import com.itheima.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

        @Reference
        private ContractService contractService;
        @RequestMapping("/list")
        public String list(@RequestParam(name = "page",defaultValue = "1") int page,
                                 @RequestParam(name = "size",defaultValue = "5") int size){
            ContractExample contractExample = new ContractExample();
            contractExample.createCriteria().andCompanyIdEqualTo(super.companyId);
            PageInfo pageInfo=contractService.findAll(contractExample,page,size);
           request.setAttribute("page",pageInfo);
            return "cargo/contract/contract-list";
        }
        /**
         * 前往修改页面
         * @return
         * @throws Exception
         */
        @RequestMapping("/toUpdate")
        public ModelAndView toUpdate(String id)throws Exception{
            //1.根据 id 查询要修改的对象
            Contract contract = contractService.findById(id);
            //2.把查询出来的存入 ModelAndView 中
            ModelAndView mv = new ModelAndView();
            mv.addObject("contract",contract);
            //3.设置响应页面
            mv.setViewName("cargo/contract/contract-update");
            //4.返回
            return mv;
        }
        /**
         * 前往新增页面
         * @return
         */
        @RequestMapping(value="/toAdd")
        public String toAdd()throws Exception{
            return "cargo/contract/contract-add";
        }
        /**
         * 增加或修改
         */
        @RequestMapping("/edit")
        public String edit(Contract contract){
            contract.setCompanyId(super.companyId);
            contract.setCompanyName(super.companyName);
            //id 为空，保存购销合同
            if(StringUtils.isEmpty(contract.getId())) {
                contractService.save(contract);
            }else{

                contractService.update(contract);
            }
            return "redirect:/cargo/contract/list.do";
        }
        /**
         * 删除
         */
        @RequestMapping("/delete")
        public String delete(String id){
            contractService.delete(id);
            return "redirect:/cargo/contract/list.do";
        }
        /**
         * 显示购销合同详情页面
         * @return
         */
        @RequestMapping("/toView")
        public ModelAndView toView(String id){
            //1.创建返回值对象
            ModelAndView mv = new ModelAndView();
            //2.设置视图名称
            mv.setViewName("cargo/contract/contract-view");
            //3.使用合同号查询
            Contract contract = contractService.findById(id);
            //4.设置响应内容
            mv.addObject("contract",contract);
            //5.返回
            return mv;
        }
    @RequestMapping("/submit")
    public String submit(String id){
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(1);

        contractService.update(contract);

        return  "redirect:/cargo/contract/list.do" ;
    }
    @RequestMapping("/cancel")
    public String cancel(String id){
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(0);
        contractService.update(contract);
        return  "redirect:/cargo/contract/list.do" ;
    }
    }

