package Henker;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CpuHenker {

	public static void main(String[] args) throws Exception {

		PrintWriter zapis;
		zapis = new PrintWriter("henker.txt");
		long ostatecznaSrednia = 0;
		int licznik = 0;

		double startCzas = System.currentTimeMillis();
		do {
			long obecnaSrednia = henkerFunction(100);
			ostatecznaSrednia += obecnaSrednia;
			licznik++;
			zapis.println("Pula watkow:  100 " + ": " + obecnaSrednia / 10
					+ " ms  ");
		}

		while (System.currentTimeMillis() - startCzas < 960000);

		ostatecznaSrednia = ostatecznaSrednia / licznik;
		zapis.println("Ostateczna srednia" + ": " + ostatecznaSrednia / 10
				+ " ms ");
		zapis.close();

	}

	private static Runnable runnable = new Runnable() {

		@Override
		public void run() {
			double a = 234324.5552311;
			double b = 5435345.545522;
			double c = 32432432.634442;
			double d = 213122.3213212;
			for (int i = 0; i < 100000; i++) {

				a = a * b * c * d;
				b = a * b / c;
				c = c * b * a / d;
				a = b * c;
				c = c * a * b;
				d = c * b * a / d;

			}
		}
	};

	private static long henkerFunction(int pulaWatkow)
			throws InterruptedException {

		long srednia = 0;

		for (int r = 0; r < 10; r++) {

			ExecutorService executor = Executors.newFixedThreadPool(pulaWatkow);
			long startLicz = System.nanoTime();

			for (int i = 0; i < 50000; i++) {
				executor.submit(runnable);
			}

			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
			long koniec = System.nanoTime();
			srednia += ((koniec - startLicz) / 1000000);
			System.gc();

		}

		return srednia;

	}
}