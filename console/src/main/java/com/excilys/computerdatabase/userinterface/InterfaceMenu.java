package com.excilys.computerdatabase.userinterface;

import java.io.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

public class InterfaceMenu {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Console-Module.xml");
    private final static Logger logger = LoggerFactory.getLogger(ComputerDao.class);
    private static ComputerService computerService = context.getBean(ComputerService.class);
    private static CompanyService companyService = context.getBean(CompanyService.class);
    private static ComputerCliService computerCliService = context.getBean(ComputerCliService.class);
    private static CompanyCliService companyCliService = context.getBean(CompanyCliService.class);
    private final static ComputerBuilder COMPUTER_BUILDER = new ComputerBuilder();

    public static void main(String args[]) {
        try {
            switchMenu();
        } catch (IOException e) {
            logger.error("InterfaceMenu : main() catched IOException", e);
        } catch (ParseException e) {
            logger.error("InterfaceMenu : main() catched ParseException", e);
        }
    }

    /**
     * switchMenu - main menu of the program
     * 
     * @throws IOException
     * @throws ParseException
     */
    public static void switchMenu() throws IOException, ParseException {
        String userInput, userInputPage;
        do {
            userInput = askUser(
                    "\nMenu :\n\t1) List computers\n\t2) List companies\n\t3) Show computer details\n\t4) Create a computer\n\t5) Update a computer\n\t6) Delete a computer\n\t7) Delete a company\n\t8) By Pages\n\t9) Quit\n");
            switch (userInput) {
            case "1": // List computers
                System.out.println(computerCliService.listEntities(null));
                break;
            case "2": // List companies
                System.out.println(companyCliService.listEntities(null));

                break;
            case "3": // Show computer details (info of one specific computer)
                // VALIDATION : is userChoice an integer ?
                do {
                    userInput = askUser("Enter computer ID");
                } while (!validateStringToInt(userInput));

                Computer c = computerCliService.getComputerId(Integer.parseInt(userInput));
                System.out.println((c != null) ? (c) : ("Unknown ID ¯\\_(ツ)_/¯"));
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
            case "7": // Delete a company & all computers from that company
                do {
                    userInput = askUser("Enter id of the company to delete");
                } while (!validateStringToInt(userInput));
                deleteCompany(Integer.parseInt(userInput));
                break;
            case "8": // Pages
                do {
                    userInput = askUser("Enter the number of elements per page wanted");
                } while (!validateStringToInt(userInput));
                do {
                    userInputPage = askUser("Enter the number of the page to display");
                } while (!validateStringToInt(userInputPage));
                Page p = new Page();
                p.setPageSize(Integer.parseInt(userInput));
                p.setCurrentPage(Integer.parseInt(userInputPage));
                System.out.println(p.toString());
                showPage(p);
                break;
            case "9": // Quit
                System.out.println("Bubye :)");
                break;
            default:
                System.out.println("try again, stupid");
                break;
            }
        } while (!userInput.equals("9"));
    }

    public static void createComputer() {
        computerCliService.createComputer(askComputerDetails());
    }
    
    public static void updateComputer(long userChoice) {
        computerCliService.update(userChoice, askComputerDetails());
    }

    public static void deleteComputer(long id) {
        computerCliService.delete(id);
    }

    public static void deleteCompany(long id) {
        companyCliService.delete(id);
    }

    /**
     * showPage
     * 
     * @param Page
     */
    public static void showPage(Page p) {
        System.out.println((computerService.readPages(p, null).isEmpty()) ? "You reached the end of the database"
                : computerService.readPages(p, null).toString());
        pagesMenu(p);
    }

    /**
     * pagesMenu - pageable feature
     * 
     * @param Page
     */
    public static void pagesMenu(Page p) {
        String newNumber;
        String userInput = askUser(
                "\nChoose an option: \n\tp- for previous page, \n\tn- next page, \n\to- other page, \n\tc- change nb of elements, \n\tq- quit\n");
        switch (userInput) {
        case "p":
            if (p.getCurrentPage() > 1) {
                p.setCurrentPage(p.getCurrentPage() - 1);
                showPage(p);
            } else {
                System.out.println("You are already at the first page");
                p.setCurrentPage(0);
                pagesMenu(p);
            }
            break;
        case "n":
            p.setCurrentPage(p.getCurrentPage() + 1);
            showPage(p);
            break;
        case "o":
            do {
                newNumber = askUser("Type the number of the page to show.");
            } while (!validateStringToInt(newNumber));

            int pageNumber = Integer.parseInt(newNumber);
            p.setCurrentPage(pageNumber);
            showPage(p);
            break;
        case "c":
            do {
                newNumber = askUser("Type the number of elements per page wanted.");
            } while (!validateStringToInt(newNumber));
            int elementsNb = Integer.parseInt(newNumber);
            p.setPageSize(elementsNb);
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
    public static boolean validateStringToInt(String userInput) {
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


    /**
     * askComputerDetails - creates new Computer with user inputs
     * 
     * @return Computer
     */
    public static Computer askComputerDetails() {
        String choiceName, choiceCompany, choiceIntroDate, choiceDiscoDate;
        LocalDate intro, disco;
        do {
            choiceName = askUser("Enter name (mandatory)");
        } while (choiceName.equals(""));
        System.out.println(companyService.listEntities(null).toString());
        choiceCompany = askUser("Choose a company from the previous list.");
        Company c = companyService.readOne(Integer.parseInt(choiceCompany));
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
    public static String askUser(String question) {
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
            logger.error("${enclosing_type} : ${enclosing_method}() catched ${exception_type}", e);
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
