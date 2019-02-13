package dianfan.wxqrcode;
public class AgentDTO {  
    private String resultCode;  
    private String desc;  
    private AgentReqView body;  
      
    public AgentDTO (){  
          
    }  
      
    public AgentDTO (String resultCode,String desc){  
        this.setResultCode(resultCode);  
        this.setDesc(desc);  
          
    }  
    public String getResultCode() {  
        return resultCode;  
    }  
    public void setResultCode(String resultCode) {  
        this.resultCode = resultCode;  
    }  
    public String getDesc() {  
        return desc;  
    }  
    public void setDesc(String desc) {  
        this.desc = desc;  
    }  
  
    public AgentReqView getBody() {  
        return body;  
    }  
  
    public void setBody(AgentReqView body) {  
        this.body = body;  
    }  
      
  
      
} 