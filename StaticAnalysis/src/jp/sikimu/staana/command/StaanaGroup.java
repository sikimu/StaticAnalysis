package jp.sikimu.staana.command;

import java.util.ArrayList;

/**
 * 静的解析用グループ
 * @author sikimu
 *
 */
public class StaanaGroup implements StaanaData {

	/**
	 * 対象となるコマンドのリスト
	 */
	private ArrayList<StaanaData> datas;

	public StaanaGroup() {

		datas = new ArrayList<StaanaData>();
	}
	
	/**
	 * コマンドの追加
	 * @param block
	 */
	void add(StaanaData block) {

		datas.add(block);
	}
}
