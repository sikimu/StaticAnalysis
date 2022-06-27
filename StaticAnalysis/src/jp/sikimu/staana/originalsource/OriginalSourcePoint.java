package jp.sikimu.staana.originalsource;

/**
 * オリジナルソースの位置
 * 
 * @author sikimu
 *
 */
class OriginalSourcePoint {

	/**
	 * 行位置
	 */
	final int linePoint;

	/**
	 * 文字位置
	 */
	final int charPoint;

	OriginalSourcePoint(int linePt, int charPt) {
		this.linePoint = linePt;
		this.charPoint = charPt;
	}
}