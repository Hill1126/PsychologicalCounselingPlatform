package org.gdou.common.result;

/**
 * 响应码枚举，参考HTTP状态码的语义
 * @author HILL
 */
public class ResultCode {

    public static final int SUCCESS =200;
    public static final int FAIL =400;
    public static final int UNAUTHORIZED =401;
    public static final int NOT_FOUND =404;
    public static final int INTERNAL_SERVER_ERROR =500;

}
