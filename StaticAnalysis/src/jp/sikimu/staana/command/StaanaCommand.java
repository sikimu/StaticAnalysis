package jp.sikimu.staana.command;

import java.util.ArrayList;

import jp.sikimu.staana.word.StaanaWordList.StaanaWord;


/**
 * 静的解析のコマンド
 * public class StaanaCommand　などを一塊で格納して、それと連動する(){}などがあればinnnerとして格納する
 * @author sikimu
 *
 */
public class StaanaCommand {
	
	/**
	 * コマンドに使用しているワード
	 */
	private ArrayList<StaanaWord> words;
	
	/**
	 * 配下として扱うコマンド
	 */
	private ArrayList<StaanaCommand> childlen;

	public StaanaCommand(ArrayList<StaanaWord> words, ArrayList<StaanaCommand> childlen) {
		
		this.words = words;
		this.childlen = childlen;
	}
}
