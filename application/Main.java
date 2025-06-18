package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;

public class Main extends Application {
	private ArrayList list = new ArrayList<Employee>();
	private TableView<Employee> table;
	private ObservableList<Employee> dataTableView = FXCollections.observableArrayList();

	private BorderPane pane;
	private File file;
	private ObservableList<Employee> data = FXCollections.observableArrayList();

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	@Override
	public void start(Stage primaryStage) {

		table = new TableView<>();
		table.setStyle("-fx-background-color: linear-gradient(to right, #acb6e5, #86fde8);");

		pane = new BorderPane();
		pane.setStyle("-fx-background-color: linear-gradient(to right, #acb6e5, #86fde8);");

		Label labelWelcome = new Label("WELCOME EMPLOYEE");
//		labelWelcome.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-style: italic");
//		labelWelcome.setStyle("-fx-background-color: linear-gradient(to right, #acb6e5, #86fde8);");
		labelWelcome.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-style: italic; "
				+ "-fx-background-color: linear-gradient(to right, #acb6e5, #86fde8);");
		pane.setCenter(labelWelcome);

		// Create table columns
		TableColumn<Employee, String> temployeeID = new TableColumn<>("ID");
		temployeeID.setStyle("-fx-background-color: linear-gradient(to right, #ff7e5f, #feb47b);");

		TableColumn<Employee, String> tName = new TableColumn<>("Name");
		tName.setStyle("-fx-background-color: linear-gradient(to right, #43cea2, #185a9d);");

		TableColumn<Employee, String> tAge = new TableColumn<>("Age");
		tAge.setStyle("-fx-background-color: linear-gradient(to right, #d4fc79, #96e6a1);");

		TableColumn<Employee, String> tDepartment = new TableColumn<>("Department");
		tDepartment.setStyle("-fx-background-color: linear-gradient(to right, #fce38a, #f38181);");

		TableColumn<Employee, String> tdateOfJoining = new TableColumn<>("Date");
		tdateOfJoining.setStyle("-fx-background-color: linear-gradient(to right, #fbc2eb, #a6c1ee);");

		TableColumn<Employee, String> tGender = new TableColumn<>("Gender");
		tGender.setStyle("-fx-background-color: linear-gradient(to right, #a8e063, #56ab2f);");

		// Set cell value factories for each column
		temployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
		tName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		tDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
		tdateOfJoining.setCellValueFactory(new PropertyValueFactory<>("dateOfJoining"));
		tGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

		// Add columns to the table
		table.getColumns().addAll(temployeeID, tName, tAge, tDepartment, tdateOfJoining, tGender);
		table.setStyle("-fx-background-color: linear-gradient(to right, #43cea2, #185a9d);");

		// Add the table to the pane's left side
		pane.setLeft(table);

		// Menu bar and options
		MenuBar bar = new MenuBar();
		bar.setStyle("-fx-background-color: linear-gradient(to right, #43cea2, #185a9d);");

		Menu menuFile = new Menu("File");
		menuFile.setStyle("-fx-background-color: linear-gradient(to right, #ffecd2, #fcb69f);");

		Menu menuEmployee = new Menu("Employee");
		menuEmployee.setStyle("-fx-background-color: linear-gradient(to right, #d4fc79, #96e6a1);");

		bar.getMenus().addAll(menuFile, menuEmployee);

		MenuItem itemOpenFile = new MenuItem("Open File");
		itemOpenFile.setStyle("-fx-background-color: linear-gradient(to right, #fbc2eb, #a6c1ee);");

		menuFile.getItems().add(itemOpenFile);
		itemOpenFile.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			file = chooser.showOpenDialog(primaryStage);
			if (file != null) {
				read(file); // Populate the table with file data
				setupTable();

			}
		});
		MenuItem add = new MenuItem("Add");
		add.setStyle("-fx-background-color: linear-gradient(to right, #d4fc79, #96e6a1);");

		MenuItem delete = new MenuItem("Delete");
		delete.setStyle("-fx-background-color: linear-gradient(to right, #fce38a, #f38181);");

		MenuItem search = new MenuItem("Search");
		search.setStyle("-fx-background-color: linear-gradient(to right, #a8e063, #56ab2f);");

		MenuItem update = new MenuItem("Update");
		update.setStyle("-fx-background-color: linear-gradient(to right, #fbc2eb, #a6c1ee);");

		MenuItem save = new MenuItem("Save");
		save.setStyle("-fx-background-color: linear-gradient(to right, #43cea2, #185a9d);");

		MenuItem display = new MenuItem("Display");
		display.setStyle("-fx-background-color: linear-gradient(to right, #d4fc79, #96e6a1);");

		add.setOnAction(e -> {
			add();
		});
		delete.setOnAction(e -> {
			delete();
		});
		search.setOnAction(e -> {
			search();
		});
		update.setOnAction(e -> {
			update();
		});
		save.setOnAction(e -> {
			save();
		});
		display.setOnAction(e -> {
			display();
		});
		menuEmployee.getItems().addAll(add, delete, search, update, display, save);
		menuEmployee.setStyle("-fx-background-color: linear-gradient(to right, #ffecd2, #fcb69f);");

		pane.setTop(bar);

		Scene scene = new Scene(pane, 1000, 600);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Employee Application");
		primaryStage.show();
	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	/// create method add
	private void add() {

		/// create pane
		pane.setBottom(null);
		GridPane gPane = new GridPane();

		///// -----create label and text field
		Label lblResult = new Label();
		Label lblEmployeeID = new Label("Employee ID :");
		Label lblName = new Label("Name :");
		Label lblAge = new Label("Age :");
		Label lblDepartment = new Label("Department :");
		Label lblDateOfJoining = new Label("Date of Joining (YYYY/MM/DD) :");

		TextField tfEmployeeID = new TextField();
		IconedTextFieled(lblEmployeeID, tfEmployeeID);

		TextField tfName = new TextField();
		IconedTextFieled(lblName, tfName);

		TextField tfAge = new TextField();
		IconedTextFieled(lblAge, tfAge);

		TextField tfDepartment = new TextField();
		IconedTextFieled(lblDepartment, tfDepartment);

		//// *** make DatePicker for Date of Joining
		DatePicker datePicker = new DatePicker();
		datePicker.setPromptText("Select Date");
		IconedTextFieled(lblDateOfJoining, datePicker.getEditor());

		// ----- make a radio button group for gender selection
		ToggleGroup genderGroup = new ToggleGroup();
		RadioButton maleButton = new RadioButton("M");
		maleButton.setToggleGroup(genderGroup);
		RadioButton femaleButton = new RadioButton("F");
		femaleButton.setToggleGroup(genderGroup);
		HBox genderBox = new HBox(10, new Label("Gender:"), maleButton, femaleButton);
		icons(genderBox); // Style method for gender box

		Button btAdd = new Button("Add");
		Button btClear = new Button("Clear");
		butoonEffect(btAdd);
		butoonEffect(btClear);

		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btAdd);
		hbButtons.setAlignment(Pos.CENTER);

		gPane.add(lblEmployeeID, 0, 0);
		gPane.add(tfEmployeeID, 1, 0);
		gPane.add(lblName, 0, 1);
		gPane.add(tfName, 1, 1);
		gPane.add(lblAge, 0, 2);
		gPane.add(tfAge, 1, 2);
		gPane.add(lblDepartment, 0, 3);
		gPane.add(tfDepartment, 1, 3);
		gPane.add(lblDateOfJoining, 0, 4);
		gPane.add(datePicker, 1, 4);
		gPane.add(genderBox, 1, 5);
		gPane.add(hbButtons, 1, 6);
		gPane.add(lblResult, 1, 7);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);

		pane.setCenter(gPane);

		//// make clear for the all field
		btClear.setOnAction(e -> {
			tfEmployeeID.clear();
			tfName.clear();
			tfAge.clear();
			tfDepartment.clear();
			datePicker.setValue(null);
			genderGroup.selectToggle(null);
			lblResult.setText("");
		});

		btAdd.setOnAction(e -> {

			// get values from the text fields
			String employeeIDStr = tfEmployeeID.getText();
			String name = tfName.getText();
			String ageStr = tfAge.getText();
			String department = tfDepartment.getText();

			/// use located data for the make data picker
			LocalDate dateOfJoining = datePicker.getValue();

			// get the selected gender from radio buttons
			RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();
			String genderStr = selectedGender != null ? selectedGender.getText() : "";

			// user input validation
			if (employeeIDStr.isEmpty() || name.isEmpty() || ageStr.isEmpty() || department.isEmpty()
					|| dateOfJoining == null || genderStr.isEmpty()) {
				lblResult.setText("All fields must be filled.");
				return;
			}

			try {
				// No need to parse employeeID into an integer since it can include letters and
				// numbers

				byte age = Byte.parseByte(ageStr); // Validate age
				char gender = genderStr.charAt(0); // Get the gender (either 'M' or 'F')

				// Create a new Employee object with LocalDate dateOfJoining
				Employee employee = new Employee(employeeIDStr, name, age, department, dateOfJoining.toString(),
						gender);

				// Add the new employee to the table
				dataTableView.add(employee);
				list.add(employee);
//				System.out.println(list);
////				for(int i=0 ; i< list.size();i++) {
//					System.out.println(list.get(i));
//				}

				// Clear input fields and display success message
				btClear.fire();
				lblResult.setText("-->Employee added successfully!!!");

			} catch (NumberFormatException nfe) {
				lblResult.setText("-->Invalid input. Age must be a number!!!");

			} catch (IndexOutOfBoundsException ioe) {
				lblResult.setText("-->Invalid input for gender!!!");
			}
		});

	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	private void delete() {

		pane.setBottom(null);
		GridPane gPane = new GridPane();

		Label lblResult = new Label();
		Label lblEmployeeID = new Label("Enter Employee ID to delete");

		TextField tfEmployeeID = new TextField();
		IconedTextFieled(lblEmployeeID, tfEmployeeID);

		Button btDelete = new Button("Delete");
		Button btClear = new Button("Clear");
		butoonEffect(btDelete);
		butoonEffect(btClear);

		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btDelete);
		hbButtons.setAlignment(Pos.CENTER);

		gPane.add(lblEmployeeID, 0, 0);
		gPane.add(tfEmployeeID, 1, 0);
		gPane.add(hbButtons, 1, 1);
		gPane.add(lblResult, 1, 2);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);

		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfEmployeeID.clear();
			lblResult.setText("");
		});

		btDelete.setOnAction(e -> {
			String employeeID = tfEmployeeID.getText();

			if (employeeID.isEmpty()) {
				lblResult.setText("Employee ID must be filled");
				return;
			}

			// find and remove the employee by Employee ID
			Employee employeeToDelete = null;
			for (Employee employee : dataTableView) {
				if (employee.getEmployeeID().equals(employeeID)) {
					employeeToDelete = employee;
					break;
				}
			}

			if (employeeToDelete != null) {
				dataTableView.remove(employeeToDelete);
				list.remove(employeeToDelete);
				lblResult.setText("Employee with ID " + employeeID + " was deleted.");
			} else {
				lblResult.setText("Employee with ID " + employeeID + " not found.");
			}

			tfEmployeeID.clear();
		});
	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	private void search() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();

		Label lblResult = new Label();
		Label lblEmployeeID = new Label("Enter Employee ID to search");

		TextField tfEmployeeID = new TextField();
		IconedTextFieled(lblEmployeeID, tfEmployeeID);

		Button btSearch = new Button("Search");
		Button btClear = new Button("Clear");
		butoonEffect(btSearch);
		butoonEffect(btClear);

		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btSearch);
		hbButtons.setAlignment(Pos.CENTER);

		gPane.add(lblEmployeeID, 0, 0);
		gPane.add(tfEmployeeID, 1, 0);
		gPane.add(hbButtons, 1, 1);
		gPane.add(lblResult, 1, 2);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);

		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfEmployeeID.clear();
			lblResult.setText("");
		});

		btSearch.setOnAction(e -> {
			String employeeID = tfEmployeeID.getText();

			if (employeeID.isEmpty()) {
				lblResult.setText("--->Employee ID must be filled !!!");
				return;
			}

			// search for the employee by Employee ID
			Employee employeeToSearch = null;
			for (Employee employee : dataTableView) {
				if (employee.getEmployeeID().equals(employeeID)) {
					employeeToSearch = employee;
					break;
				}
			}

//			if (employeeToSearch != null) {
//				lblResult.setText("Employee Found:\n" + "ID: " + employeeToSearch.getEmployeeID() + "\n" + "Name: "
//						+ employeeToSearch.getName() + "\n" + "Age: " + employeeToSearch.getAge() + "\n"
//						+ "Department: " + employeeToSearch.getDepartment() + "\n" + "Date of Joining: "
//						+ employeeToSearch.getDateOfJoining() + "\n" + "Gender: " + employeeToSearch.getGender());

			if (employeeToSearch != null) {
				lblResult.setText("Employee Exist:");
			} else {
				lblResult.setText("Employee with ID " + employeeID + " not found.");
			}
		});

	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	private void update() {
		pane.setBottom(null);
		GridPane gPane = new GridPane();

		Label lblResult = new Label();
		Label lblEmployeeID = new Label("Enter Employee ID to update");

		TextField tfEmployeeID = new TextField();
		IconedTextFieled(lblEmployeeID, tfEmployeeID);

		TextField tfName = new TextField();
		IconedTextFieled(lblEmployeeID, tfName);

		TextField tfAge = new TextField();
		IconedTextFieled(lblEmployeeID, tfAge);

		TextField tfDepartment = new TextField();
		IconedTextFieled(lblEmployeeID, tfDepartment);

		// Replace TextField for Date of Joining with a DatePicker
		DatePicker datePicker = new DatePicker();
		datePicker.setPromptText("Select Date");

		// Create a radio button group for gender selection
		ToggleGroup genderGroup = new ToggleGroup();
		RadioButton maleButton = new RadioButton("M");
		maleButton.setToggleGroup(genderGroup);
		RadioButton femaleButton = new RadioButton("F");
		femaleButton.setToggleGroup(genderGroup);
		HBox genderBox = new HBox(10, new Label("Gender:"), maleButton, femaleButton);
		icons(genderBox); // Style method for gender box

		Button btUpdate = new Button("Update");
		Button btClear = new Button("Clear");
		butoonEffect(btUpdate);
		butoonEffect(btClear);

		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btUpdate);
		hbButtons.setAlignment(Pos.CENTER);

		// Add all fields to the grid pane
		gPane.add(lblEmployeeID, 0, 0);
		gPane.add(tfEmployeeID, 1, 0);
		gPane.add(new Label("Name"), 0, 1);
		gPane.add(tfName, 1, 1);
		gPane.add(new Label("Age"), 0, 2);
		gPane.add(tfAge, 1, 2);
		gPane.add(new Label("Department"), 0, 3);
		gPane.add(tfDepartment, 1, 3);
		gPane.add(new Label("Date of Joining"), 0, 4);
		gPane.add(datePicker, 1, 4); // Use DatePicker instead of TextField
		gPane.add(genderBox, 1, 5); // Use RadioButton for gender selection
		gPane.add(hbButtons, 1, 6);
		gPane.add(lblResult, 1, 7);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);

		pane.setCenter(gPane);
		pane.setStyle("-fx-background-color: linear-gradient(to right, #acb6e5, #86fde8);");
		gPane.setStyle("-fx-background-color: linear-gradient(to right, #acb6e5, #86fde8);");

		// Clear input fields
		btClear.setOnAction(e -> {
			tfEmployeeID.clear();
			tfName.clear();
			tfAge.clear();
			tfDepartment.clear();
			datePicker.setValue(null); // Clear the DatePicker
			genderGroup.selectToggle(null); // Clear gender selection
			lblResult.setText("");
		});

		btUpdate.setOnAction(e -> {
			String employeeIDStr = tfEmployeeID.getText();
			String name = tfName.getText();
			String ageStr = tfAge.getText();
			String department = tfDepartment.getText();
			LocalDate dateOfJoining = datePicker.getValue();
			RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();
			String genderStr = selectedGender != null ? selectedGender.getText() : "";

			if (employeeIDStr.isEmpty() || name.isEmpty() || ageStr.isEmpty() || department.isEmpty()
					|| dateOfJoining == null || genderStr.isEmpty()) {
				lblResult.setText("All fields must be filled");
				return;
			}

			try {
				// Employee ID remains a string, so no parsing to an integer is needed here

				byte age = Byte.parseByte(ageStr); // Validate age (age must be a valid number)
				char gender = genderStr.charAt(0); // Validate gender

				// Format the date
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String formattedDate = dateOfJoining.format(formatter);

				// Find the employee and update the details
				Employee employeeToUpdate = null;
				for (Employee employee : dataTableView) {
					if (employee.getEmployeeID().equals(employeeIDStr)) { // Compare IDs as Strings
						lblResult.setText("Employee found: " + employee.getName());
						employeeToUpdate = employee;
						break;
					}
				}

				if (employeeToUpdate != null) {
					employeeToUpdate.setName(name);
					employeeToUpdate.setAge(age);
					employeeToUpdate.setDepartment(department);
					employeeToUpdate.setDateOfJoining(formattedDate);
					employeeToUpdate.setGender(gender);
					lblResult.setText("Employee updated successfully!");

					// Refresh the TableView to display changes
					table.refresh();
				} else {
					lblResult.setText("Employee with ID " + employeeIDStr + " not found.");
				}

			} catch (NumberFormatException nfe) {
				lblResult.setText("Invalid input for Age."); // Only age needs to be validated as a number
			} catch (IndexOutOfBoundsException ioe) {
				lblResult.setText("Invalid input for gender.");
			}
		});

	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	private void save() {

		//////// make a FileChooser to let the user specify where to save the file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Employee Data");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		// show save dialog
		File saveFile = fileChooser.showSaveDialog(pane.getScene().getWindow());

		if (saveFile != null) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
				// write a header if needed
				writer.write("EmployeeID,Name,Age,Department,Date of Joining,Gender");
				writer.newLine();

				// write each employee to the file
				for (Employee employee : dataTableView) {
					String employeeRecord = String.format("%s,%s,%d,%s,%s,%c", employee.getEmployeeID(),
							employee.getName(), employee.getAge(), employee.getDepartment(),
							employee.getDateOfJoining(), employee.getGender());
					writer.write(employeeRecord);
					writer.newLine(); // Move to the next line after each record
				}

				// display success message
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Save Successful");
				alert.setHeaderText(null);
				alert.setContentText("Employee data successfully saved to " + saveFile.getName());
				alert.showAndWait();

			} catch (IOException e) {
				e.printStackTrace();
				// Display error message in case of failure
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Save Failed");
				alert.setHeaderText(null);
				alert.setContentText("Failed to save employee data.");
				alert.showAndWait();
			}
		}
	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	private void display() {
		pane.setBottom(null); // Clear the bottom pane if necessary
		GridPane gPane = new GridPane();

		Label lblResult = new Label();
		Label lblEmployeeID = new Label("Enter Employee ID to display");

		TextField tfEmployeeID = new TextField();
		IconedTextFieled(lblEmployeeID, tfEmployeeID);

		Button btDisplay = new Button("Display");
		Button btClear = new Button("Clear");
		butoonEffect(btDisplay);
		butoonEffect(btClear);

		HBox hbButtons = new HBox(10);
		hbButtons.getChildren().addAll(btClear, btDisplay);
		hbButtons.setAlignment(Pos.CENTER);

		gPane.add(lblEmployeeID, 0, 0);
		gPane.add(tfEmployeeID, 1, 0);
		gPane.add(hbButtons, 1, 1);
		gPane.add(lblResult, 1, 2);
		gPane.setHgap(20);
		gPane.setVgap(20);
		gPane.setAlignment(Pos.CENTER);

		pane.setCenter(gPane);

		btClear.setOnAction(e -> {
			tfEmployeeID.clear();
			lblResult.setText("");
		});

		btDisplay.setOnAction(e -> {
			String employeeID = tfEmployeeID.getText();

			if (employeeID.isEmpty()) {
				lblResult.setText("Employee ID must be filled.");
				return;
			}

			// Search for the employee by Employee ID
			Employee employeeToSearch = null;
			for (Employee employee : dataTableView) {
				if (employee.getEmployeeID().equals(employeeID)) {
					employeeToSearch = employee;
					break;
				}
			}

			if (employeeToSearch != null) {
				lblResult.setText("Employee Found:\n" + "---> ID: " + employeeToSearch.getEmployeeID() + "\n"
						+ "---> Name: " + employeeToSearch.getName() + "\n" + "---> Age: " + employeeToSearch.getAge()
						+ "\n" + "---> Department: " + employeeToSearch.getDepartment() + "\n"
						+ "---> Date of Joining: " + employeeToSearch.getDateOfJoining() + "\n" + "---> Gender: "
						+ employeeToSearch.getGender());
			} else {
				lblResult.setText("Employee with ID " + employeeID + " not found.");
			}
		});
	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	private void setupTable() {
		table.setItems(dataTableView);

		// ID column
		TableColumn<Employee, String> employeeID = new TableColumn<>("ID");
		employeeID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeID()));

		// Name column
		TableColumn<Employee, String> name = new TableColumn<>("Name");
		name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

		// Age column (convert byte to String for display)
		TableColumn<Employee, String> age = new TableColumn<>("Age");
		age.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAge())));

		// Department column
		TableColumn<Employee, String> department = new TableColumn<>("Department");
		department.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));

		// Date of Joining column
		TableColumn<Employee, String> dateOfJoining = new TableColumn<>("Date");
		dateOfJoining.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfJoining()));

		// Gender column (convert char to String for display)
		TableColumn<Employee, String> gender = new TableColumn<>("Gender");
		gender.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGender())));

		// Add all the columns to the table
		table.getColumns().setAll(employeeID, name, age, department, dateOfJoining, gender);
	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	private void read(File file) {
		try (Scanner in = new Scanner(file)) {
			if (in.hasNextLine()) {
				in.nextLine();
			}
			while (in.hasNextLine()) {
				String line = in.nextLine();

				if (line.isEmpty())
					continue;
				String[] splitLine = line.split(",");

				if (splitLine.length < 6)
					continue;
				try {
					String employeeID = splitLine[0];
					String name = splitLine[1];
					byte age = Byte.parseByte(splitLine[2]);
					String department = splitLine[3];
					String dateOfJoining = splitLine[4];
					char gender = splitLine[5].charAt(0);

					// make a new Employee object
					Employee employee = new Employee(employeeID, name, age, department, dateOfJoining, gender);

					// add the employee to the list for the TableView
					dataTableView.add(employee);
					list.add(employee);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					continue; // Handle parsing errors
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/// ---------------------------------------------------
	/// ---------------------------------------------------

	private void butoonEffect(Node b) {
		b.setOnMouseMoved(e -> {
			b.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 15;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #FFFFFF;\n" // White
																														// text
																														// color
					+ "-fx-background-color: #4CAF50;\n" // Green background on hover
					+ "-fx-border-color: #4CAF50;\n" // Green border on hover
					+ "-fx-border-width: 3.5;\n" + "-fx-background-radius: 25 25 25 25;");
		});

		b.setOnMouseExited(e -> {
			b.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 15;\n"
					+ "-fx-font-family: Times New Roman;\n" + "-fx-font-weight: Bold;\n" + "-fx-text-fill: #FFFFFF;\n" // White
																														// text
																														// color
					+ "-fx-background-color: #3E3E3E;\n" // Dark gray background when not hovered
					+ "-fx-border-color: #4CAF50;\n" // Green border when not hovered
					+ "-fx-border-width: 3.5;\n" + "-fx-background-radius: 25 25 25 25;");
		});
	}

	private void IconedTextFieled(Node l, Node t) {
		l.setStyle("-fx-border-color: #6A5ACD;" // SlateBlue border color
				+ "-fx-font-size: 14;\n" + "-fx-border-width: 1;" + "-fx-border-radius: 50;"
				+ "-fx-font-weight: Bold;\n" + "-fx-background-color: #E6E6FA;" // Lavender background
				+ "-fx-background-radius: 50 0 0 50");

		t.setStyle("-fx-border-radius: 0 50 50 0;\n" + "-fx-font-size: 14;\n" + "-fx-font-family: Times New Roman;\n"
				+ "-fx-font-weight: Bold;\n" + "-fx-background-color: #FFFFFF;\n" // White background for text field
				+ "-fx-border-color: #6A5ACD;\n" // SlateBlue border color
				+ "-fx-border-width: 3.5;" + "-fx-background-radius: 0 50 50 0");
	}

	private void icons(Node l) {
		l.setStyle("-fx-border-radius: 25 25 25 25;\n" + "-fx-font-size: 15;\n" + "-fx-font-family: Times New Roman;\n"
				+ "-fx-font-weight: Bold;\n" + "-fx-text-fill: #FFFFFF;\n" // White text color
				+ "-fx-background-color: #2F4F4F;\n" // DarkSlateGray background
				+ "-fx-border-color: #6A5ACD;\n" // SlateBlue border color
				+ "-fx-border-width: 3.5;" + "-fx-background-radius: 25 25 25 25");
	}

	public static void main(String[] args) {
		launch(args);
	}
}