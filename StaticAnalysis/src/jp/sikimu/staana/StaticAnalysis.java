package jp.sikimu.staana;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jp.sikimu.staana.source.Source;
import jp.sikimu.staana.source.SourceFactory;

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
			
			System.out.println(source.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
