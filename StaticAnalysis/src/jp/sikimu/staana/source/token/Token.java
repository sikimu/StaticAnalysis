package jp.sikimu.staana.source.token;

/**
 * トークン(解析の最小単位 字句)
 * @author sikimu
 *
 */
public class Token {

	/**
	 * 文字位置(何文字目か)
	 */
	public final int charNumber;
	
	/**
	 * 形態素文字列
	 */
	public final String tokenWord;

	
	Token(int charNumber, String tokenWord) {
		
		this.charNumber = charNumber;
		this.tokenWord = tokenWord;
	}
	
	/**
	 * 自身が { か判断する
	 * @return
	 */
	public boolean isBucketStart() {
		
		return tokenWord.equals("{");
	}
	
	/**
	 * 自身が } か判断する
	 * @return
	 */
	public boolean isBucketEnd() {
		
		return tokenWord.equals("}");
	}	
	
	@Override
	public String toString() {

		return tokenWord;
	}
}