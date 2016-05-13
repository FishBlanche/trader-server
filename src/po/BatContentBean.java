package po;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import utility.BaseUtil;

/**
 * 将文档按照类型分对象， contentType 内容类型为nodeType, sensorType, nodeInfo其中的一种
 * lineContentMap为每行对象的内容
 */
public class BatContentBean {

	private String contentType;
	private Map<Integer, String> lineContentMap;

	public BatContentBean() {

		lineContentMap = new TreeMap<Integer, String>();
	}

	public BatContentBean(String aContentType) {

		this();
		contentType = aContentType;
	}

	/**
	 * 根据下标获得该下标所对应的行数，返回该行数下的内容
	 * 
	 * @param indexInSet
	 * @return
	 */
	public String getContentStringByIndex(int indexInSet) {

		Integer lineNum = this.getLineNumber(indexInSet);

		if (lineNum == null) {

			return null;
		}

		return lineContentMap.get(Integer.valueOf(lineNum));
	}

	/**
	 * 获得内容的行数
	 * 
	 * @return
	 */
	public int getRecordsNum() {

		int recordsNum = getLineNumber(BaseUtil.TAIL)
				- getLineNumber(BaseUtil.HEAD) - 1;

		return recordsNum;
	}

	/**
	 * 根据下标获得对应的行数
	 * 
	 * @param indexInSet
	 * @return
	 */
	public Integer getLineNumber(int indexInSet) {

		TreeSet<Integer> lineNumberSet = new TreeSet(lineContentMap.keySet());
//		System.out.println("lineNumberSet : " + lineNumberSet);
		Iterator<Integer> iter = lineNumberSet.iterator();
		int i = 0;
		int beginIndex = iter.next();
		iter = lineNumberSet.iterator();

		do {

			if (i == indexInSet) {

				return (beginIndex + i);
			}
			iter.next();
			i++;
		} while (iter.hasNext());

		return null;
	}

	public String toString() {

		String contentText = contentType + ": \n";

		contentText += lineContentMap;

		return contentText;
	}

	/**
	 * 获取内容头或内容尾的行号
	 * 
	 * @param aHeadOrTail
	 * @return
	 */
	public Integer getLineNumber(String aHeadOrTail) {

		String headOrTail = BaseUtil.StringSafe(aHeadOrTail);

		if (headOrTail.equalsIgnoreCase(BaseUtil.HEAD)) {

			return getLineNumber(0);
		} else if (headOrTail.equalsIgnoreCase(BaseUtil.TAIL)) {

//			System.out.println("Num : " + lineContentMap.size());
			return getLineNumber(lineContentMap.size() - 1);
		}

		return null;
	}

	public Map<Integer, String> getLineContentMap() {

		return lineContentMap;
	}

	public String getContentType() {
		return contentType;
	}

	public List<String> getContentString() {

		Collection<String> valuesCollection = lineContentMap.values();

		return (new ArrayList<String>(valuesCollection));
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setLineContentMap(Map<Integer, String> lineContentMap) {
		this.lineContentMap = lineContentMap;
	}
}
