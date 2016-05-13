package utility;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class BaseUtil {

	public static final String PROJECT_DEFAULT_ENCODING = "UTF-8";
	public static final String HEAD = "HEAD";
	public static final String TAIL = "TAIL";
	public static final String SQL_ERROR = "SqlError";
	public static final String FILEUPLOAD_ERROR = "FileUploadError";
	public static final String IO_ERROR = "IOError";
	public static final String SECURITY_ERROR = "SecurityError";

	/**
	 * Judge if the number is in a normal range.
	 * 
	 * @param num
	 * @param low_range
	 * @param high_range
	 * @return
	 */
	public static boolean isQualified(float num, float low_range,
			float high_range) {
		boolean flag = false;

		if (num <= high_range && num >= low_range) {

			flag = true;
		}
		return flag;
	}

	public static boolean isEmpty(Object arg) {
		if (StringSafe(arg).equals("")) {
			return true;
		}
		return false;
	}

	public static String translate(String field) {

		if ("".equals(StringSafe(field))) {

			return null;
		}
		return field;
	}

	public static String StringSafe(Object strParam) {
		String value = "";
		if (strParam == null) {
			value = "";
		} else {
			try {
				value = String.valueOf(strParam);
			} catch (Exception e) {
				value = "";
			}
		}
		if (value.equals("null")) {
			value = "";
		}
		return value.trim();
	}

	public static String padLeft(String orginString, String addStr, int len) {
		StringBuffer strBuffer = new StringBuffer("");
		if (orginString == null) {
			orginString = "";
		}
		if (orginString.length() >= len) {
			return orginString;
		}
		if (addStr != null) {
			while (strBuffer.length() + orginString.length() < len) {
				strBuffer.append(addStr);
			}
		}

		strBuffer.append(orginString);
		return strBuffer.toString();
	}

	public static String padRight(String orginString, String addStr, int len) {
		StringBuffer strBuffer = new StringBuffer("");
		if (orginString == null) {
			orginString = "";
		}
		if (orginString.length() >= len) {
			return orginString;
		}
		if (addStr != null) {
			strBuffer.append(orginString);
			while (strBuffer.length() < len) {
				strBuffer.append(addStr);
			}
		}
		return strBuffer.toString();
	}

	/***************************************************************************
	 * 将null字符串转换为0
	 */
	public static String NumSafe(Object strParam) {
		String value = "0";
		if (strParam == null) {
			value = "0";
		}
		try {
			value = String.valueOf(strParam);
		} catch (Exception e) {
			value = "0";
		}
		if (value == "null" || value.length() == 0) {
			value = "0";
		}
		return value.trim();
	}

	/***************************************************************************
	 * 将字符串转换为Int
	 */
	public static int String2Int(Object argValue) {
		if (isEmpty(argValue)) {
			return 0;
		} else {
			return Integer.parseInt(String.valueOf(argValue));
		}
	}

	/***************************************************************************
	 * 格式化SQL语句
	 */
	public static String FormatSQLStr(String argValue) {
		String returnValue = StringSafe(argValue);
		if (returnValue.length() == 0) {
			return "";
		} else {
			returnValue = returnValue.replaceAll("\"\"", "\"\"\"\"")
					.replaceAll("'", "''");
		}
		return returnValue;
	}

	/***************************************************************************
	 * 格式化钱四舍五入小数点后两位
	 */
	public static String fmtMoney(Object strParam) {
		String value = "0";
		if (strParam == null) {
			value = "0";
		}
		try {
			Double dblValue = Double.valueOf(strParam.toString());
			if (dblValue.doubleValue() == 0) {
				value = "0.00";
			} else {
				DecimalFormat df1 = new DecimalFormat("######.00");
				value = df1.format(dblValue.doubleValue());
			}
			if (Double.parseDouble(value) < 1 && Double.parseDouble(value) > 0) {
				value = "0" + value;
			}
		} catch (Exception e) {
			value = "0";
		}
		if (value == "null" || value.length() == 0) {
			value = "0";
		}
		return value.trim();
	}

	public static String getBasePath(HttpServletRequest req) {
		String xmlPath = req.getSession().getServletContext()
				.getRealPath("/WEB-INF/config/filepath.xml");
		String base = "";
		try {
			base = FileUtil.findUploadPath(xmlPath, "basePath");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return base;
	}

	public static String StringTrim(String input) {

		if (input == null) {

			return "";
		} else {

			return input.trim();
		}
	}

	/***************************************************************************
	 * 将超出长度的字符串转换为...
	 */
	public static String trancate(String input, int len) {
		StringBuffer sb = new StringBuffer(len);
		int count = 0;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c <= '\u00ff') {
				count++;
			} else {
				count += 2;
				if (count > len) {
					return sb
							.append(" <font color='blue'><strong>...<strong/></font>")
							.toString().trim();
				}
			}
			sb.append(c);
			if (count >= len) {
				return sb
						.append(" <font color='blue'><strong>...<strong/></font>")
						.toString().trim();
			}
		}
		return input;
	}

	public static String join(String[] strs, String token) {
		if (strs == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			if (i != 0) {
				sb.append(token);
			}
			sb.append(strs[i]);
		}
		return sb.toString();
	}

	public static boolean StringCheck(String str, String test) {
		if (str == null || str.equals("")) {
			return false;
		}
		boolean flag = false;
		for (int i = 0; i < test.length(); i++) {
			if (str.indexOf(test.charAt(i)) != -1) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public static String replace(String strSc, String oldStr, String newStr) {
		String ret = strSc;
		if (ret != null && oldStr != null && newStr != null) {
			ret = strSc.replaceAll(oldStr, newStr);
		}
		return ret;
	}

	public static String replaceAll(String strSc, String oldStr, String newStr) {
		int i = -1;
		while ((i = strSc.indexOf(oldStr)) != -1) {
			strSc = new StringBuffer(strSc.substring(0, i)).append(newStr)
					.append(strSc.substring(i + oldStr.length())).toString();
		}
		return strSc;
	}

	public static String toHtml(String str) {
		String html = StringSafe(str);
		if (str == null || str.length() == 0) {
			return "";
		} else {
			html = replace(html, "&", "&amp;");
			html = replace(html, "<", "&lt;");
			html = replace(html, ">", "&gt;");
			html = replace(html, "\"", "&quot;");
			html = replace(html, " ", "&nbsp");
			html = replace(html, "\r\n", "<br>");
			return html;
		}
	}

	public static String toText(String str) {
		String text = StringSafe(str);
		if (str == null || str.length() == 0) {
			return "";
		} else {
			text = replace(text, "&amp;", "&");
			text = replace(text, "&lt;", "<");
			text = replace(text, "&gt;", ">");
			text = replace(text, "<br>\n", "\n");
			text = replace(text, "<br>", "\n");
			text = replace(text, "&quot;", "\"");
			text = replace(text, "&nbsp;", " ");
			return text;
		}
	}

	public static int alphabet2No(String str) {
		String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		int no = 0;
		for (int i = 0; i < alphabet.length; i++) {
			no++;
			if (str.toUpperCase().equals(alphabet[i])) {
				break;
			}
		}
		return no;
	}

	public static String No2alphabet(int no) {
		String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		for (int i = 0; i < alphabet.length; i++) {
			if (no == i + 1) {
				return alphabet[i];
			}
		}
		return "";
	}

	/** 获取客户端IP */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String convStr(String arg, boolean flg) {
		String strWrk = "";
		if (arg == null) {
			arg = "";
		}
		if (flg) {
			try {
				strWrk = new String(arg.getBytes("ISO-8859-1"),
						PROJECT_DEFAULT_ENCODING);
			} catch (IOException e) {
				strWrk = "*** ERROR convStr()";
			}
		} else {
			strWrk = arg;
		}

		return strWrk;
	}

	public static final String randomString(int length) {
		Random randGen = null;
		char[] numbersAndLetters = null;
		Object initLock = new Object();
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
							.toCharArray();
				}
			}
		}
		char[] randBuffer = new char[4];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static double add(double d1, double d2) { // 进行加法计算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}

	public static double sub(double d1, double d2) { // 进行减法计算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}

	public static double mul(double d1, double d2) { // 进行乘法计算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.multiply(b2).doubleValue();
	}

	public static double div(double d1, double d2, int len) { // 进行乘法计算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double round(double d, int len) { // 进行四舍五入
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
