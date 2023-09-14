package com.gaswell.common.log;

import com.gaswell.controller.SystemLogController;
import com.gaswell.pojo.SystemLog;
import com.gaswell.pojo.User;
import com.gaswell.service.UserService;
import com.gaswell.utils.HttpContextUtils;
import com.gaswell.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

@Slf4j
@Component
@Aspect  //切面类

public class LogAspect {
    @Autowired
    private SystemLogController systemLogController;
    @Autowired
    private UserService userService;

    //切点
    @Pointcut("@annotation(com.gaswell.common.log.LogAnnotation)")
    public void  pt(){
    }

    //环绕通知
    //pt（）指定上面的切点。在这个切点旁进行环绕通知
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();

        //保存日志
        recordLog(joinPoint, beginTime);
        return result;
    }
    private void recordLog(ProceedingJoinPoint joinPoint, long beginTime) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        SystemLog systemLog=new SystemLog();

        //获取操作内容
        systemLog.setOperate_content("执行了"+logAnnotation.module()+"模块下的"+"("+logAnnotation.operator()+")操作");
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //  获取操作时间
        String bt = dateformat.format(beginTime);
        systemLog.setOperate_time(bt);


        //获取请求的参数
        Object[] args = joinPoint.getArgs();
        //String id = JSON.toJSONString(args[0]);


        String userName=String.valueOf(args[0]);

        User user = userService.selectUserByuserName(userName);

        if(user==null){

        }else {
            systemLog.setOperate_realname(user.getUser_realname());
            systemLog.setOperate_userid(user.getUser_id());

            //获取request 设置IP地址
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            systemLog.setOperate_ip(RequestUtil.getRequestIP(request));

            systemLogController.insertOne(systemLog);
        }
    }

}
