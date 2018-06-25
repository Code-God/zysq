package com.wfsc.listener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.wfsc.common.bo.user.User;
  
/**  
 * @author jacky  
 *   
 */  
@SuppressWarnings("unchecked")   
public class OnlineStatistics {   
    private static int count = 0;   
    private static Set onlineMemberList = new HashSet();   
  
    /**  
     * 统计在线总数(递增)  
     */  
    public static void increase() {   
        count++;
    }   
  
    /**  
     * 统计在线人数(递减)  
     */  
    public static void decrease() {   
        count--;   
    }   
  
    /**  
     * 统计在线会员(添加)  
     *   
     * @param memberInfo  
     */  
    public static void addAttr(User memberInfo) {   
        // 如果在线会员列表不为空,则迭代该列表,检查当前登录的会员id与列表中的记录id是否一致.   
        Iterator itr = onlineMemberList.iterator();   
        while (itr.hasNext()) {   
            // 先判断该会员是否已有session信息保存在统计对象中.若有,把旧的挤掉,再放入新的.   
            User temp = (User) itr.next();   
            String memberName = temp.getEmail();// 保存在统计类中的用户名   
            if (memberInfo.getEmail().equals(memberName)) {   
                itr.remove();   
            }
        }   
        onlineMemberList.add(memberInfo);   
    }   
  
    /**  
     * 统计在线会员(清除)  
     *   
     * @param memberInfo  
     */  
    public static void removeAttr(User memberInfo) {   
        onlineMemberList.remove(memberInfo);   
    }   
  
    /**  
     * 获取在线访客的数量  
     *   
     * @return the count  
     */  
    public static int getOnlineVisitors() {
        return count;// 除去管理员本身   
    }
  
    /**  
     * 获取在线会员的列表  
     *   
     * @return the onlineMemberList  
     */  
    public static Set getOnlineMemberList() {   
        return onlineMemberList;   
    }   
  
    /**  
     * 根据登录时间判断登录次序的先后  
     *   
     * @param memberInfo  
     * @return  
     */  
    public static boolean isOld(User memberInfo) {   
        Iterator itr = onlineMemberList.iterator();   
        while (itr.hasNext()) {   
        	User temp = (User) itr.next();   
            String memberName = temp.getEmail();// 保存在统计类中的用户名   
//            Date loginTime = new Date(temp.getLastLoginTime());// 保存在统计类中的用户登录时间   
//            if (memberInfo.getName().equals(memberName) && memberInfo.getLastLoginTime().before(loginTime)) {   
//                // 如果当前用户的登录时间比统计对象里保存的同名用户的登录时间要早,则返回true   
//                return true;   
//            }   
        }   
        return false;   
    }   
  
}  
