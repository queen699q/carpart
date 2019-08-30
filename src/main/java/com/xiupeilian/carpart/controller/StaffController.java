package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.vo.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private UserService userService;
    @RequestMapping("/staffList")
    public String staffList(Integer pageSize, Integer pageNum, UserListVo vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
        pageSize=pageSize==null?10:pageSize;
        pageNum=pageNum==null?1:pageNum;
        PageHelper.startPage(pageNum,pageSize);
        SysUser user=(SysUser)request.getSession().getAttribute("user");
        vo.setCompanyId(user.getCompanyId());
        List<SysUser> staffList =userService.findUserByNameAndPage(vo);
        PageInfo<SysUser> pageInfo=new PageInfo<>(staffList);
        request.setAttribute("pageInfo",pageInfo);
        request.setAttribute("vo",vo);
        System.out.println(staffList);
        return "staff/staffList";
    }

}
