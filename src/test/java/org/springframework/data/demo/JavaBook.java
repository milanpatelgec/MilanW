package org.springframework.data.demo;

import java.awt.print.Book;

public class JavaBook extends Book {

	@Override
	public String toString() {
		return "JavaBook :: " + super.toString();
	}

}
