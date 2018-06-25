package model.vo;

import java.io.Serializable;

public class State implements Serializable {

	private static final long serialVersionUID = 1437830289889149855L;

	private boolean opened = true;
	
	/** 该节点是否可用 */
	private boolean disabled = false;

	public State(boolean isopen, boolean isdisabled) {
		this.opened = isopen;
		this.disabled = isdisabled;
	}

	public State(boolean isopen) {
		this.opened = isopen;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	
	public boolean isDisabled() {
		return disabled;
	}

	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}