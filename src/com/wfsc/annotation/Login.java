package com.wfsc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 针对前台需要登录才能访问的Action或者方法设计的Annotation
 * 可以作用于class或者method，当作用于class的时候，表示整个Action的方法都需要登录，作用与method时，表示该方法需要登录才能访问
 * class级别优先
 * 
 * 后台管理该Annotation不生效
 * 
 * @author Xiaojiapeng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value= {ElementType.METHOD,ElementType.TYPE})
public @interface Login {

}
