package test;

public class TestThreadRunnable implements Runnable {

	public void run() {
		try{
			Thread.sleep(3000);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		System.out.println("finished");
	}
}
