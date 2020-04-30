package org.gdou.common.constant;

/**
 * 项目常量
 * @Author: HILL
 * @date: 2020/3/3 22:11

**/
public final class ProjectConstant {

    public static final String USER_SESSION_KEY = "USER:";
    public static final String ORDER_KEY = "COUNSEL_ORDER:";
    public static final Long ORDER_KEY_EXPIRE = 1000*60*15L;
    public static final String ARTICLE_KEY = "ARTICLE:";

    /**
     * 用户redis相关
    **/
    public static final String TOKEN_NAME = "USER_TOKEN";
    /**
     * 用户token在redis的存活过期时间
     */
    public static final Long USER_EXPIRE = 1000*60*60*24*3L;

    /** es相关常量
    */
    public static final String ARTICLE_INDEX_NAME = "article";
    public static final String ARTICLE_TYPE_NAME = "doc";

    /**
     * 搜索相关变量
     */
    public static final String SCROLL_ID_NAME = "scrollId";
    public static final Long SCROLL_ID_EXPIRE_TIME = 1*60*5L;
    public static final String SEARCH_KEY_WORD = "keyWord";

    /**
     * 爬虫变量相关
    **/
    public static final String CRAWL_URL_KEY = "CRAWL_URL";
    public static final String DEFAULT_CATEGORY = "心理知识";



}
