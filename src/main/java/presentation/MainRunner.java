package presentation;

import java.util.ArrayList;

import data.ResponseDataDto;
import network.RequestProcessor;

public class MainRunner {
	public static void main(String[] args) {
		RequestProcessor processor = new RequestProcessor();
		ArrayList<ResponseDataDto> resultList = processor.processRequestData();
		ResultsPrinter printer = new ResultsPrinter(resultList);
		printer.printToHtml();
		System.out.println("A terminado la recolección de los datos de los procesos");
		System.exit(0);
	}
}
