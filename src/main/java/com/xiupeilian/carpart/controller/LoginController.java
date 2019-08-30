package com.xiupeilian.carpart.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.*;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.CityService;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.task.MailTask;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    RedisTemplate jedis;
    @RequestMapping("/tologin")
    public  String toLogin(){
        return "login/login";
    }
    @RequestMapping("/login")
    public void login(LoginVo vo, HttpServletRequest request, HttpServletResponse response) throws Exception{

        //�ж���֤���Ƿ���ȷ
        String code=(String)request.getSession().getAttribute(SysConstant.VALIDATE_CODE);
        if(vo.getValidate().toUpperCase().equals(code.toUpperCase())){
            //��֤����ȷ
            Subject subject= SecurityUtils.getSubject();
            System.out.println("mima:"+vo.getPassword());
            UsernamePasswordToken token=new UsernamePasswordToken(vo.getLoginName(),vo.getPassword());
            try {
                subject.login(token);
            }catch (Exception e){
                //�û����������
                response.getWriter().write(e.getMessage());
                return ;
            }
            //��ȡ������û���Ϣ
            SysUser user=(SysUser) SecurityUtils.getSubject().getPrincipal();
            //��spring-session
            request.getSession().setAttribute("user",user);
            response.getWriter().write("3");
        }else{
            //��֤�����
            response.getWriter().write("1");
        }
    }
    @RequestMapping("/noauth")
    public String noauth(){
        return "exception/noauth";
    }

    @RequestMapping("/forgetPassword")
    public  String forgetPassword(){
        return "login/forgetPassword";
    }
    @RequestMapping("/getPassword")
    public void  getPassword(HttpServletResponse response,LoginVo vo)throws  Exception{
        //��ѯsys_user�������Լ��˺��Ƿ�ƥ��
        SysUser user=userService.findUserByLoginNameAndEmail(vo);
        if (null==user){
            response.getWriter().write("1");
        }else {
            //����������0.01
            String password =new Random().nextInt(899999)+100000+"";
            //�޸����ݿ� 0.5s
            user.setPassword(SHA1Util.encode(password));
            userService.updateUser(user);

            SimpleMailMessage message =new SimpleMailMessage();
            message.setFrom("810king@sina.cn");
            message.setTo(user.getEmail());
            message.setSubject("�����������г������һع���:");
            message.setText("������������"+password);

            MailTask mailTask=new MailTask(mailSender,message);
            //���̳߳�ȥִ�и�����Ϳ�����
            executor.execute(mailTask);

            response.getWriter().write("2");
        }
    }

    //ע��
    @RequestMapping("/toRegister")
    public String toRegister(HttpServletRequest request){
        List<Brand> brandList=brandService.findBrandAll();
        List<Parts> partList=brandService.findPartAll();
        List<Prime> primeList =brandService.findPrimeAll();
        request.setAttribute("brandList",brandList);
        request.setAttribute("partList",partList);
        request.setAttribute("primeList",primeList);
        return "login/register";
    }

//У�� juh7
    @RequestMapping("/checkLoginName")
    public void checkLoginName(String loginName,HttpServletResponse response) throws IOException {
        //�����˺�ȥ���ݿ��ѯ�������˺��Ƿ����
        SysUser user=userService.findUserByLoginName(loginName);
        if (null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }

    }

    @RequestMapping("/checkPhone")
    public  void checkPhone(String phone,HttpServletResponse response)throws Exception{
        //�����ֻ���ȥ���ݿ��ѯ
        SysUser user= userService.findUserByPhone(phone);
        if (null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }

    @RequestMapping("/checkEmail")
    public void checkEmail(String email,HttpServletResponse response)throws Exception{
        SysUser user=userService.findUserByEmail(email);
        if (null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }

    @RequestMapping("/checkCompanyname")
    public  void  checkCompanyname(String companyname,HttpServletResponse response) throws Exception{
        Company company=userService.findCompanyByName(companyname);

        if (null==company){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
    }
    @RequestMapping("/getCity")
    public @ResponseBody
    List<City> getCity(Integer parentId){
        parentId=parentId==null?SysConstant.CITY_CHINA_ID:parentId;
        List<City> cities=cityService.findCitiesByParentId(parentId);
          return cities;
    }

    @RequestMapping("/register")
    public String register(RegisterVo vo){
        //����
        //�Ȳ�����ҵ�����ٲ����û���,��Ҫ��һ��������п���
        userService.addRegsiter(vo);
        return "redirect:tologin";
    }
    @RequestMapping("/myUpload")
    public String upload(HttpServletRequest request) {
        //Items item=itemService.findItemByid(1);
        //request.setAttribute("imgurl",item.getPicUrl());

        return "login/index";
    }

    @RequestMapping("/smsControllter")
    public void getSms(String phone){
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5XIIvCBneym8", "EYK9gfK4PNdzTUpltFrN11hY8hjqYS");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        String code =new Random().nextInt(899999)+100000+"";
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName","\u4fee\u914d\u8fde\u6c7d\u4fee");
        request.putQueryParameter("TemplateCode", "SMS_172889008");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            jedis.boundValueOps(phone).set(code);
            jedis.expire(phone,5, TimeUnit.DAYS.MINUTES);

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }


    }
    //У����֤��
    @RequestMapping("/smsQuery")
    public void checkCode(HttpServletResponse response,String phone,String code) throws IOException {
        String code1=(String)jedis.boundValueOps(phone).get();

        if (null==code1){
            response.getWriter().write("1");
        }
        if (code1.equals(code)){
            System.out.println("code1"+code1);
            System.out.println("phone"+phone);
            System.out.println("code"+code+"sssss");
            response.getWriter().write("2");
        }
        if(!code1.equals(code)) {
            System.out.println("code1"+code1);
            System.out.println("phone"+phone);
            System.out.println("code"+code+"aaaaa");
            response.getWriter().write("3");
        }

    }
    @RequestMapping("/test")
    public void test(int id){
        cityService.deleteCityById(id);
    }

}