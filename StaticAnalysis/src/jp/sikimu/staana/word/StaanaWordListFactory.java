package jp.sikimu.staana.word;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * ソースを読み込んでワードリストの作成クラス
 * @author sikimu
 *
 */
class StaanaWordListFactory {

	/**
	 * パスから作成する
	 * @param path
	 * @return
	 * @throws IOException
	 */
	static ArrayList<StaanaWord> create(Path path) throws IOException {
		
		String source = Files.readString(path, StandardCharsets.UTF_8);
		return create(source);
	}
		
	private static ArrayList<StaanaWord> create(String source) {
		
		ArrayList<StaanaWord> staanaWords = new ArrayList<StaanaWord>();	

		//正規表現を利用して単語単位に分割する		
		String splitWords[] = {
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

		// 位置情報と合わせて保持していく
		int lineNumber = 0;
		int charNumber = 0;
		for (String word : source.split(String.join("|", splitWords))) {

			staanaWords.add(new StaanaWord(lineNumber, charNumber, word));
			charNumber = charNumber + word.length();
			if(word.matches("\\R")) {
				charNumber = 0;
				lineNumber++;
			}
		}
		
		return staanaWords;
	}
}
































