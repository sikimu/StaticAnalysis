package jp.sikimu.staana.word;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * 専用の形式にしたソースコードを読み込んでコマンド単位で保持するクラス
 * 
 * @author sikimu
 *
 */
public class StaanaSource {

	/**
	 * 読み込むオリジナルソース
	 * @param path
	 * @throws IOException
	 */
	public StaanaSource(Path path) throws IOException {
		
		ArrayList<StaanaWordBlock> wordList = StaanaWordsListFactory.create(path);

		//ArrayList<StaanaCommand> commandList = StaanaCommandListFactory.create(wordList);
	}
}
