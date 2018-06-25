package com.wfsc.services.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.bo.auth.Org;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.Permission;
import com.wfsc.common.bo.user.Role;
import com.wfsc.common.bo.user.User;
import com.wfsc.daos.AdminDao;
import com.wfsc.daos.PermissionDao;
import com.wfsc.daos.RoleDao;
import com.wfsc.daos.product.ProductsDao;
import com.wfsc.daos.user.UserDao;

import dao.OrgDao;

@Service("securityService")
public class SecurityServiceImpl implements ISecurityService {

	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	@Autowired
	private AdminDao adminDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean addAdminUser(Admin admin) {
		try{
			adminDao.addAdmin(admin);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addRole(Role role) {
		try{
			roleDao.addRole(role);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteAdminUser(Long adminId) {
		adminDao.deleteEntity(adminId);
		return false;
	}

	@Override
	public boolean deleteRole(Long roleId) {
		roleDao.deleteEntity(roleId);
		return false;
	}

	@Override
	public Admin getAdminInfo(Long adminId) {
		return adminDao.getEntityById(adminId);
	}

	@Override
	public List<Admin> getAllAdmin() {
		return adminDao.getAllUsers();
	}

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllEntities();
	}

	@Override
	public boolean updateAdminUser(Admin admin) {
		try{
			adminDao.updateAdmin(admin);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateRole(Role role) {
		roleDao.updateEntity(role);
		return false;
	}

	
	

	@Override
	public List<Permission> getAllPerms() {
		return permissionDao.getAllEntities();
	}

	@Override
	public Permission getPermissionByActionId(String aid) {
		return permissionDao.getUniqueEntityByOneProperty("actionId", aid);
	}

	@Override
	public Role getRoleByRoleId(String rid) {
		return roleDao.getEntityById(Long.valueOf(rid));
	}

	@Override
	public User getUserByName(String userName) {
		return userDao.getUserByNickName(userName);
	}
	@Override
	public Admin getUserWithPermissionByName(String userName){
		return adminDao.getUserWithPermissionByName(userName);
	}
	
	@Override
	public List<Admin> getUserListByRoleName(String roleName) {
		Role role = roleDao.getRoleWithPermissionByName(roleName);
		List<Admin> users = new ArrayList<Admin>();
		users.addAll(role.getUsers());
		return users;
	}
	
	@Override
	public List<Role> getRoleListByUserName(String userName) {
		Admin userObj = adminDao.getUserWithPermissionByName(userName);
		List<Role> roles = new ArrayList<Role>();
		roles.addAll(userObj.getRoles());
		return roles;
	}
	
	@Override
	public boolean isAblePermission(String userName, String actionId) {
		if (userName == null || actionId == null)
			return false;
		Admin user = adminDao.getUserWithPermissionByName(userName);
		if (null == user)
			return false;
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			Set<Permission> perms = role.getPerms();
			for (Permission perm : perms) {
				if (perm.getActionId().equals(actionId))
					return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isAbleRole(String userName, String roleName) {
		if (userName == null || roleName == null)
			return false;
		Admin user = adminDao.getUserWithPermissionByName(userName);
		if (user == null)
			return false;
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			if (role.getRoleName().equals(roleName))
				return true;
		}
		return false;
	}
	
	@Override
	public void disableAccount(String userId) {
		if (StringUtils.isEmpty(userId.trim()))
			return;
		Admin user = adminDao.getEntityById(Long.valueOf(userId));
		user.setStatus(0);
		adminDao.updateAdmin(user);
	}

	@Override
	public void enableAccount(String userId) {
		if (StringUtils.isEmpty(userId.trim()))
			return;
		Admin user = adminDao.getEntityById(Long.valueOf(userId));
		user.setStatus(1);
		adminDao.updateAdmin(user);
	}
	
	@Override
	public void disableUsers(String[] stopUserId) {
		for (int i = 0; i < stopUserId.length; i++) {
			disableAccount(stopUserId[i]);
		}
	}

	@Override
	public void enableUsers(String[] startUserId) {
		for (int i = 0; i < startUserId.length; i++) {
			enableAccount(startUserId[i]);
		}
	}
	public Page<Admin> findPageForAdmin(final Page<Admin> page, Map<String,Object> paramap){
		Page<Admin> findPageForAdmin = adminDao.findPageForAdmin(page, paramap);
		List<Admin> data = findPageForAdmin.getData();
		//查询各管理员所属的分销商，用来显示在管理员列表
		for (Admin admin : data) {
			Org orgAdmin = this.orgDao.getUniqueEntityByOneProperty("charger.id", admin.getId());
			if(orgAdmin != null){//说明该管理员已经是某个分销商的管理员了
				admin.setOrgName(orgAdmin.getOrgname());
			}else{
				admin.setOrgName("暂无");
			}
		}
		return findPageForAdmin;
	}
	
	public List<Permission> getAllSubPermission(){
		return this.permissionDao.getAllSubPermission();
	}
	
	public Role getRoleByName(String roleName){
		return roleDao.getUniqueEntityByOneProperty("roleName", roleName);
	}
	
	public boolean isExitRole(String roleName){
		Role r = getRoleByName(roleName);
		if(r==null){
			return false;
		}else{
			return true;
		}
	}
	
	public void delSelectAdmin(String[] adminIds){
		
		ProductsDao pDao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		
		for(String id : adminIds){
			//清除掉comorg表里的关联
			List<Org> charger = orgDao.getEntitiesByOneProperty("charger.id", Long.valueOf(id));
			if(!charger.isEmpty()){
				for (Org org : charger) {
					org.setCharger(null);
					orgDao.updateEntity(org);
				}
			}
			
			//清除商品里关联的和admin表里的pid
			List<Products> prdList = pDao.getEntitiesByOneProperty("serviceId", Long.valueOf(id));
			for (Products products : prdList) {
				products.setServiceId(null);
				pDao.updateEntity(products);
			}
			logger.info("清除商品里的门店管理员ID...");
			
			//该admin创建的子用户
			List<Admin> subAdmin = adminDao.getEntitiesByOneProperty("pid", Long.valueOf(id));
			for (Admin admin : subAdmin) {
				admin.setPid(null);
				adminDao.updateEntity(admin);
			}
			logger.info("清除子管理员PID...");
			
			Admin a = adminDao.getEntityById(Long.valueOf(id));
			a.setRoles(null);
			adminDao.deleteEntity(a);
			//ids.add(Long.valueOf(id));
		}
		//adminDao.deletAllEntities(ids);
	}
	public AdminDao getAdminDao() {
		return adminDao;
	}

	
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	
	public RoleDao getRoleDao() {
		return roleDao;
	}

	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public PermissionDao getPermissionDao() {
		return permissionDao;
	}

	
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	@Override
	public User getUserByMail(String account) {
		return userDao.getUniqueEntityByOneProperty("email", account);
	}

	@Override
	public User getUserByTelephone(String account) {
		return userDao.getUniqueEntityByOneProperty("telephone", account);
	}

	@Override
	public void reg4Mob(User user) {
		userDao.saveEntity(user);
	}

	
	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	@Override
	public boolean checkAreaCharger(String city, Long roleId) {
		//一个地区， 角色为代理商的，只允许有一个； 门店管理员不限
		List<Admin> s = this.adminDao.getEntitiesByPropNames(new String[]{ "area"}, new Object[]{ city});
		Role role = this.getRoleByRoleId(roleId.toString());
		if("代理商".equals(role.getRoleName()) ){
			if(!s.isEmpty()){
				return false;
			}
		}
		return true;
	}
}
