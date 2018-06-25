package com.base.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import com.base.log.LogUtil;
import com.constants.CupidStrutsConstants;

public class UeditorStrutsFilter extends StrutsPrepareAndExecuteFilter {
	
	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest request = (HttpServletRequest) req;  
        HttpServletResponse response = (HttpServletResponse) res;  
        String url = request.getRequestURI();
//        logger.info("url----" + url);
        if (url.indexOf("ueditor/jsp")>0 
        		|| url.indexOf("/js/")!=-1 || url.indexOf("/images/")!=-1 ||  url.indexOf("/css/")!=-1
        		|| url.indexOf("/fileLoad") != -1 || url.indexOf("/weixin/car/cityConfirm.jsp") != -1
        		|| url.indexOf("/pub_getFaxianList.Q") != -1  //http://localhost:88/coupon/public/pub_getFaxianList.Q
        		|| url.indexOf("/car/faxian.jsp") != -1 
        		|| url.indexOf("car/selectCity.jsp") != -1) {  
            chain.doFilter(req, res);  
        }else{
        	//过滤部分页面请求
//        	logger.info("url==" + url);
        	if(url.indexOf("/weixin/car/") != -1){
        		
        		//判断是否已经切换好地址
        		if(request.getSession().getAttribute(CupidStrutsConstants.SES_CITY) != null){
        			chain.doFilter(req, res);
        		}else{
        			//跳转到城市切换页面
        			logger.info("跳转到城市切换页面。。。。。。。。。");
//        			response.sendRedirect(request.getContextPath() + "/weixin/car/selectCity.jsp");
        		}
        	}else{
        		super.doFilter(req, res, chain);  
        	}
        }  
    }  

}
