package com.wfsc.actions.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.constants.CupidStrutsConstants;
import com.wfsc.annotation.Login;
import com.wfsc.common.bo.account.Address;
import com.wfsc.common.bo.account.Coupon;
import com.wfsc.common.bo.account.Favourite;
import com.wfsc.common.bo.account.ReturnProducts;
import com.wfsc.common.bo.comment.Comments;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.order.OrdersDetail;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IAccountService;
import com.wfsc.services.account.IUserService;
import com.wfsc.services.comment.ICommentsService;
import com.wfsc.services.orders.IOrdersService;
import com.wfsc.services.product.IProductsService;
import com.wfsc.util.CipherUtil;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Login
@Controller("AccountAction")
@Scope("prototype")
public class AccountAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840813332299260353L;

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	
	@Resource(name = "commentsService")
	private ICommentsService commentsService;
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "productsService")
	private IProductsService productsService;
	
	@Resource(name = "accountService")
	private IAccountService accountService;
	@Resource(name = "ordersService")
	private IOrdersService ordersService;
	private Comments comments;
	private User user;
	private Address addr;
	
	
	public String userInfo(){
		user = this.getCurrentUser();
		if(user!=null){
			return "userInfo";
		}else{
			return "goLogin";
		}
		
	}
	
	public String saveUser(){
		Long id = user.getId();
		User userdb = userService.getUserById(id);
		userdb.setEmail(user.getEmail());
		userdb.setTelephone(user.getTelephone());
		userdb.setNickName(user.getNickName());
		userService.saveOrUpdateEntity(userdb);
		user = userdb;
		session.put(CupidStrutsConstants.SESSION_USER, userdb);
		request.setAttribute("msg", "保存成功");
		return "userInfo";
		
	}
	
	public String toMdfPassWord(){
		return "modifypw";
	}
	
	public String mdPassWord(){
		user = this.getCurrentUser();
		String oldPw = request.getParameter("oldPw");
		if(CipherUtil.validatePassword(user.getPassword(),oldPw)){
			String newPw = request.getParameter("newPw");
			user.setPassword(CipherUtil.generatePassword(newPw));
			userService.saveOrUpdateEntity(user);
			session.put(CupidStrutsConstants.SESSION_USER, user);
			request.setAttribute("msg", "修改成功");
		}else{
			request.setAttribute("msg", "旧密码输入错误");
		}
		
		
		return "modifypw";
	}
	
	public String addrMgt(){
		user = this.getCurrentUser();
		List<Address> addrlist = accountService.findAddressByUserid(user.getId());
		request.setAttribute("addrlist", addrlist);
		return "addrMgt";
	}
	
	public String modifyAddr(){
		String id = request.getParameter("id");
		Address add = accountService.findAddressById(Long.valueOf(id));
		JSONObject json = JSONObject.fromObject(add);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String addAddr(){
		addr.setUserid(this.getCurrentUser().getId());
		accountService.saveOrUpdateAddress(addr);
		String type = getPara("type");
		if ("shopping_confirm".equals(type)) {
			return "shopping_confirm";
		}
		return "toAddrMgt";
	}
	
	public String addAddrMob(){
		String pcc = request.getParameter("pcc");
		String op = request.getParameter("op");
		if("save".equals(op)){
			addr.setUserid(this.getCurrentUser().getId());
			addr.setDetailAddr(pcc + " " + addr.getDetailAddr());
			accountService.saveOrUpdateAddress(addr);
		}else if("update".equals(op)){
			String uaId = request.getParameter("uaId");
			addr.setId(Long.valueOf(uaId));
			addr.setUserid(this.getCurrentUser().getId());
			addr.setDetailAddr(pcc + " " + addr.getDetailAddr());
			accountService.saveOrUpdateAddress(addr);
		}
		
		return "useradd";
	}
	
	public String delAddr(){
		String id = request.getParameter("id");
		accountService.deleteAddressById(Long.valueOf(id));
		try {
			response.getWriter().write("sucess");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String setDefault(){
		String id = request.getParameter("id");
		accountService.setDefaultAddr(Long.valueOf(id), this.getCurrentUser().getId());
		try {
			response.getWriter().write("sucess");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String couponMgt(){
		List<Coupon> unUselist = accountService.findCouponByUseridAndstatus(this.getCurrentUser().getId(), 1);
		List<Coupon> hasUselist = accountService.findCouponByUseridAndstatus(this.getCurrentUser().getId(), 2);
		List<Coupon> hasCanclelist = accountService.findCouponByUseridAndstatus(this.getCurrentUser().getId(), 3);
		request.setAttribute("unUselist", unUselist);
		request.setAttribute("hasUselist", hasUselist);
		request.setAttribute("hasCanclelist", hasCanclelist);
		return "couponMgt";
	}
	
	public String ordersMgt(){
		String host = Version.getInstance().getNewProperty("image.server.ip");
		String port = Version.getInstance().getNewProperty("image.server.port");
		String url = "http://"+host+":"+port+"/images/";
		request.setAttribute("imgServer", url);
		user = this.getCurrentUser();
		String status = request.getParameter("status");
		if(StringUtils.isBlank(status)){
			status = "99";
		}
		//String osdate = request.getParameter("osdate");
		//String oedate = request.getParameter("oedate");
		List<Orders> ordersList = ordersService.getOrderByUserId(user.getId().longValue(), Integer.valueOf(status), 0,10);
		for(Orders o : ordersList){
			List<OrdersDetail> ods = ordersService.getOrderDetailsByOrderNo(o.getOrderNo());
			o.setOrdersDetail(ods);
		}
		request.setAttribute("ordersList", ordersList);
		request.setAttribute("status", status);
		return "ordersMgt";
	}
	
	public String changeOrderState(){
		String orderState = request.getParameter("orderState");
		String orderNo = request.getParameter("orderNo");
		Orders orders = ordersService.getOrderByCode(orderNo);
		orders.setStatus(Integer.valueOf(orderState));
		ordersService.saveOrUpdateOrder(orders);
		return "ok";
	}
	public String ordersDetai(){
		String host = Version.getInstance().getNewProperty("image.server.ip");
		String port = Version.getInstance().getNewProperty("image.server.port");
		String url = "http://"+host+":"+port+"/images/";
		request.setAttribute("imgServer", url);
		String orderNo = request.getParameter("orderNo");
		Orders o = ordersService.getOrderByCode(orderNo);
		List<OrdersDetail> ods = new ArrayList<OrdersDetail>();
		if(o!=null&&o.getOrdersDetail()!=null){
			ods = o.getOrdersDetail();
		}
		request.setAttribute("orders", o);
		request.setAttribute("ods", ods);
		return "ordersDetai";
	}
	
	public String favoriteMgt(){
		List<Favourite> favoriteList = accountService.findFavouriteByUserid(this.getCurrentUser().getId());
		request.setAttribute("favoriteList", favoriteList);
		return "favoriteMgt";
	}
	
	public String delFavorite(){
		String id = request.getParameter("id");
		accountService.deleteFavouriteById(Long.valueOf(id));
		try {
			response.getWriter().write("sucess");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String addFavorite(){
		String prdCode = request.getParameter("prdCode");
		Products p = productsService.getByPrdCode(prdCode);
		Favourite f = new Favourite();
		f.setProducts(p);
		f.setStoreDate(new Date());
		f.setUserid(this.getCurrentUser().getId());
		accountService.saveOrUpdateFavourite(f);
		return "tofavoriteMgt";
	}
	
	public String commentsMgt(){
		List<OrdersDetail> unorderDetails = new ArrayList<OrdersDetail>();
		unorderDetails = ordersService.getOrdersDetailByUser(getCurrentUser().getId(),0);
		request.setAttribute("unorderDetails", unorderDetails);
		List<OrdersDetail> hasorderDetails = new ArrayList<OrdersDetail>();
		hasorderDetails =  ordersService.getOrdersDetailByUser(getCurrentUser().getId(),1);
		request.setAttribute("hasorderDetails", hasorderDetails);
		return "commentsMgt";
	}




	public String commentsInput() {
		String host = Version.getInstance().getNewProperty("image.server.ip");
		String port = Version.getInstance().getNewProperty("image.server.port");
		String url = "http://"+host+":"+port+"/images/";
		request.setAttribute("imgServer", url);
		String detailId = request.getParameter("detailId");
		OrdersDetail od = this.ordersService.getOrdersDetailById(Long.valueOf(detailId));
		request.setAttribute("od", od);
		return "commentsInput";
	}
	
	public String commentsDetail() {
		String detailId = request.getParameter("detailId");
		//根据订单详情找到对应评论
		comments = commentsService.getCommentsByDetailId(Long.valueOf(detailId));
		return "commentsDetail";
	}
	
	
	public String saveComments() throws IOException{
		comments.setCreatorId(this.getCurrentUser().getId());
		comments.setNickName(this.getCurrentUser().getNickName());
		comments.setPdate(new Date());
		String prdTopCode = comments.getPrdCode().substring(0, 3);
		comments.setPrdTopCode(prdTopCode);
		commentsService.saveOrUpdateEntity(comments);
		Long detailId = comments.getOrderDetailId();
		//根据id找到对应的订单详情，跟新为已评论
		OrdersDetail od = ordersService.getOrdersDetailById(Long.valueOf(detailId));
		od.setIsComment(1);
		ordersService.saveOrUpdateDetail(od);
		return "commentsOk";
	}
	
	public String returnProductsMgt(){
		List<ReturnProducts>  rplist = accountService.findReturnProductsByUserid(this.getCurrentUser().getId());
		request.setAttribute("rplist", rplist);
		return "returnProductsMgt";
	}
	
	public String returnProductsDetail(){
		String id = request.getParameter("id");
		ReturnProducts rp = accountService.findReturnProductsById(Long.valueOf(id));
		request.setAttribute("rp", rp);
		return "returnProductsDetail";
	}

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddr() {
		return addr;
	}

	public void setAddr(Address addr) {
		this.addr = addr;
	}


	
	


	
}
