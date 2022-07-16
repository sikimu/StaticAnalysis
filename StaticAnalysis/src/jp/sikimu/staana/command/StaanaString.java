package jp.sikimu.staana.command;

import java.util.ArrayList;

import jp.sikimu.staana.word.StaanaWord;

/**
 * 文字列の塊
 * @author sikimun
 *
 */
public class StaanaString extends StaanaCommand{

	/**
	 * 対象となるブロックの一覧
	 */
	private ArrayList<StaanaWord> words;

	public StaanaString() {

		words = new ArrayList<StaanaWord>();
	}
	
	/**
	 * ワードの追加
	 * @param word
	 */
	void add(StaanaWord word) {

		words.add(word);
	}
}