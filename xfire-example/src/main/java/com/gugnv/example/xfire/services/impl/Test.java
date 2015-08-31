package com.gugnv.example.xfire.services.impl;

import com.gugnv.example.xfire.services.ITest;

public class Test implements ITest {

	public String testWs() {

		return "hello! my test";
	}

	public String GetFlashofDate(String name, String password, String inputvalue, String startRow, String endRow) {
		return "----" + inputvalue + "---" + startRow + "---" + endRow + "---";
	}

}
