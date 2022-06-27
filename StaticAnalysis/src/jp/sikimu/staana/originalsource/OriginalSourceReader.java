package jp.sikimu.staana.originalsource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * オリジナルソースの読み込み
 * 
 * @author sikimu
 *
 */
public class OriginalSourceReader {
	
	/**
	 * 英数字
	 */
	private final static String ALPHABETS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	/**
	 * キーワード(括弧など)
	 */
	private final static String KEYWORDS = "={}()[]+-*/%!:;.,|&<>";
	
	/**
	 * オリジナルソースの読み込み
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static OriginalSource read(Path path) throws IOException {

		// 対象のソースを読み込み
		List<String> sourceLines = Files.readAllLines(path, StandardCharsets.UTF_8);

		OriginalSource originalSource = new OriginalSource(sourceLines);

		int linePoint = 0;
		int charPoint = 0;
		
		while(linePoint < sourceLines.size()) {
			
			//改行
			if (charPoint == sourceLines.get(linePoint).length()) {
				linePoint++;
				charPoint = 0;
				continue;
			}
			
			//空白とタブはスキップ
			char c = sourceLines.get(linePoint).charAt(charPoint);
			if (c == ' ' || c == '\t') {
				charPoint++;
				continue;
			}		
			
			OriginalSourcePoint startPoint = new OriginalSourcePoint(linePoint, charPoint);
			OriginalSourcePoint endPoint = searchEndPoint(sourceLines, startPoint);
			
			OriginalSourceBlock block = new OriginalSourceBlock(startPoint, endPoint);
			originalSource.add(block);
			
			linePoint = endPoint.linePoint;
			charPoint = endPoint.charPoint;
		}
		
		return originalSource;
	}

	/**
	 * 終了位置の検索
	 * @param sourceLines ソース
	 * @param offset 検索起点
	 * @return なければエラー
	 * @throws IOException 
	 */
	private static OriginalSourcePoint searchEndPoint(List<String> sourceLines, OriginalSourcePoint offset) throws IOException {

		OriginalSourceBlockSearcch[] searchs = {
			singleCommentBlockSearch,
			multiCommentBlockSearch,
			quotationBlockSearch,
			keywordBlockSearch,
			alphabetBlockSearch,
		};
		
		for(OriginalSourceBlockSearcch search : searchs) {
			if(search.isStartPoint(sourceLines, offset)) {
				return search.searchEndPoint(sourceLines, offset);
			}
		}
		
		throw new IOException();
	}
	
	/**
	 * 1行コメント探索
	 */
	private static OriginalSourceBlockSearcch singleCommentBlockSearch = new OriginalSourceBlockSearcch() {

		@Override
		boolean isStartPoint(List<String> sourceLines, OriginalSourcePoint offset) {
			
			int linePoint = offset.linePoint;
			int charPoint = offset.charPoint;
			return sourceLines.get(linePoint).substring(charPoint).startsWith("//");
		}

		@Override
		OriginalSourcePoint searchEndPoint(List<String> sourceLines, OriginalSourcePoint offset) throws IOException {
			
			int linePoint = offset.linePoint;
			return new OriginalSourcePoint(linePoint, sourceLines.get(linePoint).length());
		}
	};
	
	/**
	 * 複数コメント探索
	 */
	private static OriginalSourceBlockSearcch multiCommentBlockSearch = new OriginalSourceBlockSearcch() {

		@Override
		boolean isStartPoint(List<String> sourceLines, OriginalSourcePoint offset) {
			
			int linePoint = offset.linePoint;
			int charPoint = offset.charPoint;
			return sourceLines.get(linePoint).substring(charPoint).startsWith("/*");
		}

		@Override
		OriginalSourcePoint searchEndPoint(List<String> sourceLines, OriginalSourcePoint offset) throws IOException {
			
			int linePoint = offset.linePoint;
			int charPoint = offset.charPoint;
			while(linePoint < sourceLines.size()) {
				//改行
				if (charPoint == sourceLines.get(linePoint).length()) {
					linePoint++;
					charPoint = 0;
					continue;
				}
				if(sourceLines.get(linePoint).substring(charPoint).startsWith("*/")) {
					return new OriginalSourcePoint(linePoint, sourceLines.get(linePoint).length());
				}
				charPoint++;
			}
			throw new IOException();
		}
	};	
	
	/**
	 * コーテーション探索
	 */
	private static OriginalSourceBlockSearcch quotationBlockSearch = new OriginalSourceBlockSearcch() {

		@Override
		boolean isStartPoint(List<String> sourceLines, OriginalSourcePoint offset) {
			
			int linePoint = offset.linePoint;
			int charPoint = offset.charPoint;
			char offsetChar = sourceLines.get(linePoint).charAt(charPoint);
			
			return offsetChar == '\'' || offsetChar == '"';
		}

		@Override
		OriginalSourcePoint searchEndPoint(List<String> sourceLines, OriginalSourcePoint offset) throws IOException {
			
			int linePoint = offset.linePoint;
			int charPoint = offset.charPoint;
			final char quotation = sourceLines.get(linePoint).charAt(charPoint);

			charPoint++;//オフセットの位置には最初のコーテーションが入っているのでスキップ
			while(charPoint < sourceLines.get(linePoint).length()) {
				if(sourceLines.get(linePoint).charAt(charPoint) == quotation) {
					//文字列としてのコーテーションはスキップ
					if(sourceLines.get(linePoint).charAt(charPoint) != '\\') {
						return new OriginalSourcePoint(linePoint, charPoint + 1);
					}
				}
				charPoint++;
			}
			throw new IOException();
		}
	};

	/**
	 * キーワード探索(括弧や演算子など)
	 */
	private static OriginalSourceBlockSearcch keywordBlockSearch = new OriginalSourceBlockSearcch() {
		
		@Override
		boolean isStartPoint(List<String> sourceLines, OriginalSourcePoint offset) {
			
			int linePoint = offset.linePoint;
			int charPoint = offset.charPoint;
			String offsetString = sourceLines.get(linePoint).substring(charPoint, charPoint + 1);
			
			return KEYWORDS.contains(offsetString);
		}

		@Override
		OriginalSourcePoint searchEndPoint(List<String> sourceLines, OriginalSourcePoint offset) throws IOException {
			
			int linePoint = offset.linePoint;
			int charPoint = offset.charPoint;
			
			return new OriginalSourcePoint(linePoint, charPoint + 1);
		}
	};
		

	/**
	 * 英数字探索
	 */
	private static OriginalSourceBlockSearcch alphabetBlockSearch = new OriginalSourceBlockSearcch() {
		
		@Override
		boolean isStartPoint(List<String> sourceLines, OriginalSourcePoint offset) {

			return true;
		}

		@Override
		OriginalSourcePoint searchEndPoint(List<String> sourceLines, OriginalSourcePoint offset) throws IOException {
			
			int linePoint = offset.linePoint;
			int charPoint = offset.charPoint;
			
			//英数字
			while(linePoint < sourceLines.size()) {
				char c = sourceLines.get(linePoint).charAt(charPoint);
				//改行
				if (charPoint == sourceLines.get(linePoint).length()) {
					return new OriginalSourcePoint(linePoint, charPoint);
				}
				//英数字以外
				if (ALPHABETS.contains(String.valueOf(c)) == false) {
					return new OriginalSourcePoint(linePoint, charPoint);
				}
				charPoint++;
			}
			throw new IOException();
		}
	};	
}
