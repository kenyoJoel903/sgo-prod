package sgo.service;

import java.util.ResourceBundle;

//ticket 9000003025
public class ServiceProperties {
	
	public String endPointWS;
	public String userWS;
	public String passWS;

    public ServiceProperties() {

    	ResourceBundle rb = ResourceBundle.getBundle("jdbc");
    	
    	endPointWS = rb.getString("endPointWS");
    	userWS = rb.getString("userWS");
    	passWS = rb.getString("passWS");
    }

	public String getEndPointWS() {
		return endPointWS;
	}

	public void setEndPointWS(String endPointWS) {
		this.endPointWS = endPointWS;
	}

	public String getUserWS() {
		return userWS;
	}

	public void setUserWS(String userWS) {
		this.userWS = userWS;
	}

	public String getPassWS() {
		return passWS;
	}

	public void setPassWS(String passWS) {
		this.passWS = passWS;
	}
	
}
