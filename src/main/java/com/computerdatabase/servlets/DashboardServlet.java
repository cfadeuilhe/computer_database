package main.java.com.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.computerdatabase.model.Entity;
import main.java.com.computerdatabase.model.Page;
import main.java.com.computerdatabase.service.ComputerService;

public class DashboardServlet extends HttpServlet {

	private final static ComputerService COMPUTER_SERVICE = new ComputerService();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Page p;
		long page = 1, limit = 10;
		List<Entity> list = new ArrayList<Entity>();
		list = COMPUTER_SERVICE.listComputers();
		request.setAttribute("completeList", list);

		List<Entity> listPage = new ArrayList<Entity>();
		String pageNumber = request.getParameter("page");
		String pageLimit = request.getParameter("limit");

		page = checkParameter(pageNumber, 1);
		limit = checkParameter(pageLimit, 10);

		p = new Page(page, limit, (list.size() / limit + 1));

		listPage = COMPUTER_SERVICE.readPages(p);
		request.setAttribute("computerList", listPage);
		request.setAttribute("page", p);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	public long checkParameter(String s, long defaultVal) {
		long r;
		if (s != null) {
			try {
				r = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				r = defaultVal;
			}
		} else {
			r = defaultVal;
		}
		return r;
	}

	public void test() {
		System.out.println("yes");
	}
}
