package view;

import java.util.concurrent.Semaphore;

import controller.ThreadPrato;

public class Principal {

	public static void main(String[] args) {
		Semaphore mutex = new Semaphore(1);
		
		for (int i = 1; i <= 5; i++) {
			Thread prato = new ThreadPrato(mutex, i);
			prato.start();
		}
	}

}
