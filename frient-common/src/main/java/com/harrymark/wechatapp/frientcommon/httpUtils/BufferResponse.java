package com.harrymark.wechatapp.frientcommon.httpUtils;

/**
 * http响应封装
 * Created by haoweima on 2019/3/14.
 */
public class BufferResponse<T> {

    private String type = "RESPONSE";

    private Header header;
    private T body;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void setResult(HttpResultEnum result){
        header.setResponseCode(result.getCode());
        header.setResponseMsg(result.getMsg());
    }

    public static class Header{
        private String faceCode;
        private String userId;
        private String token;
        private String clientInfo;
        private String responseCode;
        private String responseMsg;

        public String getFaceCode() {
            return faceCode;
        }

        public void setFaceCode(String faceCode) {
            this.faceCode = faceCode;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getClientInfo() {
            return clientInfo;
        }

        public void setClientInfo(String clientInfo) {
            this.clientInfo = clientInfo;
        }

        public String getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(String responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseMsg() {
            return responseMsg;
        }

        public void setResponseMsg(String responseMsg) {
            this.responseMsg = responseMsg;
        }
    }
}
