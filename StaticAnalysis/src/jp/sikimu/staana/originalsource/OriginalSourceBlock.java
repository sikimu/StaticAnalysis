package jp.sikimu.staana.originalsource;

/**
 * 解析する最小単位
 * 
 * @author sikimu
 *
 */
public class OriginalSourceBlock {

	/**
	 * 開始位置
	 */
	private final OriginalSourcePoint startPoint;

	/**
	 * 終了位置
	 */
	private final OriginalSourcePoint endPoint;

	/**
	 * コンストラクタ
	 * 
	 * @param start 開始位置
	 * @param end  終了位置
	 */
	OriginalSourceBlock(OriginalSourcePoint start, OriginalSourcePoint end) {
		startPoint = start;
		endPoint = end;
	}

	/**
	 * 開始行位置
	 * 
	 * @return
	 */
	int getLineStartPoint() {
		return startPoint.linePoint;
	}	
	
	/**
	 * 開始行位置
	 * 
	 * @return
	 */
	int getLineEndPoint() {
		return startPoint.linePoint;
	}		
	
	/**
	 * 開始文字位置
	 * 
	 * @return
	 */
	int getCharStartPoint() {
		return startPoint.charPoint;
	}
	
	/**
	 * 終了文字位置
	 * 
	 * @return
	 */
	int getCharEndPoint() {
		return endPoint.charPoint;
	}
}
