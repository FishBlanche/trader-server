package po;

/**
 * 错误包括行号，出错的内容，以及出错的原因
 */
public class ErrorBean {
	private int lineNumber;
	private String content;
	private String cause;

	public ErrorBean() {

	}

	public ErrorBean(int lineNumber, String cause) {
		super();
		this.lineNumber = lineNumber;
		this.cause = cause;
	}

	public ErrorBean(String content, int lineNumber) {

		this.lineNumber = lineNumber;
		this.content = content;
	}

	public ErrorBean(int lineNumber, String content, String cause) {

		this(lineNumber, cause);
		this.content = content;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String toString() {

		return "Line" + lineNumber + " :" + cause;
	}
}
