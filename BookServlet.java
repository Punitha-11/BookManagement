package com.practice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookDao bookDao;

	public BookServlet() {
		super();

	}

	@Override
	public void init() {
		bookDao = new BookDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		String action = request.getServletPath();
		System.out.println(action);

		switch(action) {
		case "/new":
			showNewForm(request, response);
			break;

		case "/insert":
			insertBook(request, response);
			break;

		case "/delete":
			deleteBook(request, response);
			break;

		case "/update":
			updateBook(request, response);
			break;

		case "/edit":
			showEditForm(request, response);
			break;

		default:
			listBook(request, response);
			break;

		}
	}

	private void listBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Book> listBook =bookDao.selectAllBooks();

		request.setAttribute("listBook", listBook);

		RequestDispatcher rd = request.getRequestDispatcher("BookList.jsp");
		rd.forward(request, response);
	}

	// blank form
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) {

		RequestDispatcher rd = request.getRequestDispatcher("Bookform.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// existing user
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) {

		int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("Book Servlets "+id);
		
        Book existing = bookDao.selectBook(id);

		RequestDispatcher rd = request.getRequestDispatcher("Bookform.jsp");
		// to display existing users details in user-form.jsp
		request.setAttribute("book", existing);

		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		String name = request.getParameter("name");
		String author = request.getParameter("author");
		String publisher = request.getParameter("publisher");

		Book newBook = new Book(name, author, publisher);
		bookDao.insertBook(newBook);
		System.out.println("new book is added --servlets");
		try {
			response.sendRedirect("BookList.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String author = request.getParameter("author");
		String publisher = request.getParameter("publisher");

		Book existingBook = new Book(id, name, author, publisher);

		bookDao.updateBook(existingBook);

		System.out.println("existing book updated --servlets");
		try {
			response.sendRedirect("list");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		int id = Integer.parseInt(request.getParameter("id"));

		bookDao.deleteBook(id);

		System.out.println("existing book deleted --servlets");
		try {
			response.sendRedirect("list");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
