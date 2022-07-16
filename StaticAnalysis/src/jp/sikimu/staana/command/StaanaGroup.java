package jp.sikimu.staana.command;

import java.util.ArrayList;

/**
 * 静的解析用グループ
 * @author sikimu
 *
 */
public class StaanaGroup extends StaanaCommand {

	/**
	 * 対象となるコマンドのリスト
	 */
	private ArrayList<StaanaCommand> words;

	public StaanaGroup() {

		words = new ArrayList<StaanaCommand>();
	}
	
	/**
	 * コマンドの追加
	 * @param word
	 */
	void add(StaanaCommand word) {

		words.add(word);
	}
}
