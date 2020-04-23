package org.gdou.common.utils;

import org.gdou.common.constant.ProjectConstant;
import org.gdou.model.po.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
public class UserUtils {

    public static User getUserInRequest(HttpServletRequest request){
        return (User)request.getSession().getAttribute(ProjectConstant.USER_SESSION_KEY);
    }
}
