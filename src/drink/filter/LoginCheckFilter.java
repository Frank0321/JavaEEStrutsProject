package drink.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import drink.model.Account;


public class LoginCheckFilter implements Filter {


    public LoginCheckFilter() {
        
    }


	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//檢查 Session 中是否已存在登入的使用者
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		Account account = (Account) session.getAttribute("account");
		
		//檢查有沒有通過 filter
		//System.out.println("=============== LoginCheckFilter ===============");
		
		//如果有資料，則可以前往網站
		if (account != null){
			chain.doFilter(request, response);
		}else{
			//判斷目前是否為登入的網站，是的話也放行
			String requestURI = httpRequest.getRequestURI();  // /JavaEE_Session5_Homework/LoginAction.do
			String action = request.getParameter("action");  // login
			if(requestURI.endsWith("LoginAction.do") && "login".equals(action)){
				chain.doFilter(request, response);
			}else{
				//forward to 登入網站 (會顯示原本的網址)
//				RequestDispatcher dispatcher = request.getRequestDispatcher("/AccountLogin.jsp");
//				dispatcher.forward(request, response);
				
				//sendRedirect to 登入網站 
				httpResponse.sendRedirect("/JavaEE_Session6_Homework/AccountLogin.jsp");
			}
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
