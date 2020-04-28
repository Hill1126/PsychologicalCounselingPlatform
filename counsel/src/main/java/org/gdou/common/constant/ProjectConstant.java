package org.gdou.common.constant;

/**
 * 项目常量
 * @Author: HILL
 * @date: 2020/3/3 22:11

**/
public final class ProjectConstant {

    public static final String USER_SESSION_KEY = "USER";
    public static final String ORDER_KEY = "COUNSEL_ORDER:";
    public static final Long ORDER_KEY_EXPIRE = 1000*60*15L;
    public static final String ARTICLE_KEY = "ARTICLE:";

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

}
