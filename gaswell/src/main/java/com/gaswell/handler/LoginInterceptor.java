package com.gaswell.handler;

import com.alibaba.fastjson.JSON;
import com.gaswell.pojo.User;
import com.gaswell.service.LoginService;
import com.gaswell.utils.UserThreadLocal;
import com.gaswell.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /*
    * 1.需要判断 请求的接口路径 是否为handle method（controller方法）
    * 2.判断token是否为空，如果为空，未登录
    * 3.如果token不为空。登陆验证  loginService checkToken
    * 4.如果验证成功，放行
    * */
    @Autowired
    LoginService loginService;
    @Override
    //重写 preHandle，在执行controller方法之前执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception {
        //handle是代表请求的接口路径
        //判断请求的接口路径  是否为handlemethod（controller中的方法），如果不是（例如请求资源的）就无需拦截
        //instanceof 判断左边的对象是否是右边的类的实例
        if(!(handler  instanceof HandlerMethod)){
            //不是控制器方法，放行
            return true;
        }
//        log.info("=================request start===========================");
        String token=request.getHeader("Authorization");
//        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
//        log.info("request uri:{}",requestURI);
//        log.info("request method:{}",request.getMethod());
//        log.info("token:{}", token);
//        log.info("handler:{}",handler);
//        log.info("=================request end===========================");
        if(StringUtils.isBlank(token)) {
            Result result = Result.fail(111, "未登录");
            //让浏览器知道我们返回的是json数据
            response.setContentType("application/json;charset=utf-8");
            //转成json
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        User user = loginService.checkToken(token);
        if(user == null){
            Result result = Result.fail(111, "未登录");
            //让浏览器知道我们返回的是json数据
            response.setContentType("application/json;charset=utf-8");
            //转成json
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //在放行之后，怎么在处理本次请求的controller中直接得到用户信息呢？使用threadLocal
        UserThreadLocal.put(user);
        /*暂时使用testcontroller进行测试*/
        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果用完之后，不删除ThreadLocal中的信息，会有内存泄露的危险
        UserThreadLocal.remove();
    }
}
