import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("What would you like to search for?");
        System.out.println("\t1. Top-Rated Movies");
        System.out.println("\t2. Actor's/Director's Top-Rated Movies");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();  
        String infostream = "";      
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/top_rated?api_key=f7d16682c3c2176606a8caa62ab841c9");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode == 200)
            {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner2 = new Scanner(url.openStream());

                while (scanner2.hasNext()) 
                {
                    informationString.append(scanner2.nextLine());
                }

                scanner2.close();

                infostream = informationString.toString();
            }
        if(input.equals("1"))
        {
                System.out.println("Top Ten Movies:");

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(infostream);
                JSONArray arr = (JSONArray) jsonObject.get("results");
                for(int i = 0; i < 10; i++)
                {
                    JSONObject jsonObject2 = (JSONObject) arr.get(i);
                    String title = (String) jsonObject2.get("title");
                    double rating = (double) jsonObject2.get("vote_average");
                    System.out.println((i+1)+". "+title+" - Rating: "+rating);
                }
        }
        
        if(input.equals("2"))
        {
                System.out.println("Input Actor/Director Name:");
                String actor = userInput.nextLine();  
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(infostream);
                JSONArray arr = (JSONArray) jsonObject.get("results");
                int count  = 0;
                System.out.println(actor+"'s Top Five Movies");
                int i = 0;
                int page = 1;
                while(count < 5)
                {
                    if(i>19)
                    {
                        i = 0;
                        page++;
                        url = new URL("https://api.themoviedb.org/3/movie/top_rated?api_key=f7d16682c3c2176606a8caa62ab841c9&page="+page);

                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();

                        responseCode = conn.getResponseCode();

                        if (responseCode == 200)
                        {
                        StringBuilder informationString = new StringBuilder();
                        Scanner scanner2 = new Scanner(url.openStream());

                        while (scanner2.hasNext()) 
                        {
                            informationString.append(scanner2.nextLine());
                        }

                        scanner2.close();

                        infostream = informationString.toString();
                        }
                        jsonParser = new JSONParser();
                        jsonObject = (JSONObject) jsonParser.parse(infostream);
                        arr = (JSONArray) jsonObject.get("results");
                    }
                    JSONObject jsonObject2 = (JSONObject) arr.get(i);
                    long ID = (long) jsonObject2.get("id");
                    URL url2 = new URL("https://api.themoviedb.org/3/movie/"+ID+"/credits?api_key=f7d16682c3c2176606a8caa62ab841c9");

                    HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                    conn2.setRequestMethod("GET");
                    conn2.connect();

                    int responseCode2 = conn2.getResponseCode();

                if (responseCode2 == 200)
                {
                    StringBuilder informationString2 = new StringBuilder();
                    Scanner scanner3 = new Scanner(url2.openStream());

                    while (scanner3.hasNext()) 
                    {
                        informationString2.append(scanner3.nextLine());
                    }

                    scanner3.close();

                    String infostream2 = informationString2.toString();

                    JSONParser jsonParser2 = new JSONParser();
                    JSONObject jsonObject3 = (JSONObject) jsonParser2.parse(infostream2);
                    JSONArray arr2 = (JSONArray) jsonObject3.get("cast");
                    for(int j = 0; j < arr2.size(); j++)
                    {
                        JSONObject jsonObject4 = (JSONObject) arr2.get(j);
                        String name = (String) jsonObject4.get("name");
                        if(name.equals(actor))
                        {
                            String title = (String) jsonObject2.get("title");
                            double rating = (double) jsonObject2.get("vote_average");
                            System.out.println((count+1)+". "+title+" - Rating: "+rating);
                            count++;
                        }
                    }
                }
                i++;
            }   
    }
    }
    catch (Exception e) 
        {
            e.printStackTrace();
        }
    userInput.close();
}
}