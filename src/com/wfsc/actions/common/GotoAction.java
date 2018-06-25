package com.wfsc.actions.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("GotoAction")
@Scope("prototype")
/**
 * go_*_*  {1} - 目录  {2} - 文件
 */
public class GotoAction extends CupidBaseAction {

	public String gotoPage() {
		return SUCCESS;
	}
	
}
