package jp.sikimu.staana.word;

import java.util.ArrayList;

/**
 * 解析する最小単位ブロック
 * 
 * @author sikimu
 *
 */
public class StaanaWordBlock {
	
	private final ArrayList<StaanaWord> block;
	
	StaanaWordBlock(ArrayList<StaanaWord> wordsList){
		
		this.block = new ArrayList<StaanaWord>();
		this.block.addAll(wordsList);
	}

	StaanaWordBlock(StaanaWord word) {
		
		this.block = new ArrayList<StaanaWord>();
		this.block.add(word);
	}

	/**
	 * 解析する最小単位(ワード)の位置
	 * @author sikimu
	 *
	 */
	static class StaanaWordPoint{
				
		/**
		 * 開始行位置
		 */
		public final int lineNumber;

		/**
		 * 開始文字位置
		 */
		public final int charNumber;		
		
		StaanaWordPoint(int lineNumber, int charNumber) {
			this.lineNumber = lineNumber;
			this.charNumber = charNumber;
		}
		
		public boolean equals(StaanaWordPoint point) {
			if(this.lineNumber != point.lineNumber) {
				
				return false;
			}
			if(this.charNumber != point.charNumber) {
				
				return false;
			}
			
			return true;
		}
		
		/**
		 * 隣の文字位置の取得
		 * @param skip 
		 * @return
		 */
		public StaanaWordPoint getNext(int skip) {
			return new StaanaWordPoint(lineNumber, charNumber + skip);
		}
		
		/**
		 * 次の行の1文字目を取得
		 * @return
		 */
		public StaanaWordPoint getNextLine() {
			return new StaanaWordPoint(lineNumber + 1, 0);
		}
	}

	/**
	 * 解析する最小単位(ワード)
	 * 
	 * @author sikimu
	 *
	 */
	 public static class StaanaWord {
		/**
		 * ワードの位置
		 */
		public final StaanaWordPoint point;
	
		/**
		 * ワードの実体
		 */
		public final String word;	
	
	
		StaanaWord(StaanaWordPoint point, String word) {
	
			this.point = point;
			this.word = word;
		}
	
		public boolean equals(StaanaWord staanaWord) {
			
			if(this.point.equals(staanaWord.point) == false) {
				
				return false;
			}
	
			return this.word.equals(staanaWord.word);
		}
	}

}
