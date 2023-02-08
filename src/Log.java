public class Log {
    private String statusCode;
    private String url;
    private String webBrowser;
    private String callTime;

    private Log(Builder builder) {
        this.statusCode = builder.statusCode;
        this.url = builder.url;
        this.webBrowser = builder.webBrowser;
        this.callTime = builder.callTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getUrl() {
        return url;
    }

    public String getWebBrowser() {
        return webBrowser;
    }

    public String getCallTime() {
        return callTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean checkStatusCode(){ // 상태 코드 체크
        if(this.getStatusCode().equals("200")){
            return true;
        }else{
            return false;
        }
    }

    public static class Builder {
        private String statusCode;
        private String url;
        private String webBrowser;
        private String callTime;


        private Builder() {};

        public Builder statusCode(String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }
        public Builder webBrowser(String webBrowser){
            this.webBrowser = webBrowser;
            return this;
        }
        public Builder callTime(String callTime){
            this.callTime = callTime;
            return this;
        }

        public Log build(){
            return new Log(this);
        }
    }
}
