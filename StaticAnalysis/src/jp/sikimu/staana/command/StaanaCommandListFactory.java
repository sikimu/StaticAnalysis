package jp.sikimu.staana.command;

import java.util.ArrayList;
import java.util.Iterator;

import jp.sikimu.staana.word.StaanaWord;

public class StaanaCommandListFactory {

	/**
	 * コマンドリストを作成する
	 * @param wordList
	 * @return
	 */
	public static ArrayList<StaanaCommand> create(ArrayList<StaanaWord> wordList){
		
		ArrayList<StaanaCommand> list = new ArrayList<>();
		
		Iterator<StaanaWord> iterator = wordList.iterator();
		while(iterator.hasNext()) {
			
			StaanaWord offsetWord = iterator.next();
			
			//文字列
			if(offsetWord.equals("\"")) {
				
				StaanaString str = new StaanaString();
				str.add(offsetWord);
				while(iterator.hasNext()) {
										
					StaanaWord word = iterator.next();
					str.add(word);
					if(word.equals("\"")) {
						break;
					}
				}
				list.add(str);
			}
		}
		
		return list;
	}
}
