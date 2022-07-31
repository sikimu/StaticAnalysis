package jp.sikimu.staana.source;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TokenFactoryTest {

	@Test
	void test() {
		
		assertThrows(NullPointerException.class, () -> {new TokenFactory(null);});
	}
	
	@Test
	void singleCommentTest() throws IOException {
		
		{
			TokenFactory tokenFactory = new TokenFactory("//");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("//", tokenList.get(0).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("//\r\n");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("//", tokenList.get(0).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("//a\r\n");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("//a", tokenList.get(0).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("a//\r\n");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("//", tokenList.get(1).tokenWord);
		}
	}
	
	@Test
	void multiCommentTest() throws IOException {
		
		{
			TokenFactory tokenFactory = new TokenFactory("/**/");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.get(0).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/*a*/");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("/*a*/", tokenList.get(0).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("a/**/");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.get(1).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/**/a");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.get(0).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/*\r\n*/");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("/*\r\n*/", tokenList.get(0).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("\r\n/**/");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.get(0).tokenWord);
		}
		{
			TokenFactory tokenFactory = new TokenFactory("/**/\r\n");
			ArrayList<Token> tokenList = tokenFactory.create();
			assertEquals("/**/", tokenList.get(0).tokenWord);
		}
	}
}
