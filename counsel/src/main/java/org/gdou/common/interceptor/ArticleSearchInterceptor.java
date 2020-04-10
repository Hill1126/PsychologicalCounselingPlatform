package org.gdou.common.interceptor;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 查询过滤器，当访问api：/article/search时，校验当前搜索是否清除翻页id的cookie
 * 同时，在请求结束后把新的翻页id和搜索keyword写入cookie中
 * @author HILL
 * @version V1.0
 * @date 2020/4/10
 **/
@Slf4j
public class ArticleSearchInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取keyWord关键词的cookie和入参校验当前是否为同一个关键词搜索
        HttpSession session = request.getSession();
        var sessionKeyWord = (String)session.getAttribute(ProjectConstant.SEARCH_KEY_WORD);
        String paraKeyWord = request.getParameter(ProjectConstant.SEARCH_KEY_WORD);
        //若不相等，则说明此次为新搜索，清除scrollId，放入新的关键词，放行通过
        if(!StringUtils.equals(sessionKeyWord,paraKeyWord)){
            session.setAttribute(ProjectConstant.SEARCH_KEY_WORD,paraKeyWord);
            session.removeAttribute(ProjectConstant.SCROLL_ID_NAME);
            return true;
        }
        return true;
    }

}
