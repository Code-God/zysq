/******************************************************************************** 
 * Create Author   : Apex Team
 * Create Date     : Oct 21, 2009
 * File Name       : PermissionId.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.common.utils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 各模块PermissionId的定义文件，PermissionId的定义方式为模块名_PermissionId，该定义需要和数据库中保存的权限项相同
 */
public abstract class PermissionId {

    private static Set<String> permissionIds;
    /** 系统管理 */
    public static final String SYSTEM_MGMT = "system_management";
    /** 系统管理：SEO设置 */
    public static final String SYSTEM_MGMT_SEO = "system_management_seo";
    /** 系统管理：网站备份 */
    public static final String SYSTEM_MGMT_BACKUP = "system_management_backup";
    /** 系统管理：邮件服务器设置 */
    public static final String SYSTEM_MGMT_MAILCONFIG = "system_management_mailconfig";
    /** 系统管理：系统日志 */
    public static final String SYSTEM_MGMT_SYSLOG = "system_management_syslog";
    /** 商品管理 */
    public static final String PRODUCT_MGT = "product_mgt";
    /** 商品管理 分类 */
    public static final String PRODUCT_MGT_CAT = "product_mgt_cat";
    /** 商品管理 商品 */
    public static final String PRODUCT_MGT_PRO = "product_mgt_pro";
    /** 商品管理 特惠新品 */
    public static final String PRODUCT_MGT_RECOMMEND = "product_mgt_recommend";
    /** 配送区域管理 */
    public static final String AREA_CONFIG = "area_config";
    /** 广告维护 */
    public static final String ADV_CONFIG = "adv_config";
    
    /** 评价管理 */
    public static final String COMMENTS_MGT = "comments_mgt";
    /** 配送跟踪 */
    public static final String DISTRIBUTION_MGT = "distribution_mgt";
    /** 订单管理 */
    public static final String ORDER_MGT = "order_mgt";
    /** 普通会员管理 */
    public static final String COMMEN_MEMBER_MGT = "commen_member_mgt";
    /** 企业会员管理 */
    public static final String ENTERPRISE_MEMBER_MGT = "enterprise_member_mgt";
    /** 权限管理 */
    public static final String PERMISSION_MGT = "permission_mgt";
    /** 管理员列表 */
    public static final String PERMISSION_MGT_ADMIN = "permission_mgt_admin";
    /** 角色管理 */
    public static final String PERMISSION_MGT_ROLE = "permission_mgt_role";
    /** 统计报表 */
    public static final String REPORT_MGT = "report_mgt";
    /** 统计报表：会员统计 */
    public static final String REPORT_MGT_MEMBER = "report_mgt_member";
    /** 统计报表 财务统计*/
    public static final String REPORT_MGT_FINANCE = "report_mgt_finance";
    /** 统计报表 商品统计*/
    public static final String REPORT_MGT_PRODUCT = "report_mgt_product";
    /** 统计报表 满意度统计*/
    public static final String REPORT_MGT_SATISFACTION = "report_mgt_satisfaction";
    
    public static synchronized Set<String> getAllPermissionIds() {
        if (permissionIds != null) {
            return permissionIds;
        } else {
            permissionIds = new HashSet<String>();
        }
        Field[] fields = PermissionId.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                Object fieldValue = field.get(PermissionId.class);
                if (fieldValue instanceof String) {
                    String value = (String) fieldValue;
                    permissionIds.add(value);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return permissionIds;
    }
}