package com.itheima.web.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //1.定义返回值对象
        ModelAndView mv = new ModelAndView();
        //2.设置响应页面
        mv.setViewName("error");
        //3.设置错误消息
        //判断ex是不是自定义异常类型
        if(e instanceof  CustomerException){
            //自定义异常类型
            mv.addObject("errorMsg",e.getMessage());
        }else {
            e.printStackTrace();//打印堆栈信息到控制台！
            mv.addObject("errorMsg", "服务器忙！");
        }
        return mv;
    }
}
