package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Dymsn;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.Notice;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.DymsnService;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/index")
public class IndexController  {
    @Autowired
    private UserService userService;
    @Autowired
    private DymsnService dymsnService;

    @RequestMapping("/index")
    public String index(){
        return  "index/index";
    }
    @RequestMapping("/top")
    public  String top(HttpServletRequest request){
        SimpleDateFormat sdf =new SimpleDateFormat();
        String now =sdf.format(new Date());
        request.setAttribute("now",now);
        return "index/top";
    }
    @RequestMapping ("/navigation")
    public  String navigation(HttpServletRequest request){
        //取到用户id
        SysUser user=(SysUser)request.getSession().getAttribute("user");
         int id=user.getId();
         //查询到登录用户的所有导航菜单
        List<Menu> menuList =userService.findMenusById(id);
        request.setAttribute("menuList",menuList);
        return  "index/navigation";
    }

    @RequestMapping("/dymsn")
    public String dymsn(HttpServletRequest request){
        List<Dymsn> list =dymsnService.findDymsns();
        request.setAttribute("list",list);
        return "index/dymsn";
    }
   @RequestMapping("/notice")
    public  String notice(Integer pageSize, Integer pageNum,HttpServletRequest request){
        pageSize=pageSize==null?10:pageSize;
        pageNum=pageNum==null?1:pageNum;
       PageHelper.startPage(pageNum,pageSize);
       //查询全部
       List<Notice> list =dymsnService.findNotice();
       PageInfo<Notice> page=new PageInfo<>(list);
       request.setAttribute("page",page);
       return "index/notoce";

   }

}
