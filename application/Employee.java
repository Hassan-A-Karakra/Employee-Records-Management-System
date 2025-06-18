package application;

public class Employee {
	private String employeeID;
	private String name;
	private byte age;
	private String department;
	private String dateOfJoining;
	private char gender;

	public Employee(String employeeID, String name, byte age, String department, String dateOfJoining, char gender) {
		this.employeeID = employeeID;
		this.name = name;
		this.age = age;
		this.department = department;
		this.dateOfJoining = dateOfJoining;
		this.gender = gender;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

}