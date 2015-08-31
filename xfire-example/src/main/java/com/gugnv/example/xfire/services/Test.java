package com.gugnv.example.xfire.services;

public class Test implements ITest {

	public String testWs() {

		return "hello! my test";
	}

	public String GetFlashofDate(String name, String password,
			String inputvalue, String startRow, String endRow) {
		return "----" + inputvalue + "---" + startRow + "---" + endRow + "---";
	}

	public String downloadCAGCtrl(String xml) {
		String returnValue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<response>" + "<result code=\"2\">"
				+ "<error errorcode=\"0201002\">"
				+ "<attr name=\"CAC_ID\" value=\"22M00000022376640\"/>"
				+ "<debugcommandresponse>调试返回信息</debugcommandresponse>"
				+ "</error>" + "</result>" + "</response>";
		
		
		returnValue="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+"<response><result code=\"1\"><error errorcode=\"0201001\"><attr name=\"CAC_ID\" value=\"29M000000122645261\"/></error></result></response>";
		
		
		returnValue="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+"<response><result code=\"0\"/></response>";
		return returnValue;
	}

}
