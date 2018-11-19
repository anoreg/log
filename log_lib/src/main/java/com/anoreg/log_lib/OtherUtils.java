package com.anoreg.log_lib;

public class OtherUtils {

    /**
     * 获取目前的堆栈信息
     *
     * @return
     */
    public static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[3];
    }

    /**
     * 获取调用者信息
     *
     * @return
     */
    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
