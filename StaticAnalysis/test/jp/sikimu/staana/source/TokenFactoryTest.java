package jp.sikimu.staana.source;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import jp.sikimu.staana.source.token.TokenFactory;
import jp.sikimu.staana.source.token.TokenIterator;

class TokenFactoryTest {

	@Test
	void test() {
		
		assertThrows(NullPointerException.class, () -> {new TokenFactory(null);});
	}
	
	@Test
	void singleCommentTest() throws IOException {
		
		{
			TokenFactory tokenFactory = new TokenFactory("//");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("//", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("//\r\n");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("//", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("//a\r\n");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("//a", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("a//\r\n");
			TokenIterator tokenList = tokenFactory.create();
			tokenList.next();
			assertEquals("//", tokenList.next().tokenWord);
		}
	}
	
	@Test
	void multiCommentTest() throws IOException {
		
		{
			TokenFactory tokenFactory = new TokenFactory("/**/");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/*a*/");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("/*a*/", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("a/**/");
			TokenIterator tokenList = tokenFactory.create();
			tokenList.next();
			assertEquals("/**/", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/**/a");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/*\r\n*/");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("/*\r\n*/", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("\r\n/**/");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.next().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/**/\r\n");
			TokenIterator tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.next().tokenWord);
		}
	}
}
