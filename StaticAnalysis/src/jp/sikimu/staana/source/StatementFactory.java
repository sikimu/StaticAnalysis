package jp.sikimu.staana.source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * ステートメント作成
 * 
 * @author sikimu
 *
 */
public class StatementFactory {

	private List<Token> tokenList;
	
	private int offset;
	
	public StatementFactory(ArrayList<Token> tokenList) throws IOException {

		offset = 0;
		this.tokenList = Collections.unmodifiableList(tokenList);
	}

	public ArrayList<Statement> create() {
		
		ArrayList<Statement> list = new ArrayList<>();

		while(offset < tokenList.size()) {

			Statement statement = createBlock()
					.or(() -> createUnknown())
					.orElseThrow(() -> new NoSuchElementException(tokenList.toString()));
			
			list.add(statement);
		}
	
		return list;
	}
	
	/**
	 * ブロックステートメント作成
	 * @return
	 */
	private Optional<Statement> createBlock(){
		
		if(tokenList.get(offset).isBucketStart()) {
			
			Token start = tokenList.get(offset);
			offset++;
			
			ArrayList<Statement> list = new ArrayList<Statement>();
			while(offset < tokenList.size()) {

				if(tokenList.get(offset).isBucketEnd()) {
					
					Token end = tokenList.get(offset);
					offset++;
					return Optional.of(new Statement.Block(start, end, list));
				}
				
				Statement statement = createBlock()
						.or(() -> createUnknown())
						.orElseThrow(() -> new NoSuchElementException(tokenList.toString()));
				
				list.add(statement);
			}
		}
		
		return Optional.empty();
	}
	
	/**
	 * 不明ステートメント作成
	 * @param offset
	 * @return
	 */
	private Optional<Statement> createUnknown(){
		
		Statement.Unknown statement = new Statement.Unknown(tokenList.get(offset));
		
		offset = offset + 1;
		
		return Optional.of(statement);
	}
}
