package controller;

import java.util.concurrent.Semaphore;

public class ThreadPrato extends Thread {
	
	private Semaphore mutex;
	private int threadID;
	
	public ThreadPrato(Semaphore mutex, int threadID) {
		this.mutex = mutex;
		this.threadID = threadID;
	}
	
	@Override
	public void run() {
		String pratoNome = "";
		
		if (threadID % 2 == 1) {
			pratoNome = "Sopa de Cebola";
			cozinhar(pratoNome, 500, 800);
		} else {
			pratoNome = "Lasanha a Bolonesa";
			cozinhar(pratoNome, 600, 1200);
		}
		try {
			mutex.acquire();
			entregar(pratoNome);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutex.release();
		}
	}

	private void cozinhar(String pratoNome, int tempoMinCozimento, int tempoMaxCozimento) {
		System.out.println(pratoNome + " (ID #" + threadID + ") começou a cozinhar!");
		
		int tempoCozimento = (int)((Math.random() * (tempoMaxCozimento - tempoMinCozimento + 1)) + tempoMinCozimento);
		for (int i = 0; i < tempoCozimento; i+= 100) {
			System.out.println(pratoNome + " (ID #" + threadID + ") esta cozinhando. Percentual de cozimento: "  + (int)(((double) i / tempoCozimento) * 100) + "%");
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(pratoNome + " (ID #" + threadID + ") terminou de cozinhar!");
	}
	
	private void entregar(String pratoNome) {
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(pratoNome + " (ID #" + threadID + ") foi entregue!");
	}

}
