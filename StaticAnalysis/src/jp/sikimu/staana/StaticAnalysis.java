package jp.sikimu.staana;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jp.sikimu.staana.source.Source;
import jp.sikimu.staana.source.SourceFactory;
import jp.sikimu.staana.source.Statement;

/**
 * 静的解析
 * @author sikimu
 *
 */
public class StaticAnalysis {

	public static void main(String[] args) {
		Path path = Paths.get(args[0]);

		try {
			
			Source source = SourceFactory.create(path);
			
			int max = maxHierarchy(source.statementList);
			
			System.out.println("max=" + max);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 最大階層(階層がないものを1とする)
	 * @param source
	 * @return
	 */
	private static int maxHierarchy(List<Statement> list) {
		
		int max = 0;
		
		for (Statement statement : list) {
			
			if(statement instanceof Statement.Block) {
				Statement.Block block = (Statement.Block)statement;
				int m = maxHierarchy(block.statementList);
				if(max < m) {
					max = m;
				}
			}
		}
		
		return 1 + max;
	}
	
	/**
	 * 読み込みのテスト用に作ってあるメソッド
	 * @param a
	 */
	public void test(int a) {
		//test 		やで
		String str = "aaaaaa       bbbbbbbbbbbbbbbbbb";
		
		char c1 = 'c';
		String あいう = "aaa";
		
		int b = 0x11;
		b++;
		b--;
		b += 1;
		b -= 1;
		b *= １;
		b /= 1;
		
		System.out.print(str + b + c1 + あいう);
	}
}
