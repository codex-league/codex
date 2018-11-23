package org.codex.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 获得spring上下文
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext newContext) throws BeansException {
        SpringContextUtil.setContext(newContext);
    }

    private static void setContext(ApplicationContext newContext) {
        if (context == null) {
            context = newContext;
        }
    }

    @Deprecated
    public static Object getBean(String beanName) throws BeansException {
        return context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> c) throws BeansException {
        return context.getBean(c);
    }

    public static <T> T getBean(String beanId, Class<T> clazz) throws BeansException {
        return context.getBean(beanId, clazz);
    }


    /**
     * 根据错误code得到错误明细
     *
     * @param code        错误代码
     * @param params      参数
     * @param defaultDesc 默认描述
     * @param local       本地语言环境
     * @return
     */
    public static String getMessage(String code, Object[] params, String defaultDesc, Locale local) {
        return context.getMessage(code, params, defaultDesc, local);
    }

    /**
     * 根据错误code得到错误明细
     *
     * @param code   错误代码
     * @param params 参数
     * @param local  本地语言环境
     * @return
     */
    public static String getMessage(String code, Object[] params, Locale local) {
        return context.getMessage(code, params, local);
    }

}
