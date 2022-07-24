package jp.sikimu.staana.command;

import java.util.ArrayList;
import java.util.ListIterator;

import jp.sikimu.staana.word.StaanaWordList.StaanaWord;

public class StaanaCommandListFactory {

	/**
	 * コマンドリストを作成する
	 * @param wordList
	 * @return
	 */
	public static ArrayList<StaanaCommand> create(ArrayList<StaanaWord> wordList){
		
		ArrayList<StaanaCommand> list = new ArrayList<>();

		list.addAll(create(wordList, 0));
		
		return list;
	}
	
	public static ArrayList<StaanaCommand> create(ArrayList<StaanaWord> wordList, int index){
		
		ArrayList<StaanaCommand> list = new ArrayList<>();
		ListIterator<StaanaWord> iterator = wordList.listIterator(index);

		while(iterator.hasNext()) {
			
			StaanaWord staanaWord = iterator.next();	

			if(staanaWord.word.matches("^\\s*$")) {
				continue;
			}			
			
			list.add(createStaanaCommand(wordList, iterator.previousIndex()));
		}
		
		return list;
	}
	
	public static StaanaCommand createStaanaCommand(ArrayList<StaanaWord> wordList, int index){
		
		ArrayList<StaanaWord> list = new ArrayList<>();

		ListIterator<StaanaWord> iterator = wordList.listIterator(index);
		
		while(iterator.hasNext()) {
			
			StaanaWord staanaWord = iterator.next();
			if(staanaWord.word.matches("[;|\\{|\\}|\\[|\\]]")) {
				break;
			}
			list.add(staanaWord);
		}
		
		return null;//new StaanaCommand(list);
	}
}
