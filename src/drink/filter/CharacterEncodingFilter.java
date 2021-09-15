package drink.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


//@WebFilter("/CharacterEncodingFilter")
public class CharacterEncodingFilter implements Filter {


    public CharacterEncodingFilter() {
        
    }


	public void destroy() {
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// request 進來前要做的處理事情
		
		// 解決 post 請求中文亂碼的問題
		request.setCharacterEncoding("UTF-8");
		// 設置回應為 UTF-8
		response.setCharacterEncoding("UTF-8");
		//設定 Browser 輸出內容為 html，編碼為 UTF-8
		response.setContentType("text/html; charset=UTF-8");
		
		//檢查有沒有通過 filter
		//System.out.println("=============== CharacterEncodingFilter ===============");
		
		// 連接到要執行的地方
		chain.doFilter(request, response);
		
		// request 出去前要做的處理事情
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
