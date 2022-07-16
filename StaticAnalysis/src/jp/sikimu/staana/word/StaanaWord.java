package jp.sikimu.staana.word;

/**
 * 解析する最小単位(ワード)
 * 
 * @author sikimu
 *
 */
public class StaanaWord {

	/**
	 * 開始行位置
	 */
	public final int lineNumber;

	/**
	 * 開始文字位置
	 */
	public final int charNumber;

	/**
	 * ワードの実体
	 */
	public final String word;	


	StaanaWord(int lineNumber, int charNumber, String word) {

		this.lineNumber = lineNumber;
		this.charNumber = charNumber;
		this.word = word;
	}
	
	public boolean equals(String word) {
		
		return this.word.equals(word);
	}
	
	public boolean equals(StaanaWord staanaWord) {
		
		if(this.lineNumber != staanaWord.lineNumber) {
			
			return false;
		}
		if(this.charNumber != staanaWord.charNumber) {
			
			return false;
		}
		
		return this.word.equals(staanaWord.word);
	}
}
