package jp.sikimu.staana.word;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.sikimu.staana.word.StaanaWordBlock.StaanaWord;
import jp.sikimu.staana.word.StaanaWordBlock.StaanaWordPoint;

/**
 * ソースを読み込んでワードリストの作成クラス
 * @author sikimu
 *
 */
class StaanaWordsListFactory {

	/**
	 * パスから作成する
	 * @param path
	 * @return
	 * @throws IOException
	 */
	static ArrayList<StaanaWordBlock> create(Path path) throws IOException {
		
		String source = Files.readString(path, StandardCharsets.UTF_8);
		return create(source);
	}
		
	private static ArrayList<StaanaWordBlock> create(String source) {

		//正規表現を利用して単語単位に分割する		
		String regularExpression[] = {
				"(?<=\\+(?![+=]))",// +(ただし+か=が続いていない)				
				"(?<=-(?![>=-]))",// -(ただし-か>か=が続いていない)
				"(?<=\\*(?![/\\*=]))",//　*(ただし/か*か=が続いていない)
				"(?<=/(?![/\\*=]))",// /(ただし/か*か=が続いていない)
				"(?<==(?!=))",// =(ただし=が続いていない)
				"(?<=!(?!=))",// !(ただし=が続いていない)				
				"(?<=<(?!=))",// <(ただし=が続いていない)
				"(?<=>(?!=))",// >(ただし=が続いていない)
				"(?<=[\\p{Punct}&&[^\\*/<>=!+-]])",//上に出てきた以外の制御文字
				"(?<=\\s(?!\\s))",//スペース
				"(?<=\\n)",//\n改行(ここで区切れば\r\nでも行ける) 
				"\\b",//単語の両側
		};

		// 位置情報と合わせてワードとして保持していく
		List<StaanaWord> wordList = new ArrayList<StaanaWord>();
		String[] splitSource = source.split(String.join("|", regularExpression));
		StaanaWordPoint point = new StaanaWordPoint(0, 0);
		for(String s : splitSource) {
			wordList.add(new StaanaWord(point, s));
			if(s.matches("\\R")) {
				point = point.getNextLine();
				continue;
			}
			point = point.getNext(s.length());
		}
	
		// ブロックごとにまとめる
		ArrayList<StaanaWordBlock> blockList = new ArrayList<StaanaWordBlock>();		
		Iterator<StaanaWord> iterator = wordList.iterator();
		while(iterator.hasNext()) {
			StaanaWord w = iterator.next();
			//改行
			if(w.word.matches("\\R")) {
				blockList.add(new StaanaWordBlock(w));
				continue;
			}				
			//1行コメント
			if(w.word.matches("//")) {
				StaanaWordBlock block = createBlock(iterator, w, "\\R");
				blockList.add(block);
				continue;
			}	
			//複数行コメント
			if(w.word.matches("/\\*")) {
				StaanaWordBlock block = createBlock(iterator, w, "\\*/");
				blockList.add(block);
				continue;
			}
			//文字列
			if(w.word.matches("\"")) {
				StaanaWordBlock block = createBlock(iterator, w, "\"");
				blockList.add(block);
				continue;
			}
			//文字
			if(w.word.matches("'")) {
				StaanaWordBlock block = createBlock(iterator, w, "'");
				blockList.add(block);
				continue;
			}
		}
		return blockList;
	}
	
	/**
	 * 終了文字列までを固めてブロック作成
	 * @param iterator
	 * @param offset 開始文字列
	 * @param fixWord 終了文字列
	 * @return
	 */
	private static StaanaWordBlock createBlock(Iterator<StaanaWord> iterator, StaanaWord offset, String fixWord){
		
		ArrayList<StaanaWord> wordList = new ArrayList<>();
		
		wordList.add(offset);
		
		while(iterator.hasNext()) {
			StaanaWord w = iterator.next();
			wordList.add(w);
			//改行
			if(w.word.matches(fixWord)) {
				break;
			}	
		}
		
		return new StaanaWordBlock(wordList);
	}
}
































