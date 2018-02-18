package network;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import data.QueryDataDto;
import data.QueryDataRetriever;
import data.ResponseDataDto;

/**
 * Encargado de realizar el procesamiento de los request con los datos de
 * entrada necesarios.
 * 
 * @author jelopezta
 *
 */
public class RequestProcessor {

	private final short MAX_POOLED_THREADS = 10;

	private final long MAX_WAIT_TIME = 20;

	QueryDataRetriever dataRetriever;

	private ResponseCollector responseCollector;

	public RequestProcessor() {
		this.dataRetriever = new QueryDataRetriever();
		this.responseCollector = new ResponseCollector();
	}

	public RequestProcessor(QueryDataRetriever dataRetriever) {
		this.dataRetriever = dataRetriever;
		this.responseCollector = new ResponseCollector();
	}

	public RequestProcessor(ResponseCollector responseCollector) {
		this.dataRetriever = new QueryDataRetriever();
		this.responseCollector = responseCollector;
	}

	public ArrayList<ResponseDataDto> processRequestData() {
		ArrayList<QueryDataDto> queryDataList = dataRetriever.getQueryDataList();

		ExecutorService executor = Executors.newFixedThreadPool(MAX_POOLED_THREADS);
		for (QueryDataDto dto : queryDataList) {
			executor.execute(new RequestAgent(responseCollector, dto));
		}

		executor.shutdown();

		try {
			executor.awaitTermination(MAX_WAIT_TIME, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			executor.shutdownNow();
			throw new RuntimeException("Ocurrió un error al intentar realizar las consultas de los procesos", e);
		}

		return responseCollector.getResponseDataDtoList();

	}
}
