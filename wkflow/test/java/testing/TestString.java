package testing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestString {

	public static void main(String[] args) {
		System.out.println("--- INÍCIO ---");
		
		String in = "teste {entity.project.name} : {standard.notnull} {234325}";
		String out = "";
		
		Pattern p = Pattern.compile("\\{(.*?)\\}");
		Matcher m = p.matcher(in);
		
//		ResourceBundle bundle =  context.getApplication().getResourceBundle(context, "msg");
		
//		 List<String> path_resource = new ArrayList<String>();
		 
//		 Map<String,String> map = new HashMap<String,String>();
		 String ls_path;
		 String ls_msg = "";
		 
		while(m.find()) {
			ls_path = m.group(1);
			
			switch (ls_path){
				case "entity.project.name":
					ls_msg = "Nome do Projeto";
					break;
				
				case "standard.notnull":
					ls_msg = "Nulo";
					break;
			}
			
			out = in.replace(ls_path, ls_msg);
			in = out;
					
			System.out.println("Path->"+ls_path);
		    System.out.println(in);
		     
		}
		System.out.println("---  FIM  ---");
	}

}
