package com.mybatisPlus.response;

import java.io.Serializable;

/***
 * @ClassName R
 * @Description: 响应体
 * @Author Ahuan
 * @Date 2020/5/8 
 * @Version V1.0
 **/
public class R<T> implements Serializable {
    private static final Integer SUCCESS = 0;
    private static final String SUCCESS_MSG = "SUCCESS";
    private static final Integer FIALED = 1;

    /**
     * code
     */
    private Integer code;

    /**
     * message
     */
    private String message;

    /**
    *data
    */
    private T data ;

    public static  <T> R ok(){
        return  R.ok(null);
    }

    public static  <T> R ok(T data){
        return  R.ok(SUCCESS,SUCCESS_MSG,data);
    }

    public static  <T> R ok(Integer code,String message){
        return  new R(code,message,null);
    }

    public static  <T> R ok(Integer code,String message,T data){
        return  new R(code,message,data);
    }

    public static  <T> R error(String message){
        return  new R(FIALED,message,null);
    }

    public static  <T> R error(Integer code,String message){
        return  new R(code,message,null);
    }

    public static  <T> R error(Integer code,String message,T data){
        return  new R(code,message,data);
    }

    private R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}