package org.springframework.data.demo;

import java.awt.print.Book;

public class MongoBook extends Book {

	@Override
	public String toString() {
		return "MongoBook :: " + super.toString();
	}

}
