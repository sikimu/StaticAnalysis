package jp.sikimu.staana.source;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * 解析用ソース作成
 * 
 * @author sikimu
 *
 */
public class SourceFactory {

	public static Source create(Path path) throws IOException {

		String originalSource = Files.readString(path, StandardCharsets.UTF_8);
		
		TokenFactory tokenFactory = new TokenFactory(originalSource);
		ArrayList<Token> tokenList = tokenFactory.create();
		
		StatementFactory statementFactory = new StatementFactory(tokenList);
		ArrayList<Statement> statementList = statementFactory.create();

		return new Source(statementList);
	}
}
