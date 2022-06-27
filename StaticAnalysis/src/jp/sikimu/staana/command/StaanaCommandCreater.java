package jp.sikimu.staana.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jp.sikimu.staana.originalsource.OriginalSource;
import jp.sikimu.staana.originalsource.OriginalSourceBlock;

/**
 * 静的解析用にデータ作成
 * 
 * @author sikimu
 *
 */
public class StaanaCommandCreater{

	public static StaanaGroup create(OriginalSource source) {

		return create(source, source.iterator());
	}

	public static StaanaGroup create(OriginalSource source, Iterator<OriginalSourceBlock> iterator) {

		final String[] END_POINT = {";","{","}"};//コマンドの終わり
		
		StaanaGroup root = new StaanaGroup();
		List<OriginalSourceBlock> stack = new ArrayList<OriginalSourceBlock>();
		
		while(iterator.hasNext()) {
			
			OriginalSourceBlock block = iterator.next();
			String text = source.getSingleText(block);
			
			if(Arrays.asList(END_POINT).contains(source.getSingleText(block))) {
				
				StaanaCommand command = new StaanaCommand();
				command.addAll(stack);
				System.out.println(command.createSourceCode(source));
				root.add(command);
				stack.clear();
				
				StaanaCommand command2 = new StaanaCommand();
				command2.add(block);
				System.out.println(command2.createSourceCode(source));
				root.add(command2);
				continue;
			}			
			
			stack.add(block);
		}

		return root;
	}
}
