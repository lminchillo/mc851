package lais.mc851;

import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.AsyncTask;

public class RouteGetter
{
	public RouteGetter()
	{
		
	}
	
	public static String getData(String[] origin, String[] destination)
	{
		String initializeInfo="";
		
		// Primeira parte
		String url = setFirstUrl(origin, destination);
		String[][] connectionAux = connectToFirstUrl(url);
		if (connectionAux[0][1].length()<1 || connectionAux[1][1].length()<1)
		{
			System.out.println("Primeira conexao - tentando novamente");
			connectionAux = connectToFirstUrl(url);
		}
		if (connectionAux[0][1].length()<1 || connectionAux[1][1].length()<1)
		{
			System.out.println("Primeira conexao - erro");
			return null;
		}
		
		origin[0] = connectionAux[0][1];
		origin[2] = connectionAux[0][2];
		destination[0] = connectionAux[1][1];
		destination[2] = connectionAux[1][2];
		
		//Segunda parte
		url = setSecondUrl(connectionAux[0][0], connectionAux[1][0], origin, destination);
		String aux = connectToSecondUrl(url);
		if (aux.length()<1)
		{
			System.out.println("Segunda conexao - tentando novamente");
			aux = connectToSecondUrl(url);
		}
		if (aux.length()<1)
		{
			System.out.println("Segunda conexao - erro");
			return null;
		}
		url = aux;
		
		//Terceira parte
		initializeInfo = connectToThirdUrl(url);
		if (initializeInfo.length()<1)
		{
			System.out.println("Terceira conexao - tentando novamente");
			initializeInfo = connectToThirdUrl(url);
		}
		if (initializeInfo.length()<1)
		{
			System.out.println("Terceira conexao - tentando novamente");
			return null;
		}
		return initializeInfo;
	}
	
	private static String setFirstUrl(String[] origin, String[] destination)
	{
		String res = "";
		res += "http://www.emdec.com.br/ABusInf/index.asp?Consulta=1";
		res += "&TIPO_LOCAL_ORIG=-1";
		res += "&LOCAL_ORIG="+origin[0].replace(" ", "%20");
		res += "&NUM_ORIG="+origin[1];
		res += "&BAIRRO_ORIG="+origin[2].replace(" ", "%20");
		res += "&CRUZ_ORIG=";
		res += "&TIPO_LOCAL_DEST=-1";
		res += "&LOCAL_DEST="+destination[0].replace(" ", "%20");
		res += "&NUM_DEST="+destination[1];
		res += "&BAIRRO_DEST="+destination[2].replace(" ", "%20");
		res += "&CRUZ_DEST=";
		res += "&COD_TIPO_DIA=0";
		res += "&OPC_HORARIO=12";
		res += "&OPC_MAX_PE=600";
		res += "&PontosNotOrig=0&PontosNotDest=0";
		res += "&LtOpcTipoDia=0";
		res += "&LtOpcHorario=12";
		res += "&OpcPPD=0";
		return res;
	}
	
	private static String[][] connectToFirstUrl(final String url)
	{
		String[][] data = new String[2][];
		data[0] = new String[3];
		data[1] = new String[3];
		
		try
		{
			AsyncTask<Void, Void, Document> task = new AsyncTask<Void, Void, Document>()
			{
				@Override
				protected Document doInBackground(Void... params)
				{
					try
					{
						Document doc = Jsoup.connect(url).get();
						return doc;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						System.out.println("Parser: erro ao pegar dados da primeira url - "+e.getMessage());
						return null;
					}
				}
		
			}.execute();
			
			Document doc = task.get(5000, TimeUnit.MILLISECONDS);
			
			for (Element e:doc.getElementsByTag("input"))
			{
				if (e.toString().contains("<input type=\"hidden\" id=\"DadosOrig_1\" value="))
				{
					String aux = e.attr("value");
					
					data[0][0] = aux.split(";")[0]; //origemId
					data[0][1] = aux.split(";")[1]; //origemRua
					data[0][2] = aux.split(";")[2]; //origemBairro
				}
				
				if (e.toString().contains("<input type=\"hidden\" id=\"DadosDest_1\" value="))
				{
					String aux = e.attr("value");

					data[1][0] = aux.split(";")[0]; //origemId
					data[1][1] = aux.split(";")[1]; //origemRua
					data[1][2] = aux.split(";")[2]; //origemBairro
				}
			}
			
			if (data[0][0] == null)
			{
				//Endere�o de origem n�o encontrado
				System.out.println("Parser: endere�o de origem n�o encontrado");
			}
			
			if (data[1][0] == null)
			{
				//Endere�o de destino n�o encontrado
				System.out.println("Parser: endere�o de destino n�o encontrado");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parser: erro ao pegar dados da primeira url - "+e.getMessage());
		}
		
		return data;
	}
	
	private static String setSecondUrl(String originId, String destinationId, String[] origin, String[] destination)
	{
		String res = "";
		res += "http://www.emdec.com.br/ABusInf/index.asp?";
		res += "GfNosIDORIG="+originId;
		res += "&GfNosIDDEST="+destinationId;
		res += "&OPC_MAX_PE=600";
		res += "&COD_TIPO_DIA=0";
		res += "&OPC_HORARIO=12";
		res += "&OpcPPD=0";
		res += "&CodlogOrig=&CodlogDest=";
		res += "&LtOpcHorario=12";
		res += "&PontosNotOrig=0&PontosNotDest=0";
		res += "&TIPO_LOCAL_ORIG=-1&TIPO_LOCAL_DEST=-1";
		res += "&CdNotIDOrigem=undefined&CdNotIDDestino=undefined";
		res += "&LOCAL_ORIG="+origin[0].replace(" ","%20");
		res += "&LOCAL_DEST="+destination[0].replace(" ","%20");
		res += "&NumOrigem="+origin[1];
		res += "&NumDestino="+destination[1];
		res += "&BAIRRO_ORIG="+origin[2].replace(" ","%20");
		res += "&BAIRRO_DEST="+destination[2].replace(" ","%20");
		res += "&Mapa=1";
		
		return res;
	}
	
	private static String connectToSecondUrl(final String url)
	{
		String data = "";
		
		try
		{
			AsyncTask<Void, Void, Document> task = new AsyncTask<Void, Void, Document>()
			{
				@Override
				protected Document doInBackground(Void... params)
				{
					try
					{
						Document doc = Jsoup.connect(url).get();
						return doc;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						System.out.println("Parser: erro ao procurar link para fun��o initialize - "+e.getMessage());
						return null;
					}
				}
		
			}.execute();
			
			Document doc = task.get(5000, TimeUnit.MILLISECONDS);
			
			if (doc.html().contains("$(\"#mapFrame\").attr(\"src\", "))
			{
				String link = doc.html().substring(doc.html().indexOf("$(\"#mapFrame\").attr(\"src\", ")).split("\n")[0];
				if (link.contains("\");"))
				{
					link = link.replace("\");","");
				}
				link = link.substring(28);
				link = link.replace(" ", "%20");
				link = "http://www.emdec.com.br/ABusInf/" + link;

				data = link;
			}
			else
			{
				System.out.println("Parser: link para fun��o initialize n�o encontrado");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parser: erro ao procurar link para fun��o initialize - "+e.getMessage());
		}
		
		return data;
	}
	
	private static String connectToThirdUrl(final String url)
	{
		String initialize = "";
		
		try
		{
			AsyncTask<Void, Void, Document> task = new AsyncTask<Void, Void, Document>()
			{
				@Override
				protected Document doInBackground(Void... params)
				{
					try
					{
						Document doc = Jsoup.connect(url).get();
						return doc;
					}
					catch (Exception e)
					{
						e.printStackTrace();
						System.out.println("Parser: erro ao pegar fun��o initialize - "+e.getMessage());
						return null;
					}
				}
		
			}.execute();
			
			Document doc = task.get(5000, TimeUnit.MILLISECONDS);
			
			initialize = doc.html();
			if (initialize.contains("<html>"))
			{
				initialize = initialize.substring(initialize.indexOf("function initialize()"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parser: erro ao pegar fun��o initialize - "+e.getMessage());
		}
		
		return initialize;
	}
}
