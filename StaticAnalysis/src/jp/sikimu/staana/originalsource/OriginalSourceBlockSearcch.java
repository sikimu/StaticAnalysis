package jp.sikimu.staana.originalsource;

import java.io.IOException;
import java.util.List;

/**
 * ブロックの探索抽象クラス
 * @author sikimu
 *
 */
public abstract class OriginalSourceBlockSearcch {

	/**
	 * 対象の開始位置か判定
	 * @param sourceLines
	 * @param offset
	 * @return
	 */
	abstract boolean isStartPoint(List<String> sourceLines, OriginalSourcePoint offset);
	
	/**
	 * 終了位置のポイント探索
	 * @param sourceLines
	 * @param offset
	 * @return
	 */
	abstract OriginalSourcePoint searchEndPoint(List<String> sourceLines, OriginalSourcePoint offset) throws IOException;
}
