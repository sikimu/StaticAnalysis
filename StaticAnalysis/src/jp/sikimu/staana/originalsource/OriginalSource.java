package jp.sikimu.staana.originalsource;

import java.util.ArrayList;
import java.util.List;

/**
 * オリジナルソース 解析する最小単位のブロックをまとめている
 * 
 * @author sikimu
 *
 */
public class OriginalSource extends ArrayList<OriginalSourceBlock>{

	private static final long serialVersionUID = 1L;

	/**
	 * ソース実体
	 */
	private List<String> sourceLines;

	/**
	 * コンストラクタ
	 * 
	 * @param sourceLines ソース実体
	 */
	OriginalSource(List<String> sourceLines) {
		this.sourceLines = sourceLines;
	}

	/**
	 * ソースの文字列を1行目を取得
	 * @param block
	 * @param line
	 * @return
	 */
	public String getSingleText(OriginalSourceBlock block) {

		int linePoint = block.getLineStartPoint();
		int charPoint = block.getCharStartPoint();
		final int lineEndPoint = block.getLineEndPoint();
		final int charEndPoint = block.getCharEndPoint();
		
		//1行だったらそのまま返す
		if(linePoint == lineEndPoint) {
			return sourceLines.get(linePoint).substring(charPoint, charEndPoint);
		}
		else {
			return sourceLines.get(linePoint).substring(charPoint);
		}
	}
}
