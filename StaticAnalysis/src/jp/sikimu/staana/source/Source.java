package jp.sikimu.staana.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 静的解析用ソース
 * 
 * @author sikimu
 *
 */
public class Source {

	public final List<Statement> statementList;
	
	public Source(ArrayList<Statement> statementList) {

		this.statementList = Collections.unmodifiableList(statementList);
	}
}
