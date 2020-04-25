package org.gdou.common.aop;

import lombok.Data;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/23
 **/
@Data
public class RequestErrorInfo {
    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private String exceptionMsg;
}
