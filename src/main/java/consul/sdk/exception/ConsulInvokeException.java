package consul.sdk.exception;

public class ConsulInvokeException extends  RuntimeException{
    private String code;

    private String detailMassage;

    public ConsulInvokeException(String code, String detailMassage) {
        this.code = code;
        this.detailMassage = detailMassage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetailMassage() {
        return detailMassage;
    }

    public void setDetailMassage(String detailMassage) {
        this.detailMassage = detailMassage;
    }
}
