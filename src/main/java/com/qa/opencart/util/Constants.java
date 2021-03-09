package com.qa.opencart.util;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public static final String LOGIN_PAGE_TITLE="Account Login";
	public static final String ACCOUNT_PAGE_TITLE="My Account";
	public static final int ACC_PAGE_SECTIONS_COUNT=5;
	public static final String ACC_CREATION_SUCCESS_MESSG="Your Account Has Been Created!";
	public static final String ADD_TO_CART_SUCCESS_MESSG="Success: You have added MacBook Pro to your shopping cart!";
	
	public static final int PRODUCT_IMAGES_COUNT_MACBOOK=4;
	
	/********Sheet Name************/
	
	public static final String REGISTER_SHEET_NAME ="register";
	
	public static List<String>  expectedAccSecList() {
		 List<String> expEcList = new ArrayList<String>();
		 expEcList.add("My Account");
		 expEcList.add("My Orders");
		 expEcList.add("My Affiliate Account");
		 expEcList.add("Newsletter");
		 return expEcList;
	}

}
