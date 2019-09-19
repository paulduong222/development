package test;

public class TestThread {

	public static void main(String[] args){
		TestThreadRunnable t = new TestThreadRunnable();
		System.out.println("before");
		new Thread(t).start();
		System.out.println("after");
	}
}
