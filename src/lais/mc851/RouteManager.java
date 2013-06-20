package lais.mc851;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class RouteManager
{
	private static SharedPreferences pref = null;
	private static Editor editor = null;
	private static final String tag = "savedRoute";
	private static final String tagNext = "next";
	private static final String tagEnd = "end";
	
	public RouteManager(SharedPreferences sp, Editor e)
	{
		pref = sp;
		editor = e;
		
		addRoutes();
	}
	
	private void addRoutes()
	{

		
		/*RouteManager.addRoute("Casa/Trabalho", "" +
				" " + //routeSourceValue
				" " + //routeSourceLatLng
				" " + //routeDestValue
				" " + //routeDestLatLng
				" "); //initialize*/
		
		
		try
		{
			List<String> aux = GPSManager.getLocation("Rua Alcides Soares Cunha, 240");
			String address1 = aux.get(0);
			String address1latlng = aux.get(1);
			aux = GPSManager.getLocation("Rua Doutor Lauro Pimentel, 433");
			String address2 = aux.get(0);
			String address2latlng = aux.get(1);
			
			String initialize = "function initialize() " +
					"\n{" +
					"\n				" +
					"\n  var myLatLng = new google.maps.LatLng(-22.8172335, -47.0697445);" +
					"\n  var myOptions = {      zoom: 12,     center: myLatLng,     mapTypeId: google.maps.MapTypeId.ROADMAP  };   " +  
					"\n  var map = new google.maps.Map(document.getElementById(\"map_canvas\"), myOptions);" +
					"\n  var imagemA = 'imagens/A.png'; " +
					"\n  var imagemB = 'imagens/B.png'; " +
					"\n  var ImagemOnibus = 'imagens/bus.png';" +
					"\n  " +
					"\n  var l_1 = [  ];var c_1 = new google.maps.Polyline({path: l_1, strokeColor: '#000000', strokeOpacity: 0.5, clickable: false, strokeWeight: 4    });   c_1.setMap(map);var l_2 = [ new google.maps.LatLng(-22.824483,-47.067802), new google.maps.LatLng(-22.825602,-47.066753), new google.maps.LatLng(-22.825536,-47.066627), new google.maps.LatLng(-22.825722,-47.066514), new google.maps.LatLng(-22.825786,-47.066506), new google.maps.LatLng(-22.825855,-47.066533), new google.maps.LatLng(-22.826126,-47.067127), new google.maps.LatLng(-22.826175,-47.0672), new google.maps.LatLng(-22.826198,-47.067241), new google.maps.LatLng(-22.826259,-47.067288), new google.maps.LatLng(-22.826409,-47.067388), new google.maps.LatLng(-22.826503,-47.067443), new google.maps.LatLng(-22.826597,-47.06748), new google.maps.LatLng(-22.826705,-47.067474), new google.maps.LatLng(-22.826729,-47.067547), new google.maps.LatLng(-22.826736,-47.067608), new google.maps.LatLng(-22.826721,-47.067684), new google.maps.LatLng(-22.826903,-47.067669), new google.maps.LatLng(-22.827063,-47.06764), new google.maps.LatLng(-22.827195,-47.067588), new google.maps.LatLng(-22.82735,-47.06752), new google.maps.LatLng(-22.827484,-47.067417), new google.maps.LatLng(-22.827664,-47.06726), new google.maps.LatLng(-22.827993,-47.06697), new google.maps.LatLng(-22.829067,-47.065954), new google.maps.LatLng(-22.829495,-47.065549), new google.maps.LatLng(-22.829692,-47.06535), new google.maps.LatLng(-22.829748,-47.065299), new google.maps.LatLng(-22.829777,-47.065236), new google.maps.LatLng(-22.829784,-47.065186), new google.maps.LatLng(-22.829778,-47.065138), new google.maps.LatLng(-22.829762,-47.065097), new google.maps.LatLng(-22.82974,-47.065056), new google.maps.LatLng(-22.829696,-47.065007), new google.maps.LatLng(-22.829656,-47.064992), new google.maps.LatLng(-22.829606,-47.064984), new google.maps.LatLng(-22.829547,-47.065003), new google.maps.LatLng(-22.82949,-47.065042), new google.maps.LatLng(-22.829453,-47.065092), new google.maps.LatLng(-22.829434,-47.065167), new google.maps.LatLng(-22.829442,-47.065238), new google.maps.LatLng(-22.829462,-47.065283), new google.maps.LatLng(-22.829496,-47.065333), new google.maps.LatLng(-22.829082,-47.065742), new google.maps.LatLng(-22.829001,-47.065575), new google.maps.LatLng(-22.828218,-47.063782), new google.maps.LatLng(-22.828132,-47.063578), new google.maps.LatLng(-22.828024,-47.063328), new google.maps.LatLng(-22.827869,-47.062948), new google.maps.LatLng(-22.827778,-47.06273), new google.maps.LatLng(-22.827641,-47.062772), new google.maps.LatLng(-22.827164,-47.063024), new google.maps.LatLng(-22.827293,-47.063281), new google.maps.LatLng(-22.827515,-47.06381), new google.maps.LatLng(-22.828565,-47.066217), new google.maps.LatLng(-22.828196,-47.066593), new google.maps.LatLng(-22.827625,-47.067115), new google.maps.LatLng(-22.827393,-47.067311), new google.maps.LatLng(-22.827202,-47.067401), new google.maps.LatLng(-22.826995,-47.067436), new google.maps.LatLng(-22.826852,-47.067449), new google.maps.LatLng(-22.826705,-47.067474), new google.maps.LatLng(-22.826597,-47.06748), new google.maps.LatLng(-22.826503,-47.067443), new google.maps.LatLng(-22.826409,-47.067388), new google.maps.LatLng(-22.826259,-47.067288), new google.maps.LatLng(-22.826198,-47.067241), new google.maps.LatLng(-22.826175,-47.0672), new google.maps.LatLng(-22.826126,-47.067127), new google.maps.LatLng(-22.825855,-47.066533), new google.maps.LatLng(-22.82574,-47.066264), new google.maps.LatLng(-22.825435,-47.065574), new google.maps.LatLng(-22.825237,-47.065155), new google.maps.LatLng(-22.825186,-47.065092), new google.maps.LatLng(-22.825121,-47.065076), new google.maps.LatLng(-22.824907,-47.065182), new google.maps.LatLng(-22.825118,-47.065676), new google.maps.LatLng(-22.825226,-47.065956), new google.maps.LatLng(-22.825536,-47.066627), new google.maps.LatLng(-22.825104,-47.067037), new google.maps.LatLng(-22.823955,-47.068129), new google.maps.LatLng(-22.823451,-47.068598), new google.maps.LatLng(-22.823399,-47.068613), new google.maps.LatLng(-22.823349,-47.068633), new google.maps.LatLng(-22.823256,-47.06871), new google.maps.LatLng(-22.822394,-47.069521), new google.maps.LatLng(-22.822352,-47.069461), new google.maps.LatLng(-22.822301,-47.069419), new google.maps.LatLng(-22.82223,-47.069378), new google.maps.LatLng(-22.82212,-47.069354), new google.maps.LatLng(-22.82201,-47.069379), new google.maps.LatLng(-22.821967,-47.069401), new google.maps.LatLng(-22.821931,-47.06943), new google.maps.LatLng(-22.821896,-47.069466), new google.maps.LatLng(-22.821869,-47.069515), new google.maps.LatLng(-22.821848,-47.069572), new google.maps.LatLng(-22.821841,-47.069631), new google.maps.LatLng(-22.821851,-47.069723), new google.maps.LatLng(-22.821886,-47.069806), new google.maps.LatLng(-22.821918,-47.06986), new google.maps.LatLng(-22.820933,-47.070788), new google.maps.LatLng(-22.820863,-47.070852), new google.maps.LatLng(-22.819949,-47.071781), new google.maps.LatLng(-22.819744,-47.071942), new google.maps.LatLng(-22.81954,-47.072066), new google.maps.LatLng(-22.819297,-47.072192), new google.maps.LatLng(-22.819058,-47.072302), new google.maps.LatLng(-22.818879,-47.072362), new google.maps.LatLng(-22.818807,-47.072209), new google.maps.LatLng(-22.818733,-47.07213), new google.maps.LatLng(-22.818661,-47.072077), new google.maps.LatLng(-22.818571,-47.07206), new google.maps.LatLng(-22.818476,-47.072057), new google.maps.LatLng(-22.818343,-47.072093), new google.maps.LatLng(-22.818287,-47.072125), new google.maps.LatLng(-22.8182,-47.07221), new google.maps.LatLng(-22.818171,-47.072271), new google.maps.LatLng(-22.818149,-47.072348), new google.maps.LatLng(-22.818128,-47.072426), new google.maps.LatLng(-22.818128,-47.072488), new google.maps.LatLng(-22.818146,-47.072581), new google.maps.LatLng(-22.818178,-47.07268), new google.maps.LatLng(-22.818229,-47.072757), new google.maps.LatLng(-22.818287,-47.072807), new google.maps.LatLng(-22.818357,-47.072853), new google.maps.LatLng(-22.818713,-47.073246), new google.maps.LatLng(-22.819208,-47.073746), new google.maps.LatLng(-22.819699,-47.074243), new google.maps.LatLng(-22.820193,-47.074743), new google.maps.LatLng(-22.820666,-47.075243), new google.maps.LatLng(-22.821126,-47.075745), new google.maps.LatLng(-22.821406,-47.07603), new google.maps.LatLng(-22.821608,-47.076249), new google.maps.LatLng(-22.822108,-47.076759) ];var c_2 = new google.maps.Polyline({path: l_2, strokeColor: '#FF0000', strokeOpacity: 0.5, clickable: false, strokeWeight: 4    });   c_2.setMap(map);var l_3 = [ new google.maps.LatLng(-22.822108,-47.076759), new google.maps.LatLng(-22.822272,-47.07658) ];var c_3 = new google.maps.Polyline({path: l_3, strokeColor: '#000000', strokeOpacity: 0.5, clickable: false, strokeWeight: 4    });   c_3.setMap(map);var l_4 = [ new google.maps.LatLng(-22.822272,-47.07658), new google.maps.LatLng(-22.821807,-47.076075), new google.maps.LatLng(-22.821585,-47.075859), new google.maps.LatLng(-22.821333,-47.075567), new google.maps.LatLng(-22.820847,-47.075058), new google.maps.LatLng(-22.820369,-47.074554), new google.maps.LatLng(-22.819895,-47.074058), new google.maps.LatLng(-22.819401,-47.073579), new google.maps.LatLng(-22.818896,-47.073065), new google.maps.LatLng(-22.818681,-47.072836), new google.maps.LatLng(-22.818755,-47.072787), new google.maps.LatLng(-22.818824,-47.072699), new google.maps.LatLng(-22.818854,-47.072657), new google.maps.LatLng(-22.818881,-47.072583), new google.maps.LatLng(-22.818892,-47.072502), new google.maps.LatLng(-22.818887,-47.072406), new google.maps.LatLng(-22.818879,-47.072362), new google.maps.LatLng(-22.818807,-47.072209), new google.maps.LatLng(-22.818733,-47.07213), new google.maps.LatLng(-22.818661,-47.072077), new google.maps.LatLng(-22.818571,-47.07206), new google.maps.LatLng(-22.818476,-47.072057), new google.maps.LatLng(-22.818343,-47.072093), new google.maps.LatLng(-22.818287,-47.072125), new google.maps.LatLng(-22.8182,-47.07221), new google.maps.LatLng(-22.818171,-47.072271), new google.maps.LatLng(-22.818149,-47.072348), new google.maps.LatLng(-22.818128,-47.072426), new google.maps.LatLng(-22.818128,-47.072488), new google.maps.LatLng(-22.818146,-47.072581), new google.maps.LatLng(-22.818178,-47.07268), new google.maps.LatLng(-22.818229,-47.072757), new google.maps.LatLng(-22.817879,-47.07291), new google.maps.LatLng(-22.817724,-47.072978), new google.maps.LatLng(-22.817136,-47.073216), new google.maps.LatLng(-22.815668,-47.073818), new google.maps.LatLng(-22.814176,-47.074428), new google.maps.LatLng(-22.814101,-47.074451), new google.maps.LatLng(-22.814032,-47.074453), new google.maps.LatLng(-22.813945,-47.074458), new google.maps.LatLng(-22.813847,-47.074447), new google.maps.LatLng(-22.813694,-47.074417), new google.maps.LatLng(-22.813622,-47.074379), new google.maps.LatLng(-22.813523,-47.074327), new google.maps.LatLng(-22.8134,-47.074235), new google.maps.LatLng(-22.813343,-47.074186), new google.maps.LatLng(-22.813281,-47.07413), new google.maps.LatLng(-22.813186,-47.074012), new google.maps.LatLng(-22.813169,-47.07399), new google.maps.LatLng(-22.813151,-47.073954), new google.maps.LatLng(-22.813129,-47.073909), new google.maps.LatLng(-22.812398,-47.074675), new google.maps.LatLng(-22.811945,-47.07512), new google.maps.LatLng(-22.811502,-47.07557), new google.maps.LatLng(-22.810454,-47.076648), new google.maps.LatLng(-22.810492,-47.076503), new google.maps.LatLng(-22.810353,-47.075731), new google.maps.LatLng(-22.810078,-47.073627), new google.maps.LatLng(-22.810167,-47.073587), new google.maps.LatLng(-22.810243,-47.073529), new google.maps.LatLng(-22.810305,-47.073447), new google.maps.LatLng(-22.810335,-47.073356), new google.maps.LatLng(-22.810344,-47.073253), new google.maps.LatLng(-22.810333,-47.073155), new google.maps.LatLng(-22.810319,-47.073068), new google.maps.LatLng(-22.810294,-47.072999), new google.maps.LatLng(-22.810247,-47.072917), new google.maps.LatLng(-22.810188,-47.072859), new google.maps.LatLng(-22.810066,-47.072787), new google.maps.LatLng(-22.80995,-47.072758), new google.maps.LatLng(-22.809919,-47.072495), new google.maps.LatLng(-22.809813,-47.071788), new google.maps.LatLng(-22.809683,-47.071065), new google.maps.LatLng(-22.809627,-47.070867), new google.maps.LatLng(-22.80958,-47.07073), new google.maps.LatLng(-22.80949,-47.070543), new google.maps.LatLng(-22.809376,-47.070337), new google.maps.LatLng(-22.809293,-47.070233), new google.maps.LatLng(-22.809166,-47.070074), new google.maps.LatLng(-22.809074,-47.06999), new google.maps.LatLng(-22.808996,-47.069908), new google.maps.LatLng(-22.809025,-47.0695), new google.maps.LatLng(-22.809085,-47.06877), new google.maps.LatLng(-22.809104,-47.068052), new google.maps.LatLng(-22.809134,-47.067327), new google.maps.LatLng(-22.809151,-47.066588) ];var c_4 = new google.maps.Polyline({path: l_4, strokeColor: '#00FF00', strokeOpacity: 0.5, clickable: false, strokeWeight: 4    });   c_4.setMap(map);var l_5 = [ new google.maps.LatLng(-22.809151,-47.066588), new google.maps.LatLng(-22.807697,-47.066539), new google.maps.LatLng(-22.806194,-47.06645), new google.maps.LatLng(-22.804724,-47.066374), new google.maps.LatLng(-22.804683,-47.067092) ];var c_5 = new google.maps.Polyline({path: l_5, strokeColor: '#000000', strokeOpacity: 0.5, clickable: false, strokeWeight: 4    });   c_5.setMap(map);var p_1 = new google.maps.LatLng(-22.824483, -47.067802); var o_1 = new google.maps.Marker({position: p_1, icon: imagemA, map:map });  var cc_1 = '<div><table cellpadding=0 cellspacing=0 style=font-size:9pt;><tr><td>R DR  ALCIDES SOARES CUNHA, CIDADE UNIVERSITARIA CAMPINEIR</td></tr></table></div>'; var i_1 = new google.maps.InfoWindow({content: cc_1, maxWidth: 350});  o_1.setMap(map); google.maps.event.addListener(o_1, 'click', function() { i_1.open(map, o_1);});var p_2 = new google.maps.LatLng(-22.804683, -47.067092); var o_2 = new google.maps.Marker({position: p_2, icon: imagemB, map:map });  var cc_2 = '<div><table cellpadding=0 cellspacing=0 style=font-size:9pt;><tr><td>R DR  LAURO PIMENTEL, CIDADE UNIVERSITARIA (BARAO GERALDO)</td></tr></table></div>'; var i_2 = new google.maps.InfoWindow({content: cc_2, maxWidth: 350});  o_2.setMap(map); google.maps.event.addListener(o_2, 'click', function() { i_2.open(map, o_2);});var p_3 = new google.maps.LatLng(-22.824977, -47.067366); var o_3 = new google.maps.Marker({position: p_3, icon: ImagemOnibus, map:map });  var cc_3 = '<div><table cellpadding=0 cellspacing=0 style=font-size:9pt;><tr><td><strong>Linha: </strong>337   UNICAMP/HC-TERMINAL BARAO GERALDO</td></tr><tr><td><strong>Embarque: </strong>R ROXO MOREIRA, ALTURA 1779    REF.: R JOSÉ ANDERSON</td></tr></table></div>'; var i_3 = new google.maps.InfoWindow({content: cc_3, maxWidth: 350});  o_3.setMap(map); google.maps.event.addListener(o_3, 'click', function() { i_3.open(map, o_3);});var p_4 = new google.maps.LatLng(-22.822143, -47.07693); var o_4 = new google.maps.Marker({position: p_4, icon: ImagemOnibus, map:map });  var cc_4 = '<div><table cellpadding=0 cellspacing=0 style=font-size:9pt;><tr><td><strong>Desembarque:</strong> AV PROF ATILIO MARTINI, 399<tr><td></table></div>'; var i_4 = new google.maps.InfoWindow({content: cc_4, maxWidth: 350});  o_4.setMap(map); google.maps.event.addListener(o_4, 'click', function() { i_4.open(map, o_4);});var p_5 = new google.maps.LatLng(-22.822418, -47.076624); var o_5 = new google.maps.Marker({position: p_5, icon: ImagemOnibus, map:map });  var cc_5 = '<div><table cellpadding=0 cellspacing=0 style=font-size:9pt;><tr><td><strong>Linha: </strong>321-1   BOSQUE DAS PALMEIRAS-TERMINAL BARÃO GERALDO</td></tr><tr><td><strong>Embarque: </strong>AV PROF ATILIO MARTINI, ALTURA 416</td></tr></table></div>'; var i_5 = new google.maps.InfoWindow({content: cc_5, maxWidth: 350});  o_5.setMap(map); google.maps.event.addListener(o_5, 'click', function() { i_5.open(map, o_5);});var p_6 = new google.maps.LatLng(-22.809226, -47.066789); var o_6 = new google.maps.Marker({position: p_6, icon: ImagemOnibus, map:map });  var cc_6 = '<div><table cellpadding=0 cellspacing=0 style=font-size:9pt;><tr><td><strong>Desembarque:</strong> R MONS LOSCHI, ALTURA 896    REF.: R EDILBERTO LUIZ PEREIRA DA SILVA<tr><td></table></div>'; var i_6 = new google.maps.InfoWindow({content: cc_6, maxWidth: 350});  o_6.setMap(map); google.maps.event.addListener(o_6, 'click', function() { i_6.open(map, o_6);});" +
					"\n}";
			
			String routeName = "Casa/Trabalho";
			String route = routeName +
					"\n"+address1 +
					"\n"+address1latlng +
					"\n"+address2 +
					"\n"+address2latlng +
					"\n"+initialize;
			
			System.out.println(routeName+" adicionada? "+RouteManager.addRoute(routeName, route));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getRouteList()
	{
		ArrayList<String> res = new ArrayList<String>();
		String aux = tagNext;
		for (int i=1; !aux.equals(tagEnd); i++)
		{
			aux = pref.getString(tag+i, tagEnd);
			if (!aux.equals(tagNext) && !aux.equals(tagEnd))
			{
				res.add(aux);
			}
		}
		return res;
	}
	
	private static String findEmptyPosition()
	{
		String res = null;
		String aux = " ";
		for (int i=1; !aux.equals(tagEnd) && !aux.equals(tagNext); i++)
		{
			aux = pref.getString(tag+i, tagEnd);
			if (aux.equals(tagEnd) || aux.equals(tagNext))
			{
				res = tag+i;
			}
		}
		return res;
	}
	
	public static boolean addRoute(String routeName, String route)
	{
		if (findKey(routeName) == null)
		{
			editor.putString(findEmptyPosition(), route);
			editor.commit();
			return true;
		}
		return false;
	}
	
	public static boolean removeRoute(String routeName)
	{
		String key = findKey(routeName);
		if (key != null)
		{
			editor.putString(key, tagNext);
			editor.commit();
			return true;
		}
		return false;
	}
	
	private static String findKey(String routeName)
	{
		String res = null;
		String aux = " ";
		for (int i=1; (!aux.equals(tagEnd) && !aux.equals(routeName)); i++)
		{
			aux = pref.getString(tag+i, tagEnd);
			if (!aux.equals(tagEnd) && aux.contains("\n"))
			{
				aux = aux.substring(0, aux.indexOf("\n"));
			}
			if (aux.equals(routeName))
			{
				res = tag+i;
			}
		}
		return res;
	}
}