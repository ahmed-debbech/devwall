package com.debbech.devwall.model.ai;


public class WriteRequest {
    private String name;
    private String desc;

    @Override
    public String toString() {
        return "EmailRequest{" +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}
