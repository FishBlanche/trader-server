package utility;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT);
		};
	};

	private static SimpleDateFormat getDateFormat() {
		return (SimpleDateFormat) threadLocal.get();
	}

	/**
	 * ���ڸ�ʽת������source����format��ʽ��Date��<br>
	 * ���format=��������Ϊnull����ʹ��Ĭ�ϵ�"yyyy-MM-dd"
	 * 
	 * @param source
	 *            �����ַ�
	 * @param format
	 *            ���ڸ�ʽ����ʽ
	 * @return ��ʽ�����date
	 * @throws ParseException
	 */
	public static Date parse(String source, String format)
			throws ParseException {
		SimpleDateFormat sdf_sdf = null;
		if (format == null || "".equals(format)) {
			sdf_sdf = getDateFormat();
		} else {
			sdf_sdf = new SimpleDateFormat(format);
		}
		 
		return sdf_sdf.parse(source);
	}

	/**
	 * ����orlDate����days��������
	 * 
	 * @param orlDate
	 *            Ŀǰ����
	 * @param days
	 *            �����
	 * @return ����������
	 */
	public static Date addDay(Date orlDate, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(orlDate); // ���õ�ǰ����
		c.add(Calendar.DAY_OF_MONTH, days); // ���ڷ��Ӽ�1,Calendar.DATE(��),Calendar.HOUR(Сʱ)
		Date date = c.getTime(); // ���
		return date;
	}

	/**
	 * �������Ĺ���Ϊ���date�ֶ����ָ����hoursʱ������
	 * 
	 * @param date
	 *            Ŀ������
	 * @param hours
	 *            ���ϵ�����
	 * @return
	 */
	public static Date addHours(Date date, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, hours * 60);
		return c.getTime();
	}

	/**
	 * �����ڸ�ʽ���ַ����formatΪnull���ߡ�������ʹ��Ĭ�ϵĸ�ʽ"yyyy-MM-dd"
	 * 
	 * @param date
	 *            ����
	 * @param format
	 *            ��ʽ����ʽ
	 * @return ��ʽ������ַ�
	 */
	public static String getFormat(Date date, String format) {
		SimpleDateFormat sdf_sdf = null;

		if (format == null || "".equals(format)) {
			sdf_sdf = getDateFormat();
		} else {
			sdf_sdf = new SimpleDateFormat(format);
		}
		return sdf_sdf.format(date);
	}

	/**
	 * �õ� �����ʱ��θ�ʽ����10:00-12:00
	 * 
	 * @param hours
	 *            ��ʼ�����
	 * @param to
	 *            ��������
	 * @return
	 */
	public static String getTimePeriod(int hours, int to) {
		return hours + ":00-" + to + ":00";
	}

	/**
	 * �õ���ʽ 2013-09-09 8:00-9:00
	 * 
	 * @param date
	 * @param to
	 * @return
	 */
	public static String getDateTimeFormat(Date date, Date to) {

		return getDateFormat().format(date) + "\t"
				+ getTimePeriod(getHour(date), getHour(to));
	}

	/**
	 * ��date ת��Ϊ ʱ��Ϊ0��� ���ڡ��� 2013-10-01 00:09:59 ת��Ϊ2013-10-01 00:00:00
	 * 
	 * @param date
	 *            Ҫת����date
	 * @return ��������
	 */
	public static Date transformZeroDate(Date date) {
		try {
			return getDateFormat().parse(getDateFormat().format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ݴ�������ڣ���������һ�����ڵĵڼ���<br>
	 * ��������Ϊ1����һΪ2,..., ����Ϊ7
	 * 
	 * @param date
	 *            Ҫת��������
	 * @return
	 */
	public static int getDayofWeeK(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * ��ȡ��������һ�����㣨24 Сʱ��ʱ�ӣ���<br>
	 * �� 10:04:15.250 PM ��һʱ�̣�����Ϊ 22��
	 * 
	 * @param date
	 *            Ҫ������date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	// public static void main(String[] args) {
	// System.out.println(getDayofWeeK(new Date()));
	// System.out.println(getHour(new Date()));
	// }
}
