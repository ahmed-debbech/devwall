package com.debbech.devwall.model.ai;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
public class WriteResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4096)
    private String plainResponse;
    private String ReqName;
    private String responseGeneratedAt;
    private long startTs;
    private long endTs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
