package jp.sikimu.staana.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Statement {

	/**
	 * 不明な文
	 * @author sikimu
	 *
	 */
	public static class Unknown extends Statement{
				
		private final Token token;
		
		public Unknown(Token token) {

			this.token = token;
		}
		
		@Override
		public String toString() {
		
			return token.toString();
		}
	}
	
	/**
	 * ブロック分 {}で囲われたひとまとまり
	 * @author sikimu
	 *
	 */
	public static class Block extends Statement{
		
		private final Token start;
		
		private final Token end;
		
		public final List<Statement> statementList;
		
		/**
		 * コンストラクタ
		 * @param start 開始 {
		 * @param end 終了 {
		 * @param statementList　格納されているステートメント
		 * @param hierarchy 階層
		 */
		public Block(Token start, Token end, ArrayList<Statement> statementList) {

			this.start = start;
			this.end = end;
			this.statementList = Collections.unmodifiableList(statementList);
		}
		
		@Override
		public String toString() {
			
			return start.toString() + statementList.stream().map(Statement::toString).collect(Collectors.joining(" ")) + end.toString();
		}
	}
}
