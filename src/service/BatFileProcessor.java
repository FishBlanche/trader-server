package service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import po.BatContentBean;
import po.ErrorBean;
import utility.BaseUtil;
import utility.FileUtil;

/**
 * 文档类型的分类
 * 
 */
enum RULES {
	NODETYPE("nodeType"), SENSORTYPE("sensorType"), NODEINFO("nodeInfo");

	private String value;

	RULES(String aValue) {

		value = aValue;
	}

	public String toString() {

		return this.value;
	}
}

/**
 * 批处理文件的处理器，将文档分为各种BatContentBean
 */
public class BatFileProcessor {

	private List<BatContentBean> batContentList;
	private List<String> contentStringList;
	private List<String> contentTypeList;
	public static final String lineSeparator = "@@";
	private static int correctNum = 0;
	private static List<ErrorBean> errorList = new ArrayList();

	public BatFileProcessor() {

		contentTypeList = new ArrayList<String>();
		batContentList = new ArrayList<BatContentBean>();
	}

	public BatFileProcessor(File batFile) {

		this();
		contentStringList = FileUtil.readFileToList(batFile);
		getBatContentTypeList(lineSeparator);
		setBatContentListBean();
//		System.out.println(contentStringList);
	}

	/**
	 * 增加数据无误的记录数目
	 */
	public static void addCorrectRecordNum() {

		correctNum++;
	}

	public static int getCorrectRecordNum() {

		return correctNum;
	}

	/**
	 * 将错误列表清空
	 */
	public static void initErrorList() {

		correctNum = 0;
		errorList.clear();
	}

	public static void addErrorList(ErrorBean error) {

		errorList.add(error);
	}

	public static List getErrorList() {

		return errorList;
	}

	/**
	 * 取得记录数
	 * 
	 * @return
	 */
	public int getNumOfRecords() {

		int records = 0;
		for (BatContentBean contentBean : batContentList) {

			records += contentBean.getRecordsNum();
		}

		return records;
	}

	/**
	 * 
	 * @param rules
	 *            List the sequence it has to accord.
	 * @return
	 */
	private List<String> getSequencedTypeList(RULES[] rules) {

		List<String> sequenceList = new ArrayList<String>();

		for (int i = 0; i < rules.length; i++) {

			if (isContainable(rules[i].toString())) {

				sequenceList.add(rules[i].toString());
			}
		}

		return sequenceList;
	}

	/**
	 * This time, I array the sequence of the list.
	 * 
	 * @return
	 */
	private void setBatContentListBean() {

		List<String> sequenceList = getSequencedTypeList(RULES.values());

		for (String contentType : sequenceList) {

			batContentList.add(getBatContent(contentType));
		}
	}

	public List<BatContentBean> getBatContentListBean() {

		return batContentList;
	}

	/**
	 * 根据内容类型，将BatContentBean对象初始化，返回
	 * 
	 * @param contentType
	 * @return
	 */
	public BatContentBean getBatContent(String contentType) {

		if (!contentTypeList.contains(contentType)) {

			return null;
		}

		BatContentBean batContentBean = new BatContentBean(contentType);
		Map<Integer, String> map = new HashMap<Integer, String>();

		int headIndex = getContentLineNumber(BaseUtil.HEAD, contentType);
		int tailIndex = getContentLineNumber(BaseUtil.TAIL, contentType);

//		System.out.println(headIndex + " : " + tailIndex);
		// Index is corresponding to actual index and tailIndex lineNum is '@@'
		for (int i = headIndex; i < tailIndex - 1; ++i) {

			map.put(i + 1, contentStringList.get(i));
		}

		batContentBean.setLineContentMap(map);

		return batContentBean;
	}

	/**
	 * 
	 * @param lineSeparator
	 * @return The corresponding line number of content type in the list.
	 */
	private List<Integer> getBatContentLineNumList(String lineSeparator) {

		boolean flag = false;
		List<Integer> list = new ArrayList<Integer>();

		int lineNum = 0;

		for (String lineText : contentStringList) {

			lineNum++;
			if (lineText.trim().startsWith(lineSeparator)) {

				flag = true;
				list.add(lineNum);
				continue;
			}

			if (flag) {

				flag = false;
			}
		}

		return list;
	}

	private void getBatContentTypeList(String lineSeparator) {

		boolean flag = false;

		for (String lineText : contentStringList) {

			if (lineText.trim().startsWith(lineSeparator)) {

				flag = true;
				continue;
			}

			if (flag) {

				contentTypeList.add(lineText);
				flag = false;
			}
		}
	}

	/**
	 * 解析文档之后所得的所有内容中是否包含该内容类型
	 * 
	 * @param contentType
	 *            该内容类型
	 * @return
	 */
	private boolean isContainable(String contentType) {

		for (String tempType : contentTypeList) {

			if (BaseUtil.StringSafe(contentType).equalsIgnoreCase(tempType)) {

				return true;
			}
		}

		return false;
	}

	public List<String> getContentTypeList() {

		return contentTypeList;
	}

	/**
	 * 为了获取对应内容头和内容尾的行数，这样可以算出该内容的行数(tailIndex-headIndex)
	 * 
	 * @param headOrTail
	 *            内容头或者尾
	 * @param contentType
	 *            内容类型
	 * @return
	 */
	private Integer getContentLineNumber(String headOrTail, String contentType) {

		int headIndex = 0;
		int tailIndex = 0;
		List<Integer> list = getBatContentLineNumList(lineSeparator);

		headIndex = tailIndex = list.get(0);
		for (int i = 0; i < contentTypeList.size(); ++i) {

			tailIndex = list.get(i + 1);
			if (BaseUtil.StringSafe(contentTypeList.get(i)).equalsIgnoreCase(
					(contentType))) {

				break;
			}
			headIndex = tailIndex;
		}

		if (headIndex == tailIndex) {

			return null;
		}

		if (BaseUtil.HEAD.equals(headOrTail)) {

			return headIndex;
		} else if (BaseUtil.TAIL.equals(headOrTail)) {

			return tailIndex;
		}

		return null;
	}
}
