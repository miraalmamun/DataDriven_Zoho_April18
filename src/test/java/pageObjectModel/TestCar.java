package pageObjectModel;

public class TestCar {

	public static void main(String[] args) {
		Car c = new Car();
		c.start();
		c.stop();

		AudiCar c1 = new AudiCar();
		c1.start();
		c1.stop();
		c1.openGPS();

		c1.e.eName = "Engine x";

	}

}
