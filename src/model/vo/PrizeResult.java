package model.vo;

import java.io.Serializable;

import model.bo.games.PrizePool;

/**
 * 抽奖结果的对象
 * 
 * @author jacky
 * @version 1.0
 */
public class PrizeResult implements Serializable {

	private static final long serialVersionUID = 2784599901396303298L;

	/** 超过抽奖次数 */
	public static final String CODE_INVALID = "invalid";

	/** 奖池空了 */
	public static final String CODE_EMPTY = "empty";

	/** 已经中过了 */
	public static final String CODE_ALREADY = "already";

	/** 中奖了 */
	public static final String CODE_OK = "ok";

	/** 未中奖 */
	public static final String CODE_FAIL = "fail";

	/** 中奖的奖品 */
	private PrizePool pp;

	/** 中奖码 invalid - 超过次数了， empty-奖池已空 ok - 中奖 */
	private String code = "";

	public PrizeResult() {
	}

	public PrizeResult(String code, PrizePool pp) {
		this.code = code;
		this.pp = pp;
	}

	public PrizePool getPp() {
		return pp;
	}

	public void setPp(PrizePool pp) {
		this.pp = pp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
