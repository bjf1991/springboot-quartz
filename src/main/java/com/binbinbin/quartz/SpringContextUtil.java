package com.binbinbin.quartz;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
 
 
/**
 * 这个类是为了解决在普通类调用service的问题
 * 
 * @ClassName SpringContextUtil
 * @Description
 * @author gogym 189155278@qq.com
 * @date 2016-6-12
 * @content OfflineMessageService offlineMessageService = (OfflineMessageService) SpringContextUtil
 *          .getBean("offlineMessageService");
 */
@Component
public class SpringContextUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext; // Spring应用上下文
 
    // 下面的这个方法上加了@Override注解，原因是继承ApplicationContextAware接口是必须实现的方法
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        SpringContextUtil.applicationContext = applicationContext;
    }
 
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
 
    public static Object getBean(String name)
        throws BeansException
    {
        return applicationContext.getBean(name);
    }
 
    public static Object getBean(String name, Class requiredType)
        throws BeansException
    {
 
        return applicationContext.getBean(name, requiredType);
    }
 
    public static <T> T getBean(Class<T> clazz)
        throws BeansException
    {
        return applicationContext.getBean(clazz);
    }
 
    public static boolean containsBean(String name)
    {
        return applicationContext.containsBean(name);
    }
 
    public static boolean isSingleton(String name)
        throws NoSuchBeanDefinitionException
    {
        return applicationContext.isSingleton(name);
    }
 
    public static Class getType(String name)
        throws NoSuchBeanDefinitionException
    {
        return applicationContext.getType(name);
    }
 
    public static String[] getAliases(String name)
        throws NoSuchBeanDefinitionException
    {
        return applicationContext.getAliases(name);
    }
}
