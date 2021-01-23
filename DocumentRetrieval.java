import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;   // Import the FileWriter class

//import org.apache.commons.io.FileUtils;

public class DocumentRetrieval {

	public static void indexing() {
		
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		};
		
//		File folder = new File(System.getProperty("user.dir"));
//		Path listOfFiles = Paths.get(System.getProperty("user.dir"));
		
//		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
		
//		if (matcher.matches(listOfFiles)) {
//		    System.out.println("Yay");
//		    
//		    for(int i=0; i<listOfFiles.; i++) {
//				Path file = listOfFiles[i];
////				String contents = FileUtils.readFileToString(file, Charset.defaultCharset());
//				String contents = Files.readString(file);
//			}
//		}
		
//		File listOfFiles = folder.listFiles(filter);
		
		
		File folder = new File(System.getProperty("user.dir"));
		File[] listOfFiles2 = folder.listFiles(filter);
		

//		String regex = "^[a-zA-Z0-9]+$";
//		Pattern pattern = Pattern.compile(regex);
		
		
		
		for (File file : listOfFiles2) {
		    if (file.isFile()) {
		        System.out.println(file.getName());
		        
		        try {
//		            File myObj = new File("filename.txt");
					FileWriter myWriter = new FileWriter("dictionary_" + file.getName());
		            Scanner myReader = new Scanner(file);
		            
		            Set<String> set = new TreeSet<>();
		          
		            List<String> docList = new ArrayList<>();
		            
		            Map<String, Long> mapping = new TreeMap<>();
		            
		            AtomicLong totalPow = new AtomicLong();
		            double finalScore;
		            
		            while (myReader.hasNextLine()) {
		   
		              String data = myReader.nextLine().replaceAll("[^a-zA-Z0-9]+", " ").toLowerCase();
		              String[] splited = data.split("\\s+");
		              
		              for (int i = 0; i < splited.length; i++) {
		            	  if (splited[i].length() >= 3) {
		            		  
		            		  
		            		  docList.add(splited[i]);
		            		  mapping = docList
		            				  .stream()
		            				  .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		            		  
		            		  
		            		  if(set.add(splited[i])) {
		            			  System.out.println(splited[i]);
		            			  
		            	      }
		            		  
//		            		  System.out.println(set);
//		            		  myWriter.write(splited[i] + ":\n");
//		            		  System.out.println(splited[i]);
//							  myWriter.write(splited[i] + ":\n");
      
		            		  
//		            		  set.add(splited[i]);
		            		  
		            	  }
		              }
		              
		              
		              
//		              if (data.length() != 3) {
//		            	  System.out.println(data);
//		              }
		              
		              
//		              Matcher matcher = pattern.matcher(data);
//		              if(matcher.matches()) {
//		            	  System.out.println(data);
//		              }
		              
		            }
		            
//		            Iterator<String> i2 = docList.iterator();
//		            while(i2.hasNext()) {
//		            	System.out.println("I see :: " + i2.next());
//		            }
		            
//	            Iterator<String> i = set.iterator(); 
//	              while (i.hasNext()) {
//	            	  myWriter.write(i.next() + ":\n");
////		            	  System.out.println(i.next() + ":\n");
//	              }
	              
		            
		          mapping.entrySet()
		          	.stream()
		          	.sorted(Map.Entry.<String, Long>comparingByKey())
					.forEach(e -> {
						try {
							myWriter.write(e.getKey() + ":" + e.getValue() + "\n");
//							System.out.println(Math.pow(1, e.getValue()));
							totalPow.getAndAdd((long) Math.pow(e.getValue(), 2));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					});
		            
	              
		          System.out.println("totalPow: " + totalPow);
		          
//	              for(Map.Entry<String, Long> entry : mapping.entrySet()) {
//					  System.out.println("Now I see: " + entry.getKey() + ":" + entry.getValue());
//	            	  myWriter.write(entry.getKey() + ":" + entry.getValue() + "\n");
//	              }
	              
	              
		          // a new map to store 2 integer, key -> 1, value = occurrence.
		          
		          finalScore = Math.sqrt(totalPow.doubleValue());
	              myWriter.write("~score:" + finalScore);
	              System.out.println("~score: " + finalScore);
		              
		              
		            myReader.close();
					myWriter.close();
		            System.out.println();
		          } catch (FileNotFoundException e) {
		            System.out.println("An error occurred.");
		            e.printStackTrace();
		          } catch (IOException e) {
		        	  System.out.println("An error occurred.");
		              e.printStackTrace();
				}
		    }
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		indexing();

	}

}