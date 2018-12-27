package com.sankuai.mall.iot.chuanyunapi.api.result;


import com.sankuai.mall.iot.chuanyunapi.util.JsonHelper;
import java.io.IOException;

public class Response {

    public static final Integer SERVER_ERROR = 500;

    /**
     * 成功
     */
    public static final Integer SUCCESS = 200;

    public static final Integer REQUEST_ERROR = 400;

    /**
     * 404错误
     */
    public static final Integer NORESOURCE = 404;

    /**
     * 参数错误
     */
    public static final Integer PARAMERRO = 5000;

    /**
     * 数据库错误
     */
    public static final Integer DATABASEERRO = 522;

    /**
     * 中间件错误
     */
    public static final Integer MIDDLEWAREERRO = 540;

    /**
     * 网络异常
     */
    public static final Integer NETWORKERRO = 549;

    /**
     * 未知错误
     */
    public static final Integer UNKWONERRO = 550;

    /**
     * 接口调用错误
     */
    public static final Integer APIERRO = 521;
    /**
     * 重复数据
     */
    public static final Integer DATAREPLICATE = 600;

    /**
     * 签名错误
     */
    public static final Integer SING_ERROR = 4001;

    private Integer status;
    private String message;
    private Object data;
    private String code;
    private String macid;

    public Response() {
    }

    public Response(Integer status, String message) {
        this(status, message, null);
    }

    public Response(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response(Integer status, String message, Object data, String macid) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.macid = macid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        if (SUCCESS == status) {
            this.message = "成功";
//            this.code = "SUCCESS";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMacid() {
        return macid;
    }

    public void setMacid(String macid) {
        this.macid = macid;
    }

    public static Response init(Integer status, String message) {
        return new Response(status, message);
    }

    @Override
    public String toString() {
        String dataString = data + "";
        try {
            dataString = JsonHelper.toJsonWithoutNull(data);
        } catch (IOException e) {

        }
        return "Response [status=" + status + ", message=" + message + ", data=" + dataString + ", code=" + code + "]";
    }

    public static final Response defaultResponse = new Response(SUCCESS, "OK");

    public static final Response defaultResponse(Object data) {
        Response response = defaultResponse;
        response.setData(data);
        return response;
    }
}
