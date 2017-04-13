package cn.web;

import cn.dto.LoginForm;
import cn.entity.User;
import cn.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by ZLY on 2017-04-07.
 */
@Controller

public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private  HttpServletRequest request;
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String Login(){
        return "login";
    }

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public String doLogin(LoginForm loginForm){
        if (loginForm!=null){
            try {
                if (userService.hasMatchUser(loginForm.getUserId(),loginForm.getPassword())){
                    User user = userService.findUserById(loginForm.getUserId());
                    user.setUserLastIp(getIpAddr(request));
                    userService.loginSuccess(user);
                    request.getSession().setAttribute("user",user);
                    return "redirect:/index";
                }
            } catch (Exception e) {
                return "redirect:/login";
            }
        }
        return "redirect:/login";
    }
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        User user = (User)request.getSession().getAttribute("user");
       if (user==null||user.getUserId()==null||user.getUserId()==0){
            return "redirect:/login";
        }
        return "index";
    }

    /**
     * 获取真实的客户端ip地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")||ip.equals("localhost")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }


        return ip;
    }

}
