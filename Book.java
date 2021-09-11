package com.practice;

public class Book {

		int id;
		String name,author,publisher;

		public Book(int id, String name, String author, String publisher) {
			this.id=id;
			this.name = name;
			this.author = author;
			this.publisher = publisher;
		}


		public Book(String name, String author, String publisher) {
		
			this.name = name;
			this.author = author;
			this.publisher = publisher;
		}
		
		
		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getPublisher() {
			return publisher;
		}

		public void setPublisher(String publisher) {
			this.publisher = publisher;
		}


	
		
		

	}


