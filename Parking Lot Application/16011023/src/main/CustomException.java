package main;

import java.util.InputMismatchException;

@SuppressWarnings("serial")
public class CustomException extends InputMismatchException {
	CustomException(String arg0) {
		super(arg0);
	}
}
