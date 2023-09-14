package com.gaswell.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class PojoConvertParamsUtils {

    public static Object convert(Object pojo,Object param) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        //获取实体类对应的param对象的实例
        System.out.println(pojo);
        String className= pojo.getClass().getSimpleName();
        Object params = getParamInstance(className);
        try {
        Field[] field = pojo.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组

            // 遍历所有属性
            for (int j = 0; j < field.length; j++) {
                // 获取属性的名字
                String fname = field[j].getName();
                // 将属性的首字符大写，方便构造get，set方法
                String name = fname.substring(0, 1).toUpperCase() + fname.substring(1);
                // 获取属性的类型

                String type = field[j].getGenericType().toString();
                // 如果type是一种类型，则前面包含"class "，后面跟类名
                if (type.equals("class java.lang.String")) {
                    //获得get方法
                    Method m = pojo.getClass().getMethod("get" + name);
                    // 调用getter方法获取属性值
                    String value = (String) m.invoke(pojo);
                    System.out.println(value);
                    //.....处理开始........
                    if(StringUtils.isNotBlank(value)){
                        if(!value.contains("-")){
                            double v=0;
                            Method m1=null;
                            try {
                                 v = Double.parseDouble(value);
                                 m1 = params.getClass().getMethod("set" + name, Double.class);
                                m1.invoke(params,v);
                            }catch (Exception e){
                                m1 = params.getClass().getMethod("set" + name, String.class);
                                m1.invoke(params,value);
                            }

                        }else{
                            List<Double> upAndDown=split(value);
                            Method[] method=params.getClass().getDeclaredMethods();
//                            System.out.println("==========");
//                            for(Method t : method){
//                                System.out.println(t);
//                            }
//                            System.out.println("============");

                            //  !!!!!!!!!!获取方法时，如果这个方法有参数，要在获取时加上参数的类
                            Method setDown=params.getClass().getMethod("setDown_"+fname,upAndDown.get(0).getClass());
                            Method setUp=params.getClass().getMethod("setUp_"+fname,upAndDown.get(1).getClass());
                            setDown.invoke(params,upAndDown.get(0));
                            setUp.invoke(params,upAndDown.get(1));
                        }
                    }
                }
//                if (type.equals("class java.lang.Integer")) {
//                    Method m = pojo.getClass().getMethod("get" + name);
//                    Integer value = (Integer) m.invoke(pojo);
//                    if (value == null) {
//                        m = pojo.getClass().getMethod("set"+name,Integer.class);
//                        m.invoke(pojo, 1);
//                    }
//                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return params;
    }

    public static Object getParamInstance(String value) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        System.out.println(value);
        String path="com.gaswell.vo.params."+value+"Param";
        Class cls= Class.forName(path);
        Object paramInstance=cls.getDeclaredConstructor().newInstance();
        return paramInstance;
    }
    public  static List split(String str){
//        System.out.println(str);
        List<Double> list=new ArrayList<>();
        String[] str1=str.split("-");
//        if(str1[0].contains("/") ){
//            str1[0]=str1[0].replace("/","");
//            str1[1]= str1[1].replace("/","");
//        }
//        if (str1[0].contains(":")){
//            str1[0]=str1[0].replace(":","");
//            str1[1]=str1[1].replace(":","");
//        }
//        System.out.println(str1[0]);
//        System.out.println(str1[1]);
        list.add(Double.valueOf(str1[0]));
        list.add(Double.valueOf(str1[1]));
        return list;
    }
}


