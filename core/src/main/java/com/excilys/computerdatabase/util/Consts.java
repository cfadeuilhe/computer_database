package com.excilys.computerdatabase.util;

public final class Consts {

    /*
     * Basic Consts for attributes
     * 
     * */
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String INTRODUCED_DATE = "introduced";
    public static final String DISCONTINUED_DATE = "discontinued";
    public static final String SEARCH = "search";
    public static final String PAGE = "page";
    public static final String LIMIT = "limit";
    public static final String COMPUTER_NAME = "computerName";
    public static final String COMPANY_ID = "companyId";
    public static final String COMPANY_LIST = "companyList";
    public static final String SELECTION = "selection";
    public static final String COUNT = "count";
    
    public static final String COMPANY_ID_DB = "company_id";
    public static final String COMPANY_NAME_DB = "company.name";
    
    /*
     * Errors
     * 
     * */
    public static final String METHOD_NOT_IMPLEMENTED = "Method create() is not implemented";
    public static final String NEGATIVE_ID = "NegativeId";
    public static final String ID_NOT_A_NUMBER = "IdNotANumber";
    public static final String NO_NAME = "NoName";
    public static final String NAME_TOO_SHORT = "NameTooShort";
    public static final String DATE_ORDER_ERROR = "DateOrderError";
    public static final String DATE_INTRO_FORMAT_ERROR = "DateIntroFormatError";
    public static final String DATE_DISCO_FORMAT_ERROR = "DateDiscoFormatError";
    

    private Consts(){
      //prevents the creation of Consts objects with a private Constructor
      throw new AssertionError();
    }
}
