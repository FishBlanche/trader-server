package cron;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
 

import org.json.simple.JSONArray;

import po.BatContentBean;
import po.ErrorBean;
import po.NodeInformationBean;
import po.NodeTypeBean;
import po.SensorTypeBean;
import service.BatFileProcessor;
import service.NodeDBBean;
import service.NewestSensing;
import utility.BaseUtil;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import dao.MySqlConnectionHelper;
import feed.FeedSensing;

public class DataSourceServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private Statement stmt = null;
	// private PreparedStatement pstmt = null;
	/**
	 * nodeDBBean 是服务端维护的Bean对象，是application级别的
	 * 使用了hack技巧，那个ServletInitializer是在服务器一开启就可以的
	 * 
	 */
	private static NodeDBBean nodeDBBean = FeedSensing.getNodeDBBean();
	private static NewestSensing newSensing=FeedSensing.getNewestSensing();
	private String VIEW_JSP = null;
	private String JSP_FOLDER = "/";

	/**
	 * 此方法对数据进行初始化，如果一开始FeedSensing的nodeDBBean没有初始化完成， 则再次读取数据库对nodeDBBean进行初始化
	 */
	public void init() {
		boolean flag = false; // if not used the connection in this...
		 System.out.println("DataSourceServlet");
		try {
			// context = new InitialContext();
			// dataSource =
			// (DataSource)context.lookup("java:comp/env/jdbc/Citysee_DATA");

			if (nodeDBBean == null) {

				initParam(nodeDBBean);
				flag = true;
			}
			// System.out.println("Begin: " + nodeDBBean);
			// System.out.println("*****************************");
		} finally {

			if (flag) {

				MySqlConnectionHelper.close(connection);
			}
		}
	}

	public static NodeDBBean getNodeDBBean() {
		return nodeDBBean;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * 对请求进行处理，主要包括 1. 处理登录登出，对session的处理 2. 处理上传文件，对文件进行解析 3.
	 * 对数据库的增删改查，随之的是对nodeDBBean的维护
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String basePath = BaseUtil.getBasePath(req);
		String dealType = BaseUtil.StringSafe(req.getParameter("dealtype"));
		JSP_FOLDER = "/";

		resp.setContentType("text/html;charset=UTF-8");
		if(dealType.equals("province"))
		{
			List<String> list=new ArrayList<String>();
			 list.add("江苏省");
			 list.add("浙江省"); 
			 list.add("山东省");
			 list.add("湖南省");
			 list.add("海南省");
			 list.add("河南省");
			 list.add("安徽省");
			 list.add("四川省");
			 
				String respText = "";
				if (list == null) {
					respText = "None";
				} else {
					for (String text : list) {
						resp.getWriter().println(text);
					}
				}
           }
		if(dealType.equals("city"))
		{
			List<String> list=new ArrayList<String>();
			 list.add("上海市");
			 list.add("北京市"); 
			 list.add("无锡市");
			 list.add("南京市");
			 list.add("苏州市");
			 list.add("南通市");
			 list.add("黄山市");
			 list.add("兰州市");
			 
				String respText = "";
				if (list == null) {
					respText = "None";
				} else {
					for (String text : list) {
						resp.getWriter().println(text);
					}
				}
           }
		
		
		// This parameter is just for test.
		if (dealType.equals("ying")) {

			this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);
			VIEW_JSP = "index.jsp";
			gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
		}
		/*
		 * if ("authentication".equals(dealType)) {
		 * 
		 * String rootPath = "F:\\CA\\ca-cert.cer";
		 * System.out.println(rootPath); FileInputStream inUsercert = new
		 * FileInputStream(rootPath); int len = inUsercert.available(); byte[]
		 * Usercert = new byte[len]; inUsercert.read(Usercert);
		 * inUsercert.close(); BASE64Encoder encl = new BASE64Encoder(); String
		 * strCert = encl.encode(Usercert); strCert =
		 * "-----BEGIN CERTIFICATE-----" + strCert; strCert +=
		 * "-----END CERTIFICATE-----";
		 * 
		 * resp.setContentType("application/x-x509-ca-cert");
		 * resp.addHeader("Content-Disposition", "attachment; filename=" +
		 * URLEncoder.encode("ca-cert.cert", "UTF-8"));
		 * resp.getWriter().write(strCert); }
		 */

		/**
		 * 第1类请求的处理
		 */
		if ("login".equals(dealType)) {

			String username = req.getParameter("username");
			String password = req.getParameter("pwd");
			SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");  
			System.out.print("Username = " + username + " password = "
					+ password+ " IP: "+req.getRemoteAddr()+" Time:"+sDateFormat.format(new   java.util.Date()));
			String command = "";
			if (checkUserPwd(username, password)) {

				command = "index.jsp";
				HttpSession session = req.getSession(true);
				session.setAttribute("LoginOrNot", "Y");
				System.out.println("OK");
			} else {

				command = "false";
				System.out.println("ERROR");
			}

			resp.getWriter().write(command);
		} else if (dealType.equals("logout")) {

			VIEW_JSP = basePath + "login/login.jsp";

			HttpSession session = req.getSession(false);

			if (session != null) {

				session.removeAttribute("LoginOrNot");
				session.invalidate();
			}
			resp.sendRedirect(VIEW_JSP);
		}
		/**
		 * 第2类请求的处理
		 */
		else if (dealType.equals("create")) {

			VIEW_JSP = basePath + "create" + req.getParameter("selectedValue")
					+ ".jsp";
			// gotoPage(JSP_FOLDER+VIEW_JSP, req, resp);
			resp.sendRedirect(VIEW_JSP);
		} else if (dealType.equals("createFinalSensorType")) {

			createFinalSensorType(req, resp);
		} else if (dealType.equals("createFinalNodeType")) {

			createFinalNodeType(req, resp);
		} else if (dealType.equals("createFinalNodeInfo")) {

			createFinalNodeInfo(req, resp);
		} else if (dealType.equals("modify")) {

			VIEW_JSP = basePath + "modify" + req.getParameter("selectedValue")
					+ ".jsp";
			// gotoPage(JSP_FOLDER+VIEW_JSP, req, resp);
			resp.sendRedirect(VIEW_JSP);
		} else if (dealType.equals("modifyNodeTypeDir")) {

			modifyNodeTypeDir(req, resp);
		} else if (dealType.equals("modifySensorTypeDir")) {

			modifySensorTypeDir(req, resp);
		} else if (dealType.equals("modifyNodeInfoDir")) {

			modifyNodeInfoDir(req, resp);
		} else if (dealType.equals("relateSensorNames")) {

			String nodeTypePara = req.getParameter("nodeTypePara");
			List<String> list = nodeDBBean.relatedNode_Sensor(nodeTypePara);
			String respText = "";
			if (list == null) {
				respText = "None";
			} else {
				for (String text : list) {
					respText += text + " ";
				}
			}

			resp.getWriter().write(respText);
		} else if (dealType.equals("modifySensorType")) {

			modifySensorType(req, resp);
		} else if (dealType.equals("modifyNodeType")) {

			modifyNodeType(req, resp);
		} else if (dealType.equals("modifyNodeInfo")) {

			modifyNodeInfo(req, resp);
		} else if (dealType.equals("remove")) {

			VIEW_JSP = basePath + "remove" + req.getParameter("selectedValue")
					+ ".jsp";
			// gotoPage(JSP_FOLDER+VIEW_JSP, req, resp);
			resp.sendRedirect(VIEW_JSP);
		} else if (dealType.equals("removeNodeType")) {

			removeNodeType(req, resp);
		} else if (dealType.equals("removeSensorType")) {

			removeSensorType(req, resp);
		} else if (dealType.equals("removeNodeInfo")) {

			removeNodeInfo(req, resp);
		}
		/**
		 * 第3类请求的处理
		 */
		else if (dealType.equals("analyse")) {

			analyseBatFile(req, resp);
		}
	}

	/**
	 * 对用户名密码的验证
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	private boolean checkUserPwd(String username, String pwd) {

		boolean authentication = false;
		ResultSet rs = null;
		stmt = null;
		connection = null;

		try {
			connection = getConnection();
			stmt = connection.createStatement();
			String sql = "select * from NodeUserInfo";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				String userName = rs.getString("userName");
				String password = rs.getString("password");
			//	System.out.println(userName+"  "+password);
				if (BaseUtil.StringSafe(username).equals(userName)
						&& BaseUtil.StringSafe(pwd).equals(password)) {

					authentication = true;
					break;
				}
			}
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {

			MySqlConnectionHelper.closeResultSet(rs);
			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}

		return authentication;
	}

	/**
	 * 分析批处理文件的入口
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void analyseBatFile(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ServletConfig config = this.getServletConfig();
		SmartUpload su = new SmartUpload();
		List<String> errorList = new ArrayList<String>();
		PrintWriter out = resp.getWriter();

		su.initialize(config, req, resp);

	
		su.setAllowedFilesList("txt,xml,excel");
		try {
			su.upload();
			// System.out.println("----------Begin----------");

			String groupFileDir = req.getSession().getServletContext()
					.getRealPath(File.separator)
					+ "\\TempStorage\\";

			java.io.File tfile = new java.io.File(groupFileDir);
			if (!tfile.exists()) {

				tfile.mkdir();
			}

			com.jspsmart.upload.File file = su.getFiles().getFile(0);
			if (!file.isMissing()) {

				String name = "ying." + file.getFileExt();

				String absolutePath = groupFileDir + name;
				file.saveAs(absolutePath);

				File readFile = new File(absolutePath);

				BatFileProcessor batFileProcessor = new BatFileProcessor(
						readFile);

				// System.out.println("Content: ");
				// System.out.println(batFileProcessor.getBatContentListBean());

				/**
				 * Process the bat file. 将errorList作为全局的List，专门容纳错误信息，
				 * 在每次分析文件时进行初始化，清空之前上传文件的错误信息
				 */
				BatFileProcessor.initErrorList();
				processFile(batFileProcessor.getBatContentListBean(),
						batFileProcessor);
				errorList = getErrorListInBat();

				/**
				 * rightNum is the records that had been executed.
				 */
				int rightNum = BatFileProcessor.getCorrectRecordNum();
				int errorNum = batFileProcessor.getNumOfRecords() - rightNum;
				req.setAttribute("rightNum", rightNum);
				req.setAttribute("errorNum", errorNum);
				req.setAttribute("errorList", errorList);
			} else {

				out.println("<script language='javascript'>alert('No file exist !');"
						+ "history.back();</script>");
				return;
			}
		} catch (SmartUploadException e) {

			out.println("<script language='javascript'>alert('文件上传失败');"
					+ "history.back();</script>");
			e.printStackTrace();
			return;
			// throw new RuntimeException(e);
		} catch (SecurityException e) {

			out.print("<script language='javascript'>alert('上传错误：禁止上传除txt类型之外的文件');"
					+ "history.back();</script>");
			return;
			// throw new RuntimeException(e);
		} catch (SQLException e) {

			throw new RuntimeException(e);
		}

		JSP_FOLDER = "/batProcess/";
		VIEW_JSP = "batProcessResult.jsp";
		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	/**
	 * For printing the error list.
	 * 
	 * @param dataMultiMap
	 *            As introduced before, "errors" indexed for errorlist in
	 *            multimap, "dataUsable" for correct data.
	 * @return
	 */
	private List<String> getErrorListInBat() {

		return BatFileProcessor.getErrorList();
	}

	/**
	 * To analyse the NodeType data in this method.
	 * 
	 * @param dataMapList
	 *            All the data analysed in this data Map.
	 * @throws SQLException
	 */
	private void processNodeTypeFile(List<Map<String, String>> dataMapList)
			throws SQLException {

		MultiMap dataMultiMap = preCheckNodeTypeContent(dataMapList);

		insertNodeTypeBatch((List<Map<String, String>>) dataMultiMap
				.get("nodeType"));
	}

	/**
	 * To analyse the SensorType data in this method.
	 * 
	 * @param dataMapList
	 *            All the data analysed in this data Map classified by nodeType,
	 *            nodeInfo, sensorType.
	 * @throws SQLException
	 */
	private void processSensorTypeFile(List<Map<String, String>> dataMapList)
			throws SQLException {

		MultiMap dataMultiMap = preCheckSensorTypeContent(dataMapList);

		insertSensorTypeBatch((List<Map<String, String>>) dataMultiMap
				.get("sensorType"));
	}

	private void processNodeInfoFile(List<Map<String, String>> dataMapList)
			throws SQLException {

		MultiMap dataMultiMap = preCheckNodeInfoContent(dataMapList);

		insertNodeInfoBatch((List<Map<String, String>>) dataMultiMap
				.get("nodeInfo"));
	}

	/**
	 * 处理文件查看文件有哪些BatContentBean,根据这些Bean对象的内容类型，进行处理
	 * 
	 * @param processList
	 *            BatContentBean List is used.
	 * @param batFileProcessor
	 *            batFileProcessor is to process the bat file.
	 * @throws SQLException
	 */
	private void processFile(List<BatContentBean> processList,
			BatFileProcessor batFileProcessor) throws SQLException {

		connection = getConnection();
		stmt = connection.createStatement();

		for (BatContentBean batContentBean : batFileProcessor
				.getBatContentListBean()) {

			List<Map<String, String>> dataMapList = preprocess(batContentBean);
			String type = batContentBean.getContentType();

			if (type.equalsIgnoreCase("nodeType")) {

				processNodeTypeFile(dataMapList);
			} else if (type.equalsIgnoreCase("sensorType")) {

				processSensorTypeFile(dataMapList);
			} else if (type.equalsIgnoreCase("nodeInfo")) {

				processNodeInfoFile(dataMapList);
			} else {

				ErrorBean errorBean = new ErrorBean(
						batContentBean.getContentStringByIndex(0),
						batContentBean.getLineNumber(0));
				String cause = "The header should only be "
						+ "nodeType, sensorType, nodeInfo!";
				errorBean.setCause(cause);
				BatFileProcessor.addErrorList(errorBean);
			}
		}
		stmt.executeBatch();
		this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);
	}

	/**
	 * 预处理NodeInfo内容部分
	 * 
	 * @param dataMapList
	 *            All the data analysed in this data Map classified by nodeType,
	 *            nodeInfo, sensorType.
	 * @return
	 */
	private MultiMap preCheckNodeInfoContent(
			List<Map<String, String>> dataMapList) {

		MultiMap dataClassifiedMap = new MultiHashMap();
		List<String> errorList = new ArrayList<String>();

		for (Map<String, String> tempMap : dataMapList) {

			String moteid_ID = tempMap.get("Moteid_ID");
			String remarks = tempMap.get("Remarks");
			String location_X = tempMap.get("Location_X");
			String location_Y = tempMap.get("Location_Y");
			String latitude = tempMap.get("Latitude");
			String longitude = tempMap.get("Longitude");
			String nodeType = tempMap.get("nodeType");

			/**
			 * Construct the ErrorBean Object.
			 */
			int lineNum = Integer.valueOf(tempMap.get("lineNum"));
			String content = tempMap.get("content");

			if (nodeDBBean.isContainMoteidList(moteid_ID)) {

				String cause = "Moteid " + moteid_ID + " has existed!";
				ErrorBean errorBean = new ErrorBean(lineNum, content, cause);
				BatFileProcessor.addErrorList(errorBean);
				break;
			} else if (!nodeDBBean.isContainNodeTypeList(nodeType)) {

				String cause = "NodeType " + nodeType + " does not exist!";
				ErrorBean errorBean = new ErrorBean(lineNum, content, cause);
				BatFileProcessor.addErrorList(errorBean);
				break;
			} else {

				nodeDBBean.addNodeInfo(new NodeInformationBean(nodeType,
						moteid_ID, location_X, location_Y, latitude, longitude,
						remarks));
				dataClassifiedMap.put("nodeInfo", tempMap);
			}
		}

		return dataClassifiedMap;
	}

	/**
	 * 预处理SensorType内容部分
	 * 
	 * @param dataMapList
	 *            All the data analysed in this data Map classified by nodeType,
	 *            nodeInfo, sensorType.
	 * @return 已经分好类的dataMapList.
	 */
	private MultiMap preCheckSensorTypeContent(
			List<Map<String, String>> dataMapList) {

		MultiMap dataClassifiedMap = new MultiHashMap();

		for (Map<String, String> tempMap : dataMapList) {

			String sensorName = tempMap.get("sensorName");
			String unit = tempMap.get("unit");
			String rangeLow = tempMap.get("range_low");
			String rangeHigh = tempMap.get("range_high");

			/**
			 * Construct the ErrorBean Object.
			 */
			int lineNum = Integer.valueOf(tempMap.get("lineNum"));
			String content = tempMap.get("content");
			if (nodeDBBean.isContainSensorList(sensorName)) {

				String cause = "Sensor " + sensorName + " has existed!";
				ErrorBean errorBean = new ErrorBean(lineNum, content, cause);
				BatFileProcessor.addErrorList(errorBean);
				break;
			} else {

				nodeDBBean.addSensorType(new SensorTypeBean(sensorName, unit,
						rangeLow, rangeHigh));
				dataClassifiedMap.put("sensorType", tempMap);
			}
		}

		return dataClassifiedMap;
	}

	/**
	 * 预处理NodeType内容部分
	 * 
	 * @param dataMapList
	 *            All the data analysed in this data Map classified by nodeType,
	 *            nodeInfo, sensorType.
	 * @return 已经分好类的dataMapList.
	 */
	private MultiMap preCheckNodeTypeContent(
			List<Map<String, String>> dataMapList) {

		StringTokenizer tokenizer = null;
		MultiMap dataClassifiedMap = new MultiHashMap();

		for (Map<String, String> tempMap : dataMapList) {

			String nodeType = tempMap.get("nodeType");
			String remarks = tempMap.get("remarks");
			List<String> values = new ArrayList<String>();
			tokenizer = new StringTokenizer(tempMap.get("sensors"), ",");
			while (tokenizer.hasMoreElements()) {

				values.add(tokenizer.nextToken());
			}
			/**
			 * Construct the ErrorBean Object.
			 */
			int lineNum = Integer.valueOf(tempMap.get("lineNum"));
			String content = tempMap.get("content");
			if (!nodeDBBean.isContainSensorList(values)) {

				String cause = "Unrecognized sensors involved!";

				ErrorBean errorBean = new ErrorBean(lineNum, content, cause);
				BatFileProcessor.addErrorList(errorBean);
				break;
			} else if (nodeDBBean.isContainNodeTypeList(nodeType)) {

				String cause = "NodeType = " + nodeType + " has existed!";

				ErrorBean errorBean = new ErrorBean(lineNum, content, cause);
				BatFileProcessor.addErrorList(errorBean);
				break;
			} else {

				nodeDBBean.addNodeType(new NodeTypeBean(nodeType, remarks),
						values);
				dataClassifiedMap.put("nodeType", tempMap);
			}
		}

		return dataClassifiedMap;
	}

	/**
	 * 对具体的BatContent进行预处理,分析该BatContent的内容格式， 将里面的数据根据预先定义的格式分析出来， 将里面的数据分成两类
	 * lineNum对应行号，content对应该行号的内容，交叉存入List之中
	 * 
	 * @param contentBean
	 * @return 返回该BatContent的行号+内容；
	 */
	private List preprocess(BatContentBean contentBean) {

		List<String> fieldList = new ArrayList<String>();
		List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();

		List<String> contentString = contentBean.getContentString();
		StringTokenizer tokenizer = new StringTokenizer(contentString.get(1),
				",");
		while (tokenizer.hasMoreTokens()) {

			fieldList.add((String) tokenizer.nextElement());
		}

		for (int i = 2; i < contentString.size(); ++i) {

			tokenizer = new StringTokenizer(contentString.get(i), "%%");
			int lineNum = contentBean.getLineNumber(i);
			if (tokenizer.countTokens() != fieldList.size()) {

				ErrorBean errorBean = new ErrorBean(
						contentBean.getContentStringByIndex(i), lineNum);
				String cause = "The presentation is error, "
						+ "with the following not executed!";
				errorBean.setCause(cause);
				BatFileProcessor.addErrorList(errorBean);
				break;
			}

			while (tokenizer.hasMoreElements()) {

				Map<String, String> tempMap = new HashMap<String, String>();
				for (String field : fieldList) {

					tempMap.put(field, (String) tokenizer.nextElement());
				}

				tempMap.put("lineNum", ("" + lineNum));
				tempMap.put("content", contentString.get(i));
				dataMapList.add(tempMap);
			}
		}

		return dataMapList;
	}

	/**
	 * 移除NodeInfo
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void removeNodeInfo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Vector<String> values = new Vector<String>();
		String selectedValues = BaseUtil.StringSafe(req
				.getParameter("selectedValues"));
		StringTokenizer tokenizer = new StringTokenizer(selectedValues, ",");

		while (tokenizer.hasMoreElements()) {

			values.add((String) tokenizer.nextElement());
		}

		try {
			connection = getConnection();
			stmt = connection.createStatement();

			for (String moteid_ID : values) {

				deleteNodeInfoBatch(moteid_ID);
				nodeDBBean.deleteNodeInfo(moteid_ID);
				newSensing.deleteNewestSensings(moteid_ID);
			}
			stmt.executeBatch();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {

			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}

		this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);

		JSP_FOLDER = "/";
		VIEW_JSP = "index.jsp";

		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void removeSensorType(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		boolean flag = true;
		Vector<String> values = new Vector<String>();
		String selectedValues = BaseUtil.StringSafe(req
				.getParameter("selectedValues"));
		StringTokenizer tokenizer = new StringTokenizer(selectedValues, ",");

		while (tokenizer.hasMoreElements()) {

			values.add((String) tokenizer.nextElement());
		}

		try {
			connection = getConnection();
			stmt = connection.createStatement();

			for (String sensorName : values) {

				deleteSensorTypeBatch(sensorName);
				nodeDBBean.deleteSensorType(sensorName);
			}
			stmt.executeBatch();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {

			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}

		JSP_FOLDER = "/";
		VIEW_JSP = "index.jsp";
		this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);

		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void removeNodeType(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Vector<String> values = new Vector<String>();
		String selectedValues = BaseUtil.StringSafe(req
				.getParameter("selectedValues"));
		StringTokenizer tokenizer = new StringTokenizer(selectedValues, ",");

		while (tokenizer.hasMoreElements()) {

			values.add((String) tokenizer.nextElement());
		}

		try {
			connection = getConnection();
			stmt = connection.createStatement();

			for (String nodeType : values) {

				deleteNodeTypeBatch(nodeType);
				nodeDBBean.deleteNodeType(nodeType);
			}
			stmt.executeBatch();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {
			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}

		this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);

		VIEW_JSP = "index.jsp";
		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	/*
	 * This has one bug : If this database has contained the nodetype you wanna
	 * modify, what will you do this need one modify! to create one error page!
	 * ok, tonight I will modify this.
	 */
	private void modifyNodeType(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Vector<String> values = new Vector<String>();
		String nodeType = BaseUtil.StringSafe(req.getParameter("nodeType"));
		String selectedValues = BaseUtil.StringSafe(req
				.getParameter("selectedValues"));
		StringTokenizer tokenizer = new StringTokenizer(selectedValues, ",");
		String remarks = BaseUtil.StringSafe(req.getParameter("remarks"));

		while (tokenizer.hasMoreElements()) {

			values.add((String) tokenizer.nextElement());
		}

		try {
			connection = getConnection();
			stmt = connection.createStatement();

			deleteNodeTypeBatch(nodeType);
			insertNodeTypeBatch(nodeType, remarks, values);
			//
			stmt.executeBatch();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {
			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}

		nodeDBBean.deleteNodeType(nodeType);
		nodeDBBean.addNodeType(new NodeTypeBean(nodeType, remarks), values);

		this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);
		VIEW_JSP = "index.jsp";

		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void modifyNodeInfoDir(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String moteid_ID = BaseUtil.StringSafe(req.getParameter("moteid_ID"));
		NodeInformationBean nodeInfoBean = nodeDBBean
				.findNodeInfoBean(moteid_ID);

		req.setAttribute("nodeInfoBean", nodeInfoBean);

		VIEW_JSP = "modifyFinalNodeInfo.jsp";
		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void modifyNodeTypeDir(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String nodeType = BaseUtil.StringSafe(req.getParameter("nodeType"));
		NodeTypeBean nodeTypeBean = nodeDBBean.findNodeTypeBean(nodeType);

		req.setAttribute("nodeTypeBean", nodeTypeBean);
		VIEW_JSP = "modifyFinalNodeType.jsp";
		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	/*
	 * Notice: Moteid_ID can't be modified.
	 */
	private void modifyNodeInfo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String moteid_ID = BaseUtil.StringSafe(req.getParameter("moteid_ID"));
		String remarks = BaseUtil.StringSafe(req.getParameter("remarks"));
		String location_X = BaseUtil.StringSafe(req.getParameter("location_X"));
		String location_Y = BaseUtil.StringSafe(req.getParameter("location_Y"));
		String latitude = BaseUtil.StringSafe(req.getParameter("latitude"));
		String longitude = BaseUtil.StringSafe(req.getParameter("longitude"));

		String nodeType = BaseUtil.StringSafe(req.getParameter("nodeType"));

		try {
			connection = getConnection();
			stmt = connection.createStatement();

			updateNodeInfoBatch(moteid_ID, remarks, location_X, location_Y,
					latitude, longitude, nodeType);
			stmt.executeBatch();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {
			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}

		nodeDBBean.updateNodeInfo(new NodeInformationBean(nodeType, moteid_ID,
				location_X, location_Y, latitude, longitude, remarks));
		this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);

		VIEW_JSP = "modifyDirector.jsp";
		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void modifySensorType(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String sensorIndex = BaseUtil.StringSafe(req
				.getParameter("sensorIndex"));
		String sensorName = BaseUtil.StringSafe(req.getParameter("sensorName"));
		String unit = BaseUtil.StringSafe(req.getParameter("unit"));
		String rangeLow = BaseUtil.StringSafe(req.getParameter("range_low"));
		String rangeHigh = BaseUtil.StringSafe(req.getParameter("range_high"));

		try {
			connection = getConnection();
			stmt = connection.createStatement();

			updateSensorTypeBatch(sensorIndex, sensorName, unit, rangeLow,
					rangeHigh);
			stmt.executeBatch();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {
			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}

		nodeDBBean.updateSensorType(sensorIndex, new SensorTypeBean(sensorName,
				unit, rangeLow, rangeHigh));

		this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);

		VIEW_JSP = "modifyDirector.jsp";
		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void modifySensorTypeDir(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String sensorName = BaseUtil.StringSafe(req.getParameter("sensorName"));
		SensorTypeBean sensorTypeBean = nodeDBBean
				.findSensorTypeBean(sensorName);

		req.setAttribute("sensorTypeBean", sensorTypeBean);
		VIEW_JSP = "modifyFinalSensorType.jsp";
		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void createFinalSensorType(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String sensorName = BaseUtil.StringSafe(req.getParameter("sensorName"));
		String unit = BaseUtil.StringSafe(req.getParameter("unit"));
		String rangeLow = BaseUtil.StringSafe(req.getParameter("range_low"));
		String rangeHigh = BaseUtil.StringSafe(req.getParameter("range_high"));
		String createSession = BaseUtil.StringSafe(req
				.getParameter("createSession"));
		String plugonCommand = BaseUtil.StringSafe(req
				.getParameter("plugonCommand"));

		if (!"no".equals(plugonCommand)) {

			try {
				connection = getConnection();
				stmt = connection.createStatement();

				String sql = "insert into SensorType values(null, '"
						+ sensorName + "', '" + unit + "', "
						+ BaseUtil.translate(rangeLow) + ", "
						+ BaseUtil.translate(rangeHigh) + ") ";
				stmt.executeUpdate(sql);

				nodeDBBean.addSensorType(new SensorTypeBean(sensorName, unit,
						rangeLow, rangeHigh));
				this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);
			} catch (SQLException e) {

				throw new RuntimeException(e);
			} finally {

				MySqlConnectionHelper.closeStatement(stmt);
				MySqlConnectionHelper.close(connection);
			}
		}

		if ("1".equals(createSession)) {

			String nodeType = BaseUtil.StringSafe(req.getParameter("nodeType"));
			String remarks = BaseUtil.StringSafe(req.getParameter("remarks"));
			req.setAttribute("nodeTypeBean",
					new NodeTypeBean(nodeType, remarks));
			VIEW_JSP = "createNodeType.jsp";
		} else {

			VIEW_JSP = "index.jsp";
		}

		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void createFinalNodeInfo(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {

		String moteid_ID = BaseUtil.StringSafe(req.getParameter("moteid_ID"));
		String remarks = BaseUtil.StringSafe(req.getParameter("remarks"));
		String location_X = BaseUtil.StringSafe(req.getParameter("location_X"));
		String location_Y = BaseUtil.StringSafe(req.getParameter("location_Y"));
		String latitude = BaseUtil.StringSafe(req.getParameter("latitude"));
		String longitude = BaseUtil.StringSafe(req.getParameter("longitude"));

		String nodeType = BaseUtil.StringSafe(req.getParameter("nodeType"));

		try {
			connection = getConnection();
			stmt = connection.createStatement();

			insertNodeInfoBatch(moteid_ID, remarks, location_X, location_Y,
					latitude, longitude, nodeType);
			stmt.executeBatch();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {

			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}

		NodeInformationBean tempBean = new NodeInformationBean(nodeType,
				moteid_ID, location_X, location_Y, latitude, longitude, remarks);

		nodeDBBean.addNodeInfo(tempBean); // /// this needs to be detected.
		this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);

		VIEW_JSP = "index.jsp";

		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void createFinalNodeType(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		Vector<String> values = new Vector<String>();
		String nodeType = BaseUtil.StringSafe(req.getParameter("nodeType"));
		String selectedValues = BaseUtil.StringSafe(req
				.getParameter("selectedValues"));
		StringTokenizer tokenizer = new StringTokenizer(selectedValues, ",");
		String remarks = BaseUtil.StringSafe(req.getParameter("remarks"));
		String plugonCommand = BaseUtil.StringSafe(req
				.getParameter("plugonCommand"));

		if (!"no".equals(plugonCommand)) {

			while (tokenizer.hasMoreElements()) {

				values.add((String) tokenizer.nextElement());
			}

			try {
				connection = getConnection();
				stmt = connection.createStatement();

				insertNodeTypeBatch(nodeType, remarks, values);
				stmt.executeBatch();

				nodeDBBean.addNodeType(new NodeTypeBean(nodeType, remarks),
						values);
				this.getServletContext().setAttribute("nodeDBBean", nodeDBBean);
			} catch (SQLException e) {

				throw new RuntimeException(e);
			} finally {

				MySqlConnectionHelper.closeStatement(stmt);
				MySqlConnectionHelper.close(connection);
			}
		}

		if ("2".equals(req.getParameter("createSession"))) {

			String moteid_ID = BaseUtil.StringSafe(req
					.getParameter("a_moteid_ID"));
			remarks = BaseUtil.StringSafe(req.getParameter("a_remarks"));
			String location_X = BaseUtil.StringSafe(req
					.getParameter("a_location_X"));
			String location_Y = BaseUtil.StringSafe(req
					.getParameter("a_location_Y"));
			String latitude = BaseUtil.StringSafe(req
					.getParameter("a_latitude"));
			String longitude = BaseUtil.StringSafe(req
					.getParameter("a_longitude"));
			nodeType = BaseUtil.StringSafe(req.getParameter("a_nodeType"));

			NodeInformationBean tempBean = new NodeInformationBean(nodeType,
					moteid_ID, location_X, location_Y, latitude, longitude,
					remarks);

			req.setAttribute("nodeInfoBean", tempBean);
			VIEW_JSP = "modifyFinalNodeInfo.jsp?createSession=2";
		} else {

			VIEW_JSP = "index.jsp";
		}
		gotoPage(JSP_FOLDER + VIEW_JSP, req, resp);
	}

	private void updateSensorTypeBatch(String sensorIndex, String sensorName,
			String unit, String rangeLow, String rangeHigh) throws SQLException {

		String sql = "update SensorType " + "set sensorName = '" + sensorName
				+ "', " + "unit = '" + unit + "', " + "range_low = " + rangeLow
				+ ", " + "range_high = " + rangeHigh + " where sensorName = '"
				+ sensorIndex + "'";
		stmt.addBatch(sql);
		sql = "update Node_sensorType " + "set sensorName = '" + sensorName
				+ "' " + "where sensorName = '" + sensorIndex + "'";
		stmt.addBatch(sql);
	}

	private void updateNodeInfoBatch(String moteid_ID, String remarks,
			String location_X, String location_Y, String latitude,
			String longitude, String nodeType) throws SQLException {

		deleteNodeInfoBatch(moteid_ID);
		insertNodeInfoBatch(moteid_ID, remarks, location_X, location_Y,
				latitude, longitude, nodeType);
	}

	// ////

	private void insertNodeTypeBatch(String nodeType, String remarks,
			Vector values) throws SQLException {

		String sql = "insert into NodeType values(null, " + nodeType + ", '"
				+ remarks + "')";
		stmt.addBatch(sql);
		Iterator<String> it = values.iterator();

		while (it.hasNext()) {

			sql = "insert into Node_sensorType values(null, " + nodeType
					+ ", '" + it.next() + "')";
			stmt.addBatch(sql);
		}
	}

	private void insertSensorTypeBatch(String sensorName, String unit,
			String rangeLow, String rangeHigh) throws SQLException {

		if (stmt == null) {

			stmt = getConnection().createStatement();
		}
		String sql = "insert into SensorType values(null, '" + sensorName
				+ "', '" + unit + "', " + BaseUtil.translate(rangeLow) + ", "
				+ BaseUtil.translate(rangeHigh) + ") ";
		stmt.addBatch(sql);
	}

	/**
	 * 
	 * @param dataMapList
	 *            When it comes to sensorType batch, I mean the .bat file
	 *            procedure.
	 * @throws SQLException
	 */
	private void insertSensorTypeBatch(List<Map<String, String>> dataMapList)
			throws SQLException {

		if (dataMapList == null) {

			return;
		}
		for (Map<String, String> tempMap : dataMapList) {

			String sensorName = tempMap.get("sensorName");
			String unit = BaseUtil.StringTrim(tempMap.get("unit"));
			String rangeLow = BaseUtil.StringTrim(tempMap.get("range_low"));
			String rangeHigh = BaseUtil.StringTrim(tempMap.get("range_high"));
			BatFileProcessor.addCorrectRecordNum();
			insertSensorTypeBatch(sensorName, unit, rangeLow, rangeHigh);
		}
	}

	/**
	 * 
	 * @param dataMapList
	 *            When it comes to nodeType batch, I mean the .bat file
	 *            procedure.
	 * @throws SQLException
	 */
	private void insertNodeTypeBatch(List<Map<String, String>> dataMapList)
			throws SQLException {

		if (dataMapList == null) {

			return;
		}
		StringTokenizer tokenizer = null;

		for (Map<String, String> tempMap : dataMapList) {

			String nodeType = tempMap.get("nodeType");
			String remarks = tempMap.get("remarks");
			Vector<String> values = new Vector<String>();
			tokenizer = new StringTokenizer(tempMap.get("sensors"), ",");
			while (tokenizer.hasMoreElements()) {

				values.add(tokenizer.nextToken());
			}

			BatFileProcessor.addCorrectRecordNum();
			insertNodeTypeBatch(nodeType, remarks, values);
		}
	}

	/**
	 * 
	 * @param dataMapList
	 *            When it comes to node Information batch, I mean the .bat file
	 *            procedure.
	 * @throws SQLException
	 */
	private void insertNodeInfoBatch(List<Map<String, String>> dataMapList)
			throws SQLException {

		if (dataMapList == null) {

			return;
		}

		for (Map<String, String> tempMap : dataMapList) {
			String moteid_ID = tempMap.get("Moteid_ID");
			String remarks = BaseUtil.StringTrim(tempMap.get("Remarks"));
			String location_X = BaseUtil.StringTrim(tempMap.get("Location_X"));
			String location_Y = BaseUtil.StringTrim(tempMap.get("Location_Y"));
			String latitude = BaseUtil.StringTrim(tempMap.get("Latitude"));
			String longitude = BaseUtil.StringTrim(tempMap.get("Longitude"));
			String nodeType = tempMap.get("nodeType");

			BatFileProcessor.addCorrectRecordNum();
			insertNodeInfoBatch(moteid_ID, remarks, location_X, location_Y,
					latitude, longitude, nodeType);
		}
	}

	/*
	 * Notice: You can insert null but not "";
	 */
	private void insertNodeInfoBatch(String moteid_ID, String remarks,
			String location_X, String location_Y, String latitude,
			String longitude, String nodeType) throws SQLException {
		String sql = "";

		sql = "insert into NodeInformation values(null, 0, " + moteid_ID + ", "
				+ nodeType + ", " + BaseUtil.translate(location_X) + ", "
				+ BaseUtil.translate(location_Y) + ", "
				+ BaseUtil.translate(latitude) + ", "
				+ BaseUtil.translate(longitude) + ", '" + remarks + "')";
		stmt.addBatch(sql);
	}

	private void deleteSensorTypeBatch(String sensorName) throws SQLException {

		if (BaseUtil.StringSafe(sensorName).equals("")) {

			return;
		}

		String sql = "delete from SensorType where sensorName = '" + sensorName
				+ "'";
		stmt.addBatch(sql);

		sql = "delete from Node_sensorType where sensorName = '" + sensorName
				+ "'";
		stmt.addBatch(sql);
	}

	private void deleteNodeTypeBatch(String nodeType) throws SQLException {

		if (BaseUtil.StringSafe(nodeType).equals("")) {

			return;
		}

		String sql = "delete from NodeType where nodeType = " + nodeType;
		stmt.addBatch(sql);

		sql = "delete from Node_sensorType where nodeType = " + nodeType;
		stmt.addBatch(sql);

		sql = "delete from NodeInformation where nodeType = " + nodeType;
		stmt.addBatch(sql);
	}

	private void deleteNodeInfoBatch(String moteid_ID) throws SQLException {

		if (BaseUtil.StringSafe(moteid_ID).equals("")) {

			return;
		}

		String sql = "delete from NodeInformation where Moteid_ID = "
				+ moteid_ID;
		stmt.addBatch(sql);
		 sql ="delete from newestSensingData where Moteid_ID = "
				+ moteid_ID;
		stmt.addBatch(sql);
	}

	private void redirectPage(String targetURL, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			response.sendRedirect(targetURL);
		} catch (IOException e) {

			throw new RuntimeException(e);
		}
	}

	private void gotoPage(String targetURL, HttpServletRequest request,
			HttpServletResponse response) {
		RequestDispatcher rd;
		rd = request.getRequestDispatcher(targetURL);
		try {
			rd.forward(request, response);
		} catch (ServletException e) {

			throw new RuntimeException(e);
		} catch (IOException e) {

			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() throws SQLException {
		// return dataSource.getConnection();
		return MySqlConnectionHelper.getConnection();
	}

	private void initParam(List<NodeTypeBean> nodeTypeListBean,
			List<SensorTypeBean> sensorTypeListBean,
			List<NodeInformationBean> nodeInfoListBean) {

		try {
			connection = getConnection();
			stmt = connection.createStatement();

			initNodeTypeListParam(nodeTypeListBean, stmt, connection);
			initSensorTypeListParam(sensorTypeListBean, stmt, connection);
			initNodeInfoListParam(nodeInfoListBean, stmt, connection);
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} finally {

			MySqlConnectionHelper.closeStatement(stmt);
			MySqlConnectionHelper.close(connection);
		}
	}

	private void initParam(NodeDBBean nodeDBBean) {
		List<NodeTypeBean> nodeTypeListBean = new ArrayList<NodeTypeBean>();
		List<SensorTypeBean> sensorTypeListBean = new ArrayList<SensorTypeBean>();
		List<NodeInformationBean> nodeInfoListBean = new ArrayList<NodeInformationBean>();

		initParam(nodeTypeListBean, sensorTypeListBean, nodeInfoListBean);
	}

	private void initNodeTypeListParam(List<NodeTypeBean> nodeTypeListBean,
			Statement stmt, Connection conn) throws SQLException {
		if (nodeTypeListBean == null) {

			nodeTypeListBean = new ArrayList<NodeTypeBean>();
		}

		String sql = "select * from NodeType";
		ResultSet rs = stmt.executeQuery(sql);

		NodeTypeBean nodeTypeBean = null;

		while (rs.next()) {

			String id = BaseUtil.StringSafe(rs.getString("id"));
			String nodeType = BaseUtil.StringSafe(rs.getString("nodeType"));
			String remark = BaseUtil.StringSafe(rs.getString("remark"));

			nodeTypeBean = new NodeTypeBean();
			nodeTypeBean.setId(id);
			nodeTypeBean.setNodeType(nodeType);
			nodeTypeBean.setRemark(remark);
			nodeTypeListBean.add(nodeTypeBean);
		}
		for (NodeTypeBean tempBean : nodeTypeListBean) {

			sql = "select ST.id, ST.sensorName, ST.unit, ST.range_low, ST.range_high "
					+ "from Node_sensorType NS, SensorType ST "
					+ "where NS.nodeType = "
					+ tempBean.getNodeType()
					+ " and ST.sensorName = NS.sensorName";
			rs = stmt.executeQuery(sql);
			List<SensorTypeBean> sensorTypeListTempBean = new ArrayList<SensorTypeBean>();
			while (rs.next()) {

				String id = BaseUtil.StringSafe(rs.getString("id"));
				String sensorName = BaseUtil.StringSafe(rs
						.getString("sensorName"));
				String unit = BaseUtil.StringSafe(rs.getString("unit"));
				String range_low = BaseUtil.StringSafe(rs
						.getString("range_low"));
				String range_high = BaseUtil.StringSafe(rs
						.getString("range_high"));
				sensorTypeListTempBean.add(new SensorTypeBean(id, sensorName,
						unit, range_low, range_high));
			}
			tempBean.setSensorTypeListBean(sensorTypeListTempBean);
		}
		nodeDBBean.setNodeTypeList(nodeTypeListBean);
	}

	private void initSensorTypeListParam(
			List<SensorTypeBean> sensorTypeListBean, Statement stmt,
			Connection conn) throws SQLException {

		String sql = "select * from SensorType ST";
		ResultSet rs = stmt.executeQuery(sql);

		if (sensorTypeListBean == null) {

			sensorTypeListBean = new ArrayList<SensorTypeBean>();
		}
		while (rs.next()) {
			String id = BaseUtil.StringSafe(rs.getString("id"));
			String sensorName = BaseUtil.StringSafe(rs.getString("sensorName"));
			String unit = BaseUtil.StringSafe(rs.getString("unit"));
			String range_low = BaseUtil.StringSafe(rs.getString("range_low"));
			String range_high = BaseUtil.StringSafe(rs.getString("range_high"));
			sensorTypeListBean.add(new SensorTypeBean(id, sensorName, unit,
					range_low, range_high));
		}
		nodeDBBean.setSensorTypeList(sensorTypeListBean);
	}

	private void initNodeInfoListParam(
			List<NodeInformationBean> nodeInfoListBean, Statement stmt,
			Connection conn) throws SQLException {

		String sql = "select Moteid_ID from NodeInformation";
		ResultSet rs = stmt.executeQuery(sql);
		List<String> moteidList = new ArrayList<String>();

		while (rs.next()) {

			String moteid_ID = BaseUtil.StringSafe(rs.getString("moteid_ID"));
			moteidList.add(moteid_ID);
		}
		if (nodeInfoListBean == null) {

			nodeInfoListBean = new ArrayList<NodeInformationBean>();
		}

		for (String moteid : moteidList) {

			String moteid_ID = "", remarks = "", location_X = "", location_Y = "", latitude = "", longitude = "";
			String nodeType = "";

			sql = "select * from NodeInformation where Moteid_ID = " + moteid;
			rs = stmt.executeQuery(sql);

			if (rs.next()) {

				moteid_ID = BaseUtil.StringSafe(rs.getString("moteid_ID"));
				location_X = BaseUtil.StringSafe(rs.getString("location_X"));
				location_Y = BaseUtil.StringSafe(rs.getString("location_Y"));
				latitude = BaseUtil.StringSafe(rs.getString("Latitude"));
				longitude = BaseUtil.StringSafe(rs.getString("longitude"));
				remarks = BaseUtil.StringSafe(rs.getString("remarks"));
				nodeType = BaseUtil.StringSafe(rs.getString("nodeType"));

				NodeInformationBean nodeInfoBean = new NodeInformationBean(
						nodeType, moteid_ID, location_X, location_Y, latitude,
						longitude, remarks);
				nodeInfoListBean.add(nodeInfoBean);
			}
		}

		nodeDBBean.setNodeInfoList(nodeInfoListBean);
		/*
		 * for (String moteid : moteidList) {
		 * 
		 * sql = "select * from NodeInformation where Moteid_ID = " + moteid;
		 * String moteid_ID = "", remarks = "", location_X = "", location_Y =
		 * "", latitude = "", longitude = ""; rs = stmt.executeQuery(sql);
		 * List<String> list = new ArrayList<String>(); while (rs.next()) {
		 * 
		 * String nodeType = BaseUtil.StringSafe(rs.getString("nodeType"));
		 * list.add(nodeType); }
		 * 
		 * sql = "select * from NodeInformation where Moteid_ID = " + moteid; rs
		 * = stmt.executeQuery(sql); if (rs.next()) {
		 * 
		 * moteid_ID = BaseUtil.StringSafe(rs.getString("moteid_ID"));
		 * location_X = BaseUtil.StringSafe(rs.getString("location_X"));
		 * location_Y = BaseUtil.StringSafe(rs.getString("location_Y"));
		 * latitude = BaseUtil.StringSafe(rs.getString("Latitude")); longitude =
		 * BaseUtil.StringSafe(rs.getString("longitude")); remarks =
		 * BaseUtil.StringSafe(rs.getString("remarks"));
		 * 
		 * NodeInformationBean nodeInfoBean = new NodeInformationBean(moteid_ID,
		 * location_X, location_Y, latitude, longitude, remarks); for (String
		 * nodeType : list) {
		 * 
		 * nodeInfoBean.addNodeType(nodeDBBean.findNodeTypeBean(nodeType)); }
		 * nodeInfoListBean.add(nodeInfoBean); } }
		 */
	}
}
