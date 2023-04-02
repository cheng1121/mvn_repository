package com.cheng.common.response;


public class CustomResult<T> {

    /**
     * 业务数据
     */
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "CustomResult{" +
                "data=" + data +
                '}';
    }
}
