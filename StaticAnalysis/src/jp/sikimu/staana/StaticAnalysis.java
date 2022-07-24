package jp.sikimu.staana;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jp.sikimu.staana.word.StaanaSource;

/**
 * 静的解析
 * @author sikimu
 *
 */
public class StaticAnalysis {

	public static void main(String[] args) {
		Path path = Paths.get(args[0]);

		try {
			StaanaSource staanaSource = new StaanaSource(path);

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
		
		int b = 0;
		b++;
		b--;
		b += 1;
		b -= 1;
		b *= 1;
		b /= 1;
		
		System.out.print(str + b);
	}
}
