package org.gdou.common.constant;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/22
 **/
public class CommonDataStatus {

    /**
     * 删除状态，意味着该数据已不能使用
    **/
    public static final int DELETE = -1;
    /**
     * 编辑状态，意味着只有使用者能看到。
     */
    public static final int EDIT = 1;
    /**
     * 公布状态，此状态意味着使用者能够看到数据
    **/
    public static final int PUBLIC = 2;

}
