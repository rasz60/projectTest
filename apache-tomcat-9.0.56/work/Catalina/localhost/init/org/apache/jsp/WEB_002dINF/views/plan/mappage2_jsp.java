/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.56
 * Generated at: 2022-04-04 02:31:20 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.plan;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class mappage2_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(10);
    _jspx_dependants.put("jar:file:/D:/project_init/apache-tomcat-9.0.56/wtpwebapps/project_init/WEB-INF/lib/jstl-1.2.jar!/META-INF/sql.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/lib/spring-security-taglibs-4.2.13.RELEASE.jar", Long.valueOf(1646873798857L));
    _jspx_dependants.put("jar:file:/D:/project_init/apache-tomcat-9.0.56/wtpwebapps/project_init/WEB-INF/lib/jstl-1.2.jar!/META-INF/x.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("jar:file:/D:/project_init/apache-tomcat-9.0.56/wtpwebapps/project_init/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/views/plan/../header.jsp", Long.valueOf(1648618030978L));
    _jspx_dependants.put("jar:file:/D:/project_init/apache-tomcat-9.0.56/wtpwebapps/project_init/WEB-INF/lib/spring-security-taglibs-4.2.13.RELEASE.jar!/META-INF/security.tld", Long.valueOf(1560936316000L));
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1644909417825L));
    _jspx_dependants.put("jar:file:/D:/project_init/apache-tomcat-9.0.56/wtpwebapps/project_init/WEB-INF/lib/jstl-1.2.jar!/META-INF/fn.tld", Long.valueOf(1153352682000L));
    _jspx_dependants.put("/WEB-INF/views/plan/../footer.jsp", Long.valueOf(1647391121162L));
    _jspx_dependants.put("jar:file:/D:/project_init/apache-tomcat-9.0.56/wtpwebapps/project_init/WEB-INF/lib/jstl-1.2.jar!/META-INF/fmt.tld", Long.valueOf(1153352682000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, false, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("    \r\n");
      out.write("  \r\n");
      out.write(" \r\n");
      out.write("   \r\n");
      out.write("  \r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<meta id=\"_csrf\" name=\"_csrf\" content=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_csrf.token}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css\">\r\n");
      out.write("<script src=\"https://kit.fontawesome.com/b4e02812b5.js\" crossorigin=\"anonymous\"></script>\r\n");
      out.write("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>\r\n");
      out.write("<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js\"></script>\r\n");
      out.write("<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd&libraries=services\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/plan/plan_detail.js\"></script>\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/plan/kakaomap/kakaomap.css\" />\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/plan/plan_detail.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/header.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/footer.css\" />\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("  \r\n");
      out.write(" \r\n");
      out.write("   \r\n");
      out.write("  \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<nav class=\"navbar navbar-default fixed-top border-bottom pt-3 bg-white\">\r\n");
      out.write("	<div class=\"container\">\r\n");
      out.write("		<div class=\"navbar-header\">\r\n");
      out.write("			<a href=\"/init\" class=\"navbar-brand\">\r\n");
      out.write("				<i class=\"menu-icon fa-solid fa-house\"></i>\r\n");
      out.write("			</a>\r\n");
      out.write("		</div>\r\n");
      out.write("		\r\n");
      out.write("		<div>\r\n");
      out.write("			<div class=\"input-group border rounded bg-light\">\r\n");
      out.write("		    	<div class=\"input-group-btn\">\r\n");
      out.write("		    		<button type=\"button\" class=\"btn btn-default mr-1\">filter</button>\r\n");
      out.write("		        	<button type=\"button\" class=\"btn btn-default dropdown-toggle mr-1\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"caret\"></span></button>\r\n");
      out.write("		        	<ul class=\"dropdown-menu\">\r\n");
      out.write("		          		<li>filter 1</li>\r\n");
      out.write("		          		<li>filter 2</li>\r\n");
      out.write("		          		<li>filter 3</li>\r\n");
      out.write("		        	</ul>\r\n");
      out.write("		      	</div>\r\n");
      out.write("		      	<input type=\"text\" class=\"form-control bg-light mr-1\" size=\"30\" aria-label=\"000\" placeholder=\"search...\">\r\n");
      out.write("	    		<a href=\"search\" class=\"btn btn-default mr-1\"><i class=\"fa-brands fa-sistrix\"></i></a>	      	\r\n");
      out.write("		    </div>\r\n");
      out.write("		</div>\r\n");
      out.write("		\r\n");
      out.write("		<ul class=\"nav navbar\">\r\n");
      out.write("			<li class=\"mr-4 feed\">\r\n");
      out.write("				<a href=\"/init/feed\">\r\n");
      out.write("					<i class=\"menu-icon fa-regular fa-circle-user\"></i>\r\n");
      out.write("				</a>\r\n");
      out.write("			</li>\r\n");
      out.write("			\r\n");
      out.write("			<li class=\"mr-4 notice\">\r\n");
      out.write("				<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\r\n");
      out.write("					<i class=\"menu-icon fa-regular fa-bell\"></i>\r\n");
      out.write("				</a>\r\n");
      out.write("	        	<ul class=\"dropdown-menu\">\r\n");
      out.write("	        		");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("	        	</ul>\r\n");
      out.write("				\r\n");
      out.write("			</li>\r\n");
      out.write("			\r\n");
      out.write("			<li class=\"mr-4 msg\">\r\n");
      out.write("				<a href=\"#\">\r\n");
      out.write("					<i class=\"menu-icon fa-regular fa-comment-dots\"></i>\r\n");
      out.write("				</a>\r\n");
      out.write("			</li>\r\n");
      out.write("		</ul>\r\n");
      out.write("		\r\n");
      out.write("	</div>		\r\n");
      out.write("\r\n");
      out.write("</nav>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<section class=\"container-fluid\">\r\n");
      out.write("	<div class=\"planlist row mx-0\">\r\n");
      out.write("		");
      out.write("\r\n");
      out.write("		<div id=\"kakaobox\" class=\"map_wrap col-7\">\r\n");
      out.write("			");
      out.write("\r\n");
      out.write("			<div id=\"map\" style=\"width:100%;height:100%;position:relative;overflow:hidden;\"></div>\r\n");
      out.write("			\r\n");
      out.write("			");
      out.write("\r\n");
      out.write("			<div id=\"menu_wrap\" class=\"bg_white\">\r\n");
      out.write("		    	<div class=\"option\">\r\n");
      out.write("		        	<div>\r\n");
      out.write("		            	<form onsubmit=\"searchPlaces(); return false;\">\r\n");
      out.write("		                	키워드 : <input type=\"text\" value=\"이태원\" id=\"keyword\" size=\"15\"> \r\n");
      out.write("		                	<button type=\"submit\">검색하기</button>\r\n");
      out.write("		            	</form>\r\n");
      out.write("		        	</div>\r\n");
      out.write("		    	</div>\r\n");
      out.write("		    	<hr/>\r\n");
      out.write("		    	<ul id=\"placesList\"></ul>\r\n");
      out.write("		    	<div id=\"pagination\"></div>\r\n");
      out.write("			</div>\r\n");
      out.write("		</div>\r\n");
      out.write("		\r\n");
      out.write("		");
      out.write("\r\n");
      out.write("		<div class=\"planbox col-5 d-block\" id=\"tabdiv\">\r\n");
      out.write("			<div class=\"tab-nav row mx-0 mt-2 justify-content-end\" data-count=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.dateCount}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      out.write("				");
      out.write("\r\n");
      out.write("				<button type=\"button\" id=\"submitAll\" class=\"btn btn-sm btn-primary col-1  mr-2\">\r\n");
      out.write("					<i class=\"fa-regular fa-floppy-disk\"></i>\r\n");
      out.write("				</button>\r\n");
      out.write("\r\n");
      out.write("				<button type=\"button\" class=\"btn btn-outline-default text-dark col-1 border\" id=\"prev-btn\" data-index=\"0\">\r\n");
      out.write("					<i class=\"fa-solid fa-angle-left\"></i>\r\n");
      out.write("				</button>\r\n");
      out.write("				<button type=\"button\" class=\"btn btn-outline-default text-dark col-1 border\" id=\"next-btn\" data-index=\"2\">\r\n");
      out.write("					<i class=\"fa-solid fa-angle-right\"></i>\r\n");
      out.write("				</button>\r\n");
      out.write("			</div>\r\n");
      out.write("			\r\n");
      out.write("			");
      out.write("\r\n");
      out.write("			<form id=\"frm0\" name=\"frm0\" action=\"#\" method=\"post\">\r\n");
      out.write("				<input type=\"hidden\" name=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_csrf.parameterName }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${_csrf.token }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly/>\r\n");
      out.write("				");
      out.write("\r\n");
      out.write("				<input type=\"hidden\" class=\"form-control\" name=\"planNum\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.planNum}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" value=\"0\" readonly/>\r\n");
      out.write("				");
      out.write("\r\n");
      out.write("				<input type=\"hidden\" class=\"form-control\" name=\"userId\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${id }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" value=\"0\" readonly/>\r\n");
      out.write("				");
      out.write("\r\n");
      out.write("				<input type=\"hidden\" class=\"form-control\" name=\"planName\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.planName}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly/>\r\n");
      out.write("				");
      out.write("\r\n");
      out.write("				<input type=\"hidden\" class=\"form-control\" name=\"startDate\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.startDate}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly/>\r\n");
      out.write("				");
      out.write("\r\n");
      out.write("				<input type=\"hidden\" class=\"form-control\" name=\"endDate\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.endDate}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly/>\r\n");
      out.write("				");
      out.write("\r\n");
      out.write("				<input type=\"hidden\" class=\"form-control\" name=\"dateCount\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.dateCount}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly/>\r\n");
      out.write("				");
      out.write("\r\n");
      out.write("				<input type=\"hidden\" class=\"form-control\" name=\"eventColor\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.eventColor}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly/>\r\n");
      out.write("			</form>\r\n");
      out.write("			\r\n");
      out.write("			");
      out.write("\r\n");
      out.write("			<div id=\"tab-1\" class=\"tab-content active\">\r\n");
      out.write("				<h3 class=\"display-4 font-italic\" id=\"date-title\">DAY 1 : ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.startDate }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("</h3>\r\n");
      out.write("				<hr />\r\n");
      out.write("				<p class=\"mt-2\">일정 : <span class=\"showIndex\">0</span> / 10</p>\r\n");
      out.write("				<div class=\"inputDiv\">\r\n");
      out.write("					");
      out.write("\r\n");
      out.write("					<form id=\"frm1\" name=\"frm1\" action=\"#\" method=\"post\" data-count=\"0\" data-day=\"day1\" data-date=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.startDate}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">\r\n");
      out.write("						\r\n");
      out.write("						<div class=\"detail1 mt-2 py-2 border bg-light rounded\" data-index=\"1\">\r\n");
      out.write("							<h3 class=\"font-italic ml-2 d-inline mt-2\">Place</h3>\r\n");
      out.write("							");
      out.write("\r\n");
      out.write("							<input type=\"text\" class=\"form-control col-8 d-inline ml-3\" name=\"placeName\" readonly/>\r\n");
      out.write("							<button type=\"button\" class=\"btn btn-sm btn-danger deleteBtn float-right mr-2 mt-1\" data-index=\"1\"><i class=\"fa-solid fa-minus\"></i></button>\r\n");
      out.write("							<button type=\"button\" class=\"btn btn-sm btn-dark detailBtn float-right mr-2 mt-1\" data-count=\"0\"><i class=\"fa-solid fa-angles-down\"></i></button>\r\n");
      out.write("							<hr />\r\n");
      out.write("\r\n");
      out.write("							<div class=\"inputbox row mx-0 justify-content-between\">\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<input type=\"hidden\" class=\"form-control\" name=\"planDtNum\" value=\"0\" readonly/>\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<input type=\"hidden\" class=\"form-control\" name=\"planDay\" value=\"day1\" readonly/>\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<input type=\"hidden\" class=\"form-control\" name=\"placeCount\" readonly/>\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<input type=\"hidden\" class=\"form-control\" name=\"planDate\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${plan.startDate}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" readonly/>\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<input type=\"hidden\" class=\"form-control\" name=\"latitude\" readonly/>\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<input type=\"hidden\" class=\"form-control\" name=\"longitude\" readonly/>\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<input type=\"hidden\" class=\"form-control\" name=\"address\" readonly/>\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<input type=\"hidden\" class=\"form-control\" name=\"category\" readonly/>\r\n");
      out.write("								\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<div class=\"form-group col-4\">\r\n");
      out.write("									<label for=\"startTime\">StartTime</label>\r\n");
      out.write("									<input type=\"time\" class=\"form-control\" name=\"startTime\" />\r\n");
      out.write("								</div>\r\n");
      out.write("								\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<div class=\"form-group col-4\">\r\n");
      out.write("									<label for=\"endTime\">EndTime</label>\r\n");
      out.write("									<input type=\"time\" class=\"form-control\" name=\"endTime\" />\r\n");
      out.write("								</div>\r\n");
      out.write("								\r\n");
      out.write("								");
      out.write("\r\n");
      out.write("								<div class=\"form-group col-4\">\r\n");
      out.write("									<label for=\"theme\">목적</label>\r\n");
      out.write("									<select class=\"custom-select my-1 mr-sm-2 \" id=\"theme\" name=\"theme\">\r\n");
      out.write("										<option value=\"방문\" selected>방문</option>\r\n");
      out.write("										<option value=\"데이트\">데이트</option>\r\n");
      out.write("										<option value=\"가족여행\">가족여행</option>\r\n");
      out.write("										<option value=\"친구들과\">친구들과</option>\r\n");
      out.write("										<option value=\"맛집탐방\">맛집탐방</option>\r\n");
      out.write("										<option value=\"비즈니스\">비즈니스</option>\r\n");
      out.write("										<option value=\"소개팅\">소개팅</option>\r\n");
      out.write("										<option value=\"미용\">미용</option>\r\n");
      out.write("										<option value=\"운동\">운동</option>\r\n");
      out.write("										<option value=\"문화생활\">문화생활</option>\r\n");
      out.write("										<option value=\"여가생활\">여가생활</option>\r\n");
      out.write("									</select>\r\n");
      out.write("								</div>\r\n");
      out.write("								\r\n");
      out.write("								");
      out.write("								\r\n");
      out.write("								<div class=\"form-group col-12 toggle none\">\r\n");
      out.write("									<label for=\"transportation\">교통수단</label>\r\n");
      out.write("									<input type=\"text\" class=\"form-control\" name=\"transportation\" />\r\n");
      out.write("								</div>\r\n");
      out.write("								\r\n");
      out.write("								");
      out.write("	\r\n");
      out.write("								<div class=\"form-group col-12 toggle none\">\r\n");
      out.write("									<label for=\"details\">상세 일정</label>\r\n");
      out.write("									<textarea rows=\"5\" class=\"form-control\" name=\"details\" ></textarea>\r\n");
      out.write("								</div>\r\n");
      out.write("							</div>\r\n");
      out.write("						</div>\r\n");
      out.write("					</form>\r\n");
      out.write("				</div>\r\n");
      out.write("			</div>\r\n");
      out.write("		</div>\r\n");
      out.write("	</div>\r\n");
      out.write("</section>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("	<input id=\"modalBtn\" type=\"hidden\" class=\"btn btn-info btn-lg\" data-toggle=\"modal\" data-target=\"#myModal\" value=\"modal\"/>\r\n");
      out.write("	");
      out.write("\r\n");
      out.write("	<div class=\"modal fade\" id=\"myModal\" role=\"dialog\">\r\n");
      out.write("		<div class=\"modal-dialog modal-dialog-centered modal-md text-center\">\r\n");
      out.write("			<div class=\"modal-content\">\r\n");
      out.write("				<div class=\"modal-header bg-light\">\r\n");
      out.write("					<h3 class=\"modal-title font-italic\">WAYG</h3>\r\n");
      out.write("				</div>\r\n");
      out.write("				<div class=\"modal-body bg-light\">\r\n");
      out.write("					<h4></h4>\r\n");
      out.write("				</div>\r\n");
      out.write("				<div class=\"modal-footer bg-light row mx-0\">\r\n");
      out.write("					<button id=\"trueBtn\" type=\"button\" class=\"btn btn-sm btn-primary col-6 mx-0 border-white\">저장</button>\r\n");
      out.write("					<button id=\"falseBtn\" type=\"button\" class=\"btn btn-sm btn-danger col-6 mx-0 border-white\" data-dismiss=\"modal\">닫기</button>\r\n");
      out.write("				</div>\r\n");
      out.write("			</div>\r\n");
      out.write("		</div>\r\n");
      out.write("	</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/plan/kakaomap/kakaomap.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("  \r\n");
      out.write(" \r\n");
      out.write("   \r\n");
      out.write("  \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"ko\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<footer class=\"bg-dark\">\r\n");
      out.write("\r\n");
      out.write("</footer>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    boolean _jspx_th_c_005fforEach_005f0_reused = false;
    try {
      _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fforEach_005f0.setParent(null);
      // /WEB-INF/views/plan/../header.jsp(57,11) name = begin type = int reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setBegin(1);
      // /WEB-INF/views/plan/../header.jsp(57,11) name = end type = int reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setEnd(10);
      // /WEB-INF/views/plan/../header.jsp(57,11) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setVar("i");
      int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
        if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\r\n");
            out.write("			          	<li class=\"list-group-item\">\r\n");
            out.write("			          		alarm");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${i}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("\r\n");
            out.write("			          	</li>\r\n");
            out.write("		          	");
            int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (java.lang.Throwable _jspx_exception) {
        while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_c_005fforEach_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0);
      _jspx_th_c_005fforEach_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fforEach_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fforEach_005f0_reused);
    }
    return false;
  }
}
