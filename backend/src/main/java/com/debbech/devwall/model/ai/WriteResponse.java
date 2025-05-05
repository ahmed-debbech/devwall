package com.debbech.devwall.model.ai;


public class WriteResponse {

    private String plainResponse;
    private String ReqName;
    private String responseGeneratedAt;
    private long startTs;

    public String getResponseGeneratedAt() {
        return responseGeneratedAt;
    }

    public void setResponseGeneratedAt(String responseGeneratedAt) {
        this.responseGeneratedAt = responseGeneratedAt;
    }

    public long getStartTs() {
        return startTs;
    }

    public void setStartTs(long startTs) {
        this.startTs = startTs;
    }

    public long getEndTs() {
        return endTs;
    }

    public void setEndTs(long endTs) {
        this.endTs = endTs;
    }

    @Override
    public String toString() {
        return "WriteResponse{" +
                "plainResponse='" + plainResponse + '\'' +
                ", ReqName='" + ReqName + '\'' +
                ", responseGeneratedAt='" + responseGeneratedAt + '\'' +
                ", startTs=" + startTs +
                ", endTs=" + endTs +
                '}';
    }

    private long endTs;

    public String getPlainResponse() {
        return plainResponse;
    }

    public void setPlainResponse(String plainResponse) {
        this.plainResponse = plainResponse;
    }

    public String getReqName() {
        return ReqName;
    }

    public void setReqName(String reqName) {
        ReqName = reqName;
    }
}
