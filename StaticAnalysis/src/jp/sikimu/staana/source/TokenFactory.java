package jp.sikimu.staana.source;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * トークン作成
 * 
 * @author sikimu
 *
 */
public class TokenFactory {

	/**
	 * 読み込んだオリジナルソース
	 */
	private final String originalSource;

	private int offset;
	
	public TokenFactory(Path path) throws IOException {

		originalSource = Files.readString(path, StandardCharsets.UTF_8);
		offset = 0;
	}

	public ArrayList<Token> create() {
		
		ArrayList<Token> list = new ArrayList<>();
		
		while(offset < originalSource.length()) {
			
			// 空白はいらないので除去　
			if(Character.isWhitespace(originalSource.codePointAt(offset))) {
				offset++;	
				continue;
			}			
			
			Token token = createComment()
					.or(() -> createString())
					.or(() -> createDigit())
					.or(() -> createWord())
					.or(() -> createSeparator())
					.or(() -> createOperator())
					.orElseThrow(() -> new NoSuchElementException(originalSource.substring(offset)));
			
			list.add(token);
		}
	
		return list;
	}
	
	/**
	 * コメントトークン作成
	 * @param offset
	 * @return
	 */
	private Optional<Token> createComment(){
		
		final String offsetSource = originalSource.substring(offset);
		// マルチコメント
		if(offsetSource.startsWith("/*")) {		
			String word = offsetSource.substring(0, offsetSource.indexOf("*/") + 2);
			Optional<Token> token = Optional.of(new Token(offset, word));
			offset = offset + word.length();
			return token;
		}
		// シングルコメント
		else if(offsetSource.startsWith("//")) {	
			String word = offsetSource.substring(0, offsetSource.indexOf("\n"));
			Optional<Token> token = Optional.of(new Token(offset, word));
			offset = offset + word.length();
			return token;
		}
		
		return Optional.empty();
	}
	
	/**
	 * 文字/文字列トークン作成
	 * @param offset
	 * @return
	 */
	private Optional<Token> createString(){
		
		final String offsetSource = originalSource.substring(offset);
		// 文字列
		if(offsetSource.startsWith("\"")) {
			String word = originalSource.substring(offset, originalSource.indexOf('"', offset + 1) + 1);	
			Optional<Token> token = Optional.of(new Token(offset, word));
			offset = offset + word.length();
			return token;
		}
		// 文字
		else if(offsetSource.startsWith("'")) {
			String word = originalSource.substring(offset, originalSource.indexOf('\'', offset + 1) + 1);	
			Optional<Token> token = Optional.of(new Token(offset, word));
			offset = offset + word.length();
			return token;
		}
		
		return Optional.empty();
	}
	
	/**
	 * 単語トークン作成
	 * @param offset
	 * @return
	 */
	private Optional<Token> createWord(){
		
		final String offsetSource = originalSource.substring(offset);
		if(Character.isJavaIdentifierStart(originalSource.codePointAt(offset))) {
			IntStream wordStream = offsetSource.codePoints().takeWhile(Character::isJavaIdentifierPart);
			String word = wordStream.collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append).toString();
			Optional<Token> token = Optional.of(new Token(offset, word));
			offset = offset + word.length();
			return token;
		}
		
		return Optional.empty();
	}
	
	
	/**
	 * 値トークン作成
	 * @param offset
	 * @return
	 */
	private Optional<Token> createDigit(){
		
		final String offsetSource = originalSource.substring(offset);
		if(Character.isDigit(originalSource.codePointAt(offset))) {
			IntStream wordStream = offsetSource.codePoints().takeWhile(Character::isDigit);
			String word = wordStream.collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append).toString();		
			Optional<Token> token = Optional.of(new Token(offset, word));
			offset = offset + word.length();
			return token;
		}
		
		return Optional.empty();
	}
	
	/**
	 * 分離子トークン作成
	 */
	private Optional<Token> createSeparator(){
		
		final String[] separators = { "(", ")", "{", "}", "[", "]", ";", ",", ".", "...", "@", "::" };
		
		final String offsetSource = originalSource.substring(offset);
		
		for(String separator : separators) {
			if(offsetSource.startsWith(separator)) {	
				Optional<Token> token = Optional.of(new Token(offset, separator));
				offset = offset + separator.length();
				return token;
			}
		}
		return Optional.empty();
	}

	
	/**
	 * 演算子トークン作成
	 */
	private Optional<Token> createOperator(){
		
		final String[] operators = {
				"=",">","<","!","~","?",":","->",
				"==",">=","<=","!=","&&","||","++","--",
				"+","-","*","/","&","|","^","%","<<",">>",">>>",
				"+=","-=","*=","/=","&=","|=","^=","%=","<<=",">>=",">>>="
		};
		
		final String offsetSource = originalSource.substring(offset);
		
		for(String operator : operators) {
			if(offsetSource.startsWith(operator)) {	
				Optional<Token> token = Optional.of(new Token(offset, operator));
				offset = offset + operator.length();
				return token;
			}
		}
		return Optional.empty();
	}
}
