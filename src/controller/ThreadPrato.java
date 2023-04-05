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
			// Pratos de ID ímpar, são chamados de Sopa de Cebola e levam de 0,5 a 0,8 segundos para ficar prontos.
			pratoNome = "Sopa de Cebola";
			cozinhar(pratoNome, 500, 800);
		} else {
			// Pratos de ID par, são chamados de Lasanha a Bolonhesa e levam de 0,6 a 1,2 segundos para ficar prontos. Quando
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

	// Quando um prato inicia, é necessário comunicar, em console, que se iniciou e, a cada 0,1 segundos, deve-se exibir
	// o percentual de cozimento
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
	
	// Quando um prato fica pronto, é necessário comunicar em console o final e
	// fazer a entrega, que leva 0,5 segundos. O jogador só pode entregar um prato por vez e
	// deve comunicar a entrega.
	private void entregar(String pratoNome) {
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(pratoNome + " (ID #" + threadID + ") foi entregue!");
	}

}
