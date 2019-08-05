package pageObjectModel;

public class Car {
	String name;
	Engine e;

	public void start() {
		e = new Engine();
		e.startup();
		System.out.println("Car starting");
	}

	public void stop() {
		System.out.println("Car stoping");
	}
}
