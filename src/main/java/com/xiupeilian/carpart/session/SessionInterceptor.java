package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    /**
     *
     * @Description
     * @param httpServletResponse
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler)  throws Exception {
//        //获取请求目标资源路径
//        String path =httpServletRequest.getRequestURI();
//        //判断路径是否需要登录才可访问
//        if(path.contains("login")||path.contains("upLoad")){
//            return true;
//        }else {
//            //意味着需要进行session过滤以及权限控制
//            HttpSession session=httpServletRequest.getSession(false);
//            if(null==session){
//                //session为null
//                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
//                        +"/login/tologin");
//                return false;
//            }else {
//                if (null==session.getAttribute("user")){
//                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
//                            +"/login/tologin");
//                    return false;
//                }else {
//                    //意味着用户登录过（session）过滤完毕
//                    SysUser user=(SysUser) session.getAttribute("user");
//                    //查询出该用户对应的权限
//                    List<Menu> menuList =userService.findMenusById(user.getId());
//                    //每一个导航菜单都有一个权限关键字（url的分包路径）
//                    boolean check =false;
//                    for(Menu menu:menuList){
//                        //如果用户的请求路径包含了自己的导航权限关键字
//                        if(path.contains(menu.getMenuKey())){
//                            check=true;
//                        }
//                    }
//                 //如果check是true，就是正常访问，如果是false，就是非法访问
//                    if(check){
//                        return true;
//
//                    }else {
//                        //登录是成功，但是非法访问
//                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
//                                +"/login/noauth");
//                        return false;
//                    }
//
//                }
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletRequest.setAttribute("ctx",httpServletRequest.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
