package com.computer_database.userinterface;

import java.io.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.computer_database.model.Company;
import com.computer_database.model.Computer;
import com.computer_database.model.Computer.ComputerBuilder;
import com.computer_database.model.Page;
import com.computer_database.service.CompanyService;
import com.computer_database.service.ComputerService;

public class interfaceMenu {

	private final static ComputerService COMPUTER_SERVICE = new ComputerService();
	private final static CompanyService COMPANY_SERVICE = new CompanyService();
	private final static ComputerBuilder COMPUTER_BUILDER = new ComputerBuilder();

	public static void main(String[] args) throws IOException, ParseException {
		switchMenu();
	}

	public static void switchMenu() throws IOException, ParseException {
		String userChoice, choicePage;
		do {
			userChoice = askUser(
					"Menu :\n\t1) List computers\n\t2) List companies\n\t3) Show computer details\n\t4) Create a computer\n\t5) Update a computer\n\t6) Delete a computer\n\t7) By Pages\n\t8) Quit");
			switch (userChoice) {
			case "1": // List computers
				System.out.println(COMPUTER_SERVICE.listComputers());
				break;
			case "2": // List companies
				System.out.println(COMPANY_SERVICE.listCompanies());
				break;
			case "3": // Show computer details (info of one specific computer)
				// VALIDATION : is userChoice an integer ?
				do {
					userChoice = askUser("Enter computer ID");
				} while (!validateStringToInt(userChoice));

				System.out.println((COMPUTER_SERVICE.read(Integer.parseInt(userChoice)) != null)
						? (COMPUTER_SERVICE.read(Integer.parseInt(userChoice))) : ("Unknown ID"));
				break;
			case "4": // Create a computer
				createComputer();
				break;
			case "5": // Update a computer
				do {
					userChoice = askUser("Enter id of computer to update");
				} while (!validateStringToInt(userChoice));
				updateComputer(Integer.parseInt(userChoice));
				break;
			case "6": // Delete a computer
				do {
					userChoice = askUser("Enter id of the computer to delete");
				} while (!validateStringToInt(userChoice));
				deleteComputer(Integer.parseInt(userChoice));
				break;
			case "7": // Pages
				do {
					userChoice = askUser("Enter the number of elements per page wanted");
				} while (!validateStringToInt(userChoice));
				do {
					choicePage = askUser("Enter the number of the page to display");
				} while (!validateStringToInt(choicePage));
				Page p = new Page();
				p.setNbElementsPerPage(Integer.parseInt(userChoice));
				p.setPageNumber(Integer.parseInt(choicePage));
				System.out.println(p);
				showPage(p);
				break;
			case "8": // Quit
				printString("Bubye :)");
				break;
			default:
				printString("try again, stupid");
				break;
			}
		} while (!userChoice.equals("8"));
	}

	public static boolean validateStringToInt(String userChoice) {
		try {
			Integer.parseInt(userChoice);
		} catch (NumberFormatException e) {
			printString("You must enter a number..");
			return false;
		}
		if (Integer.parseInt(userChoice) <= 0) {
			printString("Number must be > 0");
			return false;
		}
		return true;
	}

	public static void createComputer() {
		COMPUTER_SERVICE.create(askComputerDetails());
	}

	public static void deleteComputer(long id) {
		COMPUTER_SERVICE.delete(id);
	}

	public static void showPage(Page p) {
		System.out.println((COMPUTER_SERVICE.readPages(p).isEmpty()) ? "You reached the end of the database"
				: COMPUTER_SERVICE.readPages(p));
		pagesMenu(p);
	}

	public static void pagesMenu(Page p) {
		String newNumber;
		String userChoice = askUser(
				"Choose an option: \n\tp- for previous page, \n\tn- next page, \n\to- other page, \n\tc- change nb of elements, \n\tq- quit");
		switch (userChoice) {
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

	public static void updateComputer(long userChoice) {
		COMPUTER_SERVICE.update(userChoice, askComputerDetails());
	}

	public static Computer askComputerDetails() {
		String choiceName, choiceCompany, choiceIntroDate, choiceDiscoDate;
		LocalDate intro, disco;
		do {
			choiceName = askUser("Enter name (mandatory)");
		} while (choiceName.equals(""));
		// lister les companies ?
		System.out.println(COMPANY_SERVICE.listCompanies());
		choiceCompany = askUser("Choose a company from the previous list.");
		Company c = COMPANY_SERVICE.readOne(Integer.parseInt(choiceCompany));
		choiceIntroDate = askUser("Enter introduction date. [dd/mm/yyyy] t) for today's date");
		intro = formatDate(choiceIntroDate);
		choiceDiscoDate = askUser("Enter discontinution date. [dd/mm/yyyy] t) for today's date");
		disco = formatDate(choiceDiscoDate);

		return COMPUTER_BUILDER.name(choiceName).company(c).introducedDate(intro).discontinuedDate(disco).build();
	}

	public static String askUser(String question) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		printString(question);
		String result = null;
		try {
			do {
				result = reader.readLine();
				if (result == null || result.isEmpty()) {
					System.out.println("Invalid input.");
				}
			} while (result == null || result.isEmpty());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void printString(String toPrint) {
		System.out.println(toPrint);
	}

	public static LocalDate formatDate(String date) {
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
