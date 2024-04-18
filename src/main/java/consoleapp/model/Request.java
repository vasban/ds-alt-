package model;

public class Request {

    private String code;
    private Accommodation accommodation;

    public Request(String code, Accommodation accommodation) {
        this.code = code;
        this.accommodation = accommodation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
