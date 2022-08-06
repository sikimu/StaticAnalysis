package jp.sikimu.staana.source.token;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class TokenFactoryTest {

	@Test
	void test() {
		
		assertThrows(NullPointerException.class, () -> {new TokenFactory(null);});
	}
	
	@Test
	void createCommentTest() throws IOException {
		
		{
			TokenFactory tokenFactory = new TokenFactory("/**/");
			Optional<Token> token = tokenFactory.createComment();
			assertEquals("/**/", token.get().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/*test*/");
			Optional<Token> token = tokenFactory.createComment();
			assertEquals("/*test*/", token.get().tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("a/*test*/");
			Optional<Token> token = tokenFactory.createComment();
			assertTrue(token.isEmpty());
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/*");
			Optional<Token> token = tokenFactory.createComment();
			assertTrue(token.isEmpty());
		}
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
