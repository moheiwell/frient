package com.harrymark.wechatapp.frientcommon.httpUtils;

/**
 * http请求封装
 * Created by haoweima on 2019/3/14.
 */
public class BufferRequest<T> {

    private String type = "REQUEST";

    private Header header;
    private T body;

    public static class Header{
        private String faceCode;
        private String userId;
        private String token;
        private String clientInfo;

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
    }

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
}
