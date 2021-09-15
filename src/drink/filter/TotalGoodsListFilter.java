package drink.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import drink.model.Good;
import drink.service.GoodSQLService;



public class TotalGoodsListFilter implements Filter {
	
	private GoodSQLService goodSQLService = GoodSQLService.getInstance();

    public TotalGoodsListFilter() { }

	public void destroy() { }

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//檢查是否有 "全部產品的資訊"
		HttpServletRequest httpRequest = (HttpServletRequest) request;		
		HttpSession session = httpRequest.getSession();
		List<Good> goods = (List<Good>) session.getAttribute("goods");
		if(goods == null || goods.isEmpty()){
			goods = goodSQLService.queryAllGoodsService();
			session.setAttribute("goods", goods);
			System.out.println("我建立一個新的 goodsList 了喔");
		}
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {}

}
