package jp.sikimu.staana.command;

import java.util.ArrayList;
import java.util.List;

import jp.sikimu.staana.originalsource.OriginalSource;
import jp.sikimu.staana.originalsource.OriginalSourceBlock;

/**
 * 静的解析ようにオリジナルソースのブロックの塊
 * @author sikimun
 *
 */
public class StaanaCommand implements StaanaData{

	/**
	 * 対象となるブロックの一覧
	 */
	private ArrayList<OriginalSourceBlock> blocks;

	public StaanaCommand() {

		blocks = new ArrayList<OriginalSourceBlock>();
	}
	
	/**
	 * ブロックの追加
	 * @param block
	 */
	void add(OriginalSourceBlock block) {

		blocks.add(block);
	}

	public String createSourceCode(OriginalSource source) {
		
		List<String> list = blocks.stream().map(b -> source.getSingleText(b)).toList();
		
		return String.join("", list);
	}
}