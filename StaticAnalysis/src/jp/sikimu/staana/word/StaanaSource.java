package jp.sikimu.staana.word;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import jp.sikimu.staana.command.StaanaCommand;
import jp.sikimu.staana.command.StaanaCommandListFactory;

/**
 * 専用の形式にしたソースコードを読み込んでコマンド単位で保持するクラス
 * 
 * @author sikimu
 *
 */
public class StaanaSource {

	/**
	 * ワードリスト
	 */
	private ArrayList<StaanaWord> wordList;
	
	/**
	 * コマンドリスト
	 */
	private ArrayList<StaanaCommand> commandList;
	
	/**
	 * 読み込むオリジナルソース
	 * @param path
	 * @throws IOException
	 */
	public StaanaSource(Path path) throws IOException {
		
		ArrayList<StaanaWord> wordList = StaanaWordListFactory.create(path);		
		ArrayList<StaanaCommand> commandList = StaanaCommandListFactory.create(wordList);
	}
}
