package com.practice;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

	public class BookDao {

		private String jdbcURL = "jdbc:mysql://localhost:3306/batch_5723?useSSL=false";
				//"jdbc:mysql://127.0.0.1:3306/batch_5723";
		//"jdbc:mysql://localhost:3306/employee_database?useSSL=false";

		private String username = "root";

		private String password = "Udhay@1998";

		private static final String INSERT_BOOKS_SQL = "INSERT INTO Books" + "  (name, author , publisher) VALUES "
				+ " (?, ?, ?);";

		private static final String SELECT_BOOK_BY_ID = "select id,name,author,publisher from Books where id =?";
		private static final String SELECT_ALL_BOOKS = "select * from Books";
		private static final String DELETE_BOOKS_SQL = "delete from Books where id = ?;";
		private static final String UPDATE_BOOKS_SQL = "update Books set name = ?,author= ?, publisher =? where id = ?;";

		Connection getConnection() {
			Connection con = null;

			try {
				// 1 Load mySql driver
				Class.forName("com.mysql.jdbc.Driver");

				System.out.println("Drivers loaded successfully");

				con = DriverManager.getConnection(jdbcURL, username, password);
				System.out.println("Connection Made.");

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return con;

		}

		public void insertBook(Book book) {
			System.out.println(INSERT_BOOKS_SQL);

			// try with resource will auto close connection
			try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_BOOKS_SQL);) {

				ps.setString(1, book.getName());
				ps.setString(2, book.getAuthor());
				ps.setString(3, book.getPublisher());

				ps.executeUpdate();
				System.out.println("Inserted record.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public boolean updateBook(Book book) {
			System.out.println(UPDATE_BOOKS_SQL);
			boolean rowUpdated = false;
			// try with resource will auto close connection
			try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_BOOKS_SQL);) {

				ps.setString(1, book.getName());
				ps.setString(2, book.getAuthor());
				ps.setString(3, book.getPublisher());
				ps.setInt(4, book.getId());

				int result = ps.executeUpdate();
				rowUpdated = result > 0;
				System.out.println("Record Updated Successfully.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return rowUpdated;
		}

		public boolean deleteBook(int id) {

			boolean rowDeleted = false;
			System.out.println(DELETE_BOOKS_SQL);
			try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_BOOKS_SQL);) {

				ps.setInt(1, id);

				int result = ps.executeUpdate();
				rowDeleted = result > 0;
				System.out.println("Record Deleted  Successfully.");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return rowDeleted;
		}

		public Book selectBook(int id) {
			Book book = null;
			System.out.println(SELECT_BOOK_BY_ID);
			try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_BOOK_BY_ID);) {

				ps.setInt(1, id);

				System.out.println(ps + " prepared Statment object created for select query by id");

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					String name = rs.getString("name");
					String author = rs.getString("author");
					String publisher= rs.getString("publisher");

					book= new Book(id, name, author, publisher);
					System.out.println("Select book by id  "+id);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return book;
		}

		public List<Book> selectAllBooks() {
			List<Book> books = new ArrayList<Book>();

			System.out.println(SELECT_ALL_BOOKS);
			try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_ALL_BOOKS);) {

				System.out.println(ps + " prepared Statment object created for select query for all books");

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String author = rs.getString("author");
					String publisher = rs.getString("publisher");
					
					books.add(new Book(id, name, author, publisher));
					System.out.println("book added to ArrayList");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return books;
		}
	}



