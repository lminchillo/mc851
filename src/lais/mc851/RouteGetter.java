package lais.mc851;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RouteGetter
{
	public RouteGetter()
	{
		String[] origin = {"Rua Alcides Soares Cunha","240","Cidade Universitaria"};
		String[] destination = {"Rua Luiz Vicentim","100","Barao Geraldo"};
		
		System.out.println(getData(origin, destination)[1]);
	}
	
	public static String[] getData(String[] origin, String[] destination)
	{
		String[] data = new String[2];
		String busInfo="", initializeInfo="";
		
		String url = setFirstUrl(origin, destination);
		String[][] connectionAux = connectToFirstUrl(url);
		origin[0] = connectionAux[0][1];
		origin[2] = connectionAux[0][2];
		destination[0] = connectionAux[1][1];
		destination[2] = connectionAux[1][2];
		url = setSecondUrl(connectionAux[0][0], connectionAux[1][0], origin, destination);
		url = connectToSecondUrl(url);
		initializeInfo = connectToThirdUrl(url);
		
		data[0] = busInfo;
		data[1] = initializeInfo;
		return data;
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
	
	private static String[][] connectToFirstUrl(String url)
	{
		String[][] data = new String[2][];
		data[0] = new String[3];
		data[1] = new String[3];
		
		try
		{
			Document doc = Jsoup.connect(url).get();
			System.out.println("111111111111111111111111111111111111111111111111111111\n"+doc);
			
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
				//Endereço de origem não encontrado
				System.out.println("Parser: endereço de origem não encontrado");
			}
			
			if (data[1][0] == null)
			{
				//Endereço de destino não encontrado
				System.out.println("Parser: endereço de destino não encontrado");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parser: erro ao pegar dados da primeira url");
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
	
	private static String connectToSecondUrl(String url)
	{
		String data="";
		
		try
		{
			Document doc = Jsoup.connect(url).post();
			System.out.println("222222222222222222222222222222222222222222222222222222\n"+doc);
			
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
				System.out.println("Parser: link para função initialize não encontrado");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parser: erro ao procurar link para função initialize");
		}
		
		return data;
	}
	
	private static String connectToThirdUrl(String url)
	{
		String initialize = "";
		
		try
		{
			Document doc = Jsoup.connect(url).get();
			System.out.println("333333333333333333333333333333333333333333333333333333\n"+doc);
			
			initialize = doc.html();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Parser: erro ao pegar função initialize");
		}
		
		return initialize;
	}
}
