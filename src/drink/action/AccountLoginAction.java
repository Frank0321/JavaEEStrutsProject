package drink.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import drink.dao.AccountDao;
import drink.model.Account;

public class AccountLoginAction extends DispatchAction {
	
	private AccountDao accountDao = AccountDao.getInstance(); 
	
	//登入要處理的事情
	public ActionForward login(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		//登入請求
		ActionForward actFwd = null;
		//先產生 session
		HttpSession session = request.getSession();
		//擷取輸入的資訊
		String inputID = request.getParameter("id");
		String inputPwd = request.getParameter("pwd");
		
		String message ="";
		
		//先確認是否有輸入帳號
		if (inputID.isEmpty()){
			message = "請先輸入帳號";
			actFwd = mapping.findForward("fail");
		}else{
			//將 id 去對資料庫做查詢
			Account account = accountDao.queryAccountById(inputID);
			//如果有此 id 則比對密碼
			if (account.getName() != null){
				//比對密碼
				if (inputPwd.equals(account.getPwd())){
					System.out.println(account.getName() + " 您好");
					session.setMaxInactiveInterval(30*60);
					session.setAttribute("account", account);
					session.removeAttribute("tempBuyMap");
					session.removeAttribute("countPage");
					session.removeAttribute("goods");
					actFwd = mapping.findForward("success");
				}else if (inputPwd.isEmpty()){
					message = "密碼不能為空 !";
					//回到登入的畫面
					actFwd = mapping.findForward("fail");
				}else{
					message = "密碼輸入錯誤 !";
					//回到登入的畫面
					actFwd = mapping.findForward("fail");
				}
				request.setAttribute("inputID", inputID);
			}else{
				message = "無此帳號 !";
				request.setAttribute("inputID", inputID);
				//回到登入的畫面
				actFwd = mapping.findForward("fail");
			}
		}
		
		request.setAttribute("message", message);
		
		return actFwd;
	}
	
	//登出要處理的事情
	public ActionForward logout(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		System.out.println("謝謝光臨");
		session.removeAttribute("account");
		session.removeAttribute("tempBuyMap");
		session.removeAttribute("countPage");
		session.removeAttribute("goods");
		session.setMaxInactiveInterval(30*60);
		//回到登入的畫面
		return mapping.findForward("fail");
	}

}
