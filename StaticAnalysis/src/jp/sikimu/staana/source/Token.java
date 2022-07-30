package jp.sikimu.staana.source;

/**
 * トークン(解析の最小単位 字句)
 * @author sikimu
 *
 */
public class Token {

	/**
	 * 文字位置(何文字目か)
	 */
	private final int charNumber;
	
	/**
	 * 形態素文字列
	 */
	private final String tokenWord;

	
	Token(int charNumber, String tokenWord) {
		
		this.charNumber = charNumber;
		this.tokenWord = tokenWord;
	}
	
	/**
	 * 自身が { か判断する
	 * @return
	 */
	boolean isBucketStart() {
		
		return tokenWord.equals("{");
	}
	
	/**
	 * 自身が } か判断する
	 * @return
	 */
	boolean isBucketEnd() {
		
		return tokenWord.equals("}");
	}	
	
	@Override
	public String toString() {

		return tokenWord;
	}
}