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
//        //��ȡ����Ŀ����Դ·��
//        String path =httpServletRequest.getRequestURI();
//        //�ж�·���Ƿ���Ҫ��¼�ſɷ���
//        if(path.contains("login")||path.contains("upLoad")){
//            return true;
//        }else {
//            //��ζ����Ҫ����session�����Լ�Ȩ�޿���
//            HttpSession session=httpServletRequest.getSession(false);
//            if(null==session){
//                //sessionΪnull
//                httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
//                        +"/login/tologin");
//                return false;
//            }else {
//                if (null==session.getAttribute("user")){
//                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath()
//                            +"/login/tologin");
//                    return false;
//                }else {
//                    //��ζ���û���¼����session���������
//                    SysUser user=(SysUser) session.getAttribute("user");
//                    //��ѯ�����û���Ӧ��Ȩ��
//                    List<Menu> menuList =userService.findMenusById(user.getId());
//                    //ÿһ�������˵�����һ��Ȩ�޹ؼ��֣�url�ķְ�·����
//                    boolean check =false;
//                    for(Menu menu:menuList){
//                        //����û�������·���������Լ��ĵ���Ȩ�޹ؼ���
//                        if(path.contains(menu.getMenuKey())){
//                            check=true;
//                        }
//                    }
//                 //���check��true�������������ʣ������false�����ǷǷ�����
//                    if(check){
//                        return true;
//
//                    }else {
//                        //��¼�ǳɹ������ǷǷ�����
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
