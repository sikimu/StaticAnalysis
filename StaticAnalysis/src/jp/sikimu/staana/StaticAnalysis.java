package jp.sikimu.staana;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jp.sikimu.staana.command.StaanaCommandCreater;
import jp.sikimu.staana.command.StaanaGroup;
import jp.sikimu.staana.originalsource.OriginalSource;
import jp.sikimu.staana.originalsource.OriginalSourceReader;

/**
 * 静的解析
 * @author sikimu
 *
 */
public class StaticAnalysis {

	public static void main(String[] args) {
		Path path = Paths.get(args[0]);

		OriginalSource originalSource;
		try {
			originalSource = OriginalSourceReader.read(path);
			
			StaanaGroup root =  StaanaCommandCreater.create(originalSource);
			
			System.out.println(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
