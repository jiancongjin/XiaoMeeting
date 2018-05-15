package cn.li.dto;

public class ResponseData {
    private int status;

    private  String info;

    private Object object;

    public ResponseData(){

    }

    public ResponseData(int status, String info) {
        this.status = status;
        this.info = info;
    }

    public ResponseData(int status, String info, Object object) {

        this.status = status;
        this.info = info;
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
