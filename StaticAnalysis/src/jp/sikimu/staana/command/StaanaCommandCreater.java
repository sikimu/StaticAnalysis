package jp.sikimu.staana.command;

import java.util.Arrays;
import java.util.Iterator;

import jp.sikimu.staana.originalsource.OriginalSource;
import jp.sikimu.staana.originalsource.OriginalSourceBlock;

/**
 * 静的解析用にデータ作成
 * 
 * @author sikimun
 *
 */
public class StaanaCommandCreater{

	public static StaanaGroup create(OriginalSource source) {

		return create(source, source.iterator());
	}

	public static StaanaGroup create(OriginalSource source, Iterator<OriginalSourceBlock> iterator) {

		StaanaGroup root = new StaanaGroup();
		
		while(iterator.hasNext()) {
			
			StaanaCommand command = createCommand(source, iterator);
			root.add(command);	
			command.createSourceCode(source);
		}

		return root;
	}
	
	/**
	 * 1コマンド分を取得する
	 * @param source
	 * @param iterator
	 * @return
	 */
	public static StaanaCommand createCommand(OriginalSource source, Iterator<OriginalSourceBlock> iterator) {
		
		final String[] END_POINT = {";","{","}"};//コマンドの終わり
		
		StaanaCommand command = new StaanaCommand();
		while(iterator.hasNext()) {
			OriginalSourceBlock block = iterator.next();
			command.add(block);
			if(Arrays.asList(END_POINT).contains(source.getSingleText(block))) {
				break;
			}
		}
		
		return command;
	}
}
