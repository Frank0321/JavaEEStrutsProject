package drink.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


public class TotalCountFilter implements Filter {

	public void destroy() {	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		 * FrontendAction 執行，會先通過此 filter
		 * 確保"SQL回傳有效商品的數量"存入 session
		 */
		
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {	}

}
