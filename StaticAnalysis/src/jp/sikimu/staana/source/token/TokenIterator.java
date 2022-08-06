package jp.sikimu.staana.source.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * トークンイテレーター
 * @author sikimu
 *
 */
public class TokenIterator implements Iterator<Token>{

	private List<Token> tokenList;
	
	private int offset;
	
	TokenIterator(ArrayList<Token> tokenList) {

		offset = 0;
		this.tokenList = Collections.unmodifiableList(tokenList);
	}
	
	void goBack() {
		
		offset--;
	}
	
	@Override
	public boolean hasNext() {

		return offset < this.tokenList.size();
	}

	@Override
	public Token next() {

		Token token = this.tokenList.get(offset);
		
		offset++;
		
		return token;
	}

	public boolean isBucketStartNext() {

		return this.tokenList.get(offset).isBucketStart();
	}

	public boolean isBucketEndNext() {

		return this.tokenList.get(offset).isBucketEnd();
	}

}
