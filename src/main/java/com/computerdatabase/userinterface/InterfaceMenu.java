package main.java.com.computerdatabase.userinterface;

import java.io.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import main.java.com.computerdatabase.model.Company;
import main.java.com.computerdatabase.model.Computer;
import main.java.com.computerdatabase.model.Page;
import main.java.com.computerdatabase.model.Computer.ComputerBuilder;
import main.java.com.computerdatabase.service.CompanyService;
import main.java.com.computerdatabase.service.ComputerService;

public class InterfaceMenu {

	private final static ComputerService COMPUTER_SERVICE = new ComputerService();
	private final static CompanyService COMPANY_SERVICE = new CompanyService();
	private final static ComputerBuilder COMPUTER_BUILDER = new ComputerBuilder();

	/**
	 * switchMenu - main menu of the program
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public void switchMenu() throws IOException, ParseException {
		String userInput, userInputPage;
		do {
			userInput = askUser(
					"Menu :\n\t1) List computers\n\t2) List companies\n\t3) Show computer details\n\t4) Create a computer\n\t5) Update a computer\n\t6) Delete a computer\n\t7) By Pages\n\t8) Quit");
			switch (userInput) {
			case "1": // List computers
				System.out.println(COMPUTER_SERVICE.listComputers());
				break;
			case "2": // List companies
				System.out.println(COMPANY_SERVICE.listCompanies());
				break;
			case "3": // Show computer details (info of one specific computer)
				// VALIDATION : is userChoice an integer ?
				do {
					userInput = askUser("Enter computer ID");
				} while (!validateStringToInt(userInput));

				System.out.println((COMPUTER_SERVICE.readOne(Integer.parseInt(userInput)) != null)
						? (COMPUTER_SERVICE.readOne(Integer.parseInt(userInput))) : ("Unknown ID"));
				break;
			case "4": // Create a computer
				createComputer();
				break;
			case "5": // Update a computer
				do {
					userInput = askUser("Enter id of computer to update");
				} while (!validateStringToInt(userInput));
				updateComputer(Integer.parseInt(userInput));
				break;
			case "6": // Delete a computer
				do {
					userInput = askUser("Enter id of the computer to delete");
				} while (!validateStringToInt(userInput));
				deleteComputer(Integer.parseInt(userInput));
				break;
			case "7": // Pages
				do {
					userInput = askUser("Enter the number of elements per page wanted");
				} while (!validateStringToInt(userInput));
				do {
					userInputPage = askUser("Enter the number of the page to display");
				} while (!validateStringToInt(userInputPage));
				Page p = new Page();
				p.setNbElementsPerPage(Integer.parseInt(userInput));
				p.setPageNumber(Integer.parseInt(userInputPage));
				System.out.println(p);
				showPage(p);
				break;
			case "8": // Quit
				System.out.println("Bubye :)");
				break;
			default:
				System.out.println("try again, stupid");
				break;
			}
		} while (!userInput.equals("8"));
	}

	public void createComputer() {
		COMPUTER_SERVICE.create(askComputerDetails());
	}

	public void deleteComputer(long id) {
		COMPUTER_SERVICE.delete(id);
	}

	/**
	 * showPage
	 * 
	 * @param Page
	 */
	public void showPage(Page p) {
		System.out.println((COMPUTER_SERVICE.readPages(p).isEmpty()) ? "You reached the end of the database"
				: COMPUTER_SERVICE.readPages(p));
		pagesMenu(p);
	}

	/**
	 * pagesMenu - pageable feature
	 * 
	 * @param Page
	 */
	public void pagesMenu(Page p) {
		String newNumber;
		String userInput = askUser(
				"Choose an option: \n\tp- for previous page, \n\tn- next page, \n\to- other page, \n\tc- change nb of elements, \n\tq- quit");
		switch (userInput) {
		case "p":
			if (p.getPageNumber() > 1) {
				p.setPageNumber(p.getPageNumber() - 1);
				showPage(p);
			} else {
				System.out.println("You are already at the first page");
				p.setPageNumber(0);
				pagesMenu(p);
			}
			break;
		case "n":
			p.setPageNumber(p.getPageNumber() + 1);
			showPage(p);
			break;
		case "o":
			do {
				newNumber = askUser("Type the number of the page to show.");
			} while (!validateStringToInt(newNumber));

			long pageNumber = Integer.parseInt(newNumber);
			p.setPageNumber(pageNumber);
			showPage(p);
			break;
		case "c":
			do {
				newNumber = askUser("Type the number of elements per page wanted.");
			} while (!validateStringToInt(newNumber));
			int elementsNb = Integer.parseInt(newNumber);
			p.setNbElementsPerPage(elementsNb);
			showPage(p);
			break;
		case "q":
			System.out.println("the end.");
			break;
		default:
			System.out.println("Wrong character. Try again");
			pagesMenu(p);
			break;
		}
	}

	/**
	 * validateStringToInt - validate user input
	 * 
	 * @param String
	 *            userInput
	 * @return boolean
	 */
	public boolean validateStringToInt(String userInput) {
		try {
			Integer.parseInt(userInput);
		} catch (NumberFormatException e) {
			System.out.println("You must enter a number..");
			return false;
		}
		if (Integer.parseInt(userInput) <= 0) {
			System.out.println("Number must be > 0");
			return false;
		}
		return true;
	}

	public void updateComputer(long userChoice) {
		COMPUTER_SERVICE.update(userChoice, askComputerDetails());
	}

	/**
	 * askComputerDetails - creates new Computer with user inputs
	 * 
	 * @return Computer
	 */
	public Computer askComputerDetails() {
		String choiceName, choiceCompany, choiceIntroDate, choiceDiscoDate;
		LocalDate intro, disco;
		do {
			choiceName = askUser("Enter name (mandatory)");
		} while (choiceName.equals(""));
		System.out.println(COMPANY_SERVICE.listCompanies());
		choiceCompany = askUser("Choose a company from the previous list.");
		Company c = COMPANY_SERVICE.readOne(Integer.parseInt(choiceCompany));
		choiceIntroDate = askUser("Enter introduction date. [dd/mm/yyyy] t) for today's date");
		intro = formatDate(choiceIntroDate);
		choiceDiscoDate = askUser("Enter discontinution date. [dd/mm/yyyy] t) for today's date");
		disco = formatDate(choiceDiscoDate);

		return COMPUTER_BUILDER.name(choiceName).company(c).introducedDate(intro).discontinuedDate(disco).build();
	}

	/**
	 * askUser - get user input
	 * 
	 * @param String
	 *            question
	 * @return userInput
	 */
	public String askUser(String question) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(question);
		String userInput = null;
		try {
			do {
				userInput = reader.readLine();
				if (userInput == null || userInput.isEmpty()) {
					System.out.println("Invalid input.");
				}
			} while (userInput == null || userInput.isEmpty());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInput;
	}

	/**
	 * formatDate
	 * 
	 * @param String
	 *            date
	 * @return LocalDate
	 */
	public LocalDate formatDate(String date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateResult;
		if (date.equals("t")) {
			dateResult = LocalDate.now();
		} else if (date.equals("")) {
			dateResult = null;
		} else {
			dateResult = LocalDate.parse(date, format);
		}
		return dateResult;
	}

}
