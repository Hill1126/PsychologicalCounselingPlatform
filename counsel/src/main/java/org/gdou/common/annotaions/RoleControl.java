package org.gdou.common.annotaions;

import org.gdou.common.constant.user.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过注解指定对应的controller需要什么用户权限
 * @Author: HILL
 * @date: 2020/5/12 16:52
**/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleControl {

    int userType() default UserType.STUDENT;

}

