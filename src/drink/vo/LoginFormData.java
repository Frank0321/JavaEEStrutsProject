package drink.vo;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class LoginFormData extends ActionForm {
	
	private String id;
	private String pwd;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/*
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors actionErrors = new ActionErrors();
		if (id == null || id.length()<1){
			actionErrors.add("id", new ActionMessage("error.id"));
		}
		if (pwd == null || pwd.length()<1){
			actionErrors.add("pwd", new ActionMessage("error.pwd"));
		}
		
		return actionErrors;
	}
	*/
	
	
}
