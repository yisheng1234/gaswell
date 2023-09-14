package com.gaswell.common.log;

import com.alibaba.fastjson.JSON;
import com.gaswell.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

@Slf4j
@Component
@Aspect
//aop定义了一个切面，切面定义了切点和通知的关系
public class CacheAspect {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Pointcut("@annotation(com.gaswell.common.log.CacheAnnotation)")
    public void pt(){}

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp){
        try{
            //获取目标方法
            Signature signature = pjp.getSignature();
            MethodSignature methodSignature=(MethodSignature)pjp.getSignature();
            String methodName = signature.getName();
            String className=pjp.getTarget().getClass().getSimpleName();
            Class[] parameterTypes =new Class[pjp.getArgs().length];
            Object[] args=pjp.getArgs();
            String params="";
            for(int i=0;i<args.length;i++){
                if(args[i]!=null){
                    params+= JSON.toJSONString(args[i]);
                    parameterTypes[i]=args[i].getClass();
                }else{
                    parameterTypes[i]=null;
                }
            }
            if(StringUtils.isNotBlank(params)){
                params= DigestUtils.md5Hex(params);
            }
            Method method= methodSignature.getMethod();
            CacheAnnotation cacheAnnotation=method.getAnnotation(CacheAnnotation.class);
            long expire=cacheAnnotation.expire();
            String name=cacheAnnotation.name();
            String redisKey=name+"::"+className+"::"+methodName+"::"+params;
            String redisValue=redisTemplate.opsForValue().get(redisKey);
            if(StringUtils.isNotEmpty(redisValue)){
                log.info("走了缓存，{}，{}",className,methodName);
                return JSON.parseObject(redisValue, Result.class);
            }
            //调用方法
            Object proceed=pjp.proceed();
            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存,{},{}",className,methodName);
            return proceed;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return Result.fail(-999,"系统错误");
    }
}
