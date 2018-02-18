package presentation;

import java.util.ArrayList;
import java.util.Scanner;

import data.ResponseDataDto;
import network.RequestProcessor;
import network.ResponseCollector;

public class MainRunnerPreviousDays {
	public static void main(String[] args) {
		int daysBefore = 0;
		String mensajeInicio = "Por favor ingrese el número de días que quiere restar a"
				+ " la fecha actual para realizar la consulta de procesos";
		System.out.println(mensajeInicio);
		try (Scanner s = new Scanner(System.in);) {
			String str = s.nextLine();
			daysBefore = Integer.parseInt(str);
		}
		final ResponseCollector responseCollector = new ResponseCollector(daysBefore);
		RequestProcessor processor = new RequestProcessor(responseCollector);
		ArrayList<ResponseDataDto> resultList = processor.processRequestData();
		ResultsPrinter printer = new ResultsPrinter(resultList);
		printer.printToHtml();
		System.out.println("A terminado la recolección de los datos de los procesos");
		System.exit(0);
	}
}
