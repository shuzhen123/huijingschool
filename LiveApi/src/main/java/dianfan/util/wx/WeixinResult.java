package dianfan.util.wx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.NONE)
public class WeixinResult implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "return_code")
	private String return_code;
	@XmlElement(name = "return_msg")
	private String return_msg;

	public WeixinResult() {
	}

	public WeixinResult(String return_code) {
		this.return_code = return_code;
	}

	public WeixinResult(String return_code, String return_msg) {
		this.return_code = return_code;
		this.return_msg = return_msg;
	}

	@XmlTransient
	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	@XmlTransient
	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

}
