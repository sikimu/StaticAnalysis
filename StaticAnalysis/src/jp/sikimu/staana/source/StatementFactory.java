package jp.sikimu.staana.source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jp.sikimu.staana.source.token.Token;
import jp.sikimu.staana.source.token.TokenIterator;

/**
 * ステートメント作成
 * 
 * @author sikimu
 *
 */
public class StatementFactory {

	private TokenIterator tokenIterator;

	public StatementFactory(TokenIterator tokenIterator) {

		this.tokenIterator = tokenIterator;
	}

	public ArrayList<Statement> create() {
		
		ArrayList<Statement> list = new ArrayList<>();

		while(tokenIterator.hasNext()) {

			Statement statement = createBlock()
					.or(() -> createUnknown())
					.orElseThrow(() -> new NoSuchElementException(tokenIterator.next().toString()));
			
			list.add(statement);
		}
	
		return list;
	}
	
	/**
	 * ブロックステートメント作成
	 * @return
	 */
	private Optional<Statement> createBlock(){
		
		if(tokenIterator.isBucketStartNext()) {
			
			Token start = tokenIterator.next();
			
			ArrayList<Statement> list = new ArrayList<Statement>();
			while(tokenIterator.hasNext()) {

				if(tokenIterator.isBucketEndNext()) {
					
					Token end = tokenIterator.next();
					return Optional.of(new Statement.Block(start, end, list));
				}
				
				Statement statement = createBlock()
						.or(() -> createUnknown())
						.orElseThrow(() -> new NoSuchElementException(tokenIterator.next().toString()));
				
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
		
		Statement.Unknown statement = new Statement.Unknown(tokenIterator.next());

		return Optional.of(statement);
	}
}
