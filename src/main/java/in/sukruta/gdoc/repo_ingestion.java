package in.sukruta.gdoc;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;


public class repo_ingestion
{
    static String repo_name;
    public static String repo_url; //the main & only input
    public static Path local_temp_path;


    public static String accept_input()
    {
        Scanner sc = new Scanner(System.in);
        boolean check = true;
        do
        {
            System.out.println("Enter the repo URL");
            repo_url = sc.nextLine();
            check = validate(repo_url); //basic validation of url till correct input not provided
            //System.out.println(repo_url + " " + check + "\n"); //debug log
            if(check != false)
            {
            sc.close();
            break;
            }
            System.out.println("Error! Please correct repo URL");
        }
        while(check != true);
        System.out.println("URL Recieved"); //logs for debugging
        return repo_url;
    }

    private static boolean validate(String repo_url)
    {
        String regex
            = "((http|git|ssh|http(s)|file|\\/?)|"
              + "(git@[\\w\\.]+))(:(\\/\\/)?)"
              + "([\\w\\.@\\:/\\-~]+)(\\.git)(\\/)?"; //pattern for Git repo URL
        Pattern p = Pattern.compile(regex);

        if(repo_url == null) return false;

        Matcher m = p.matcher(repo_url);
        p = Pattern.compile("([^/]+)\\.git$"); //pattern for repo name in the URL
        Matcher matcher = p.matcher(repo_url);
        if(matcher.find())
            repo_name = matcher.group(1); //saving repo name for all future use
        
        return m.matches();
    }

    private static void deleteDirectory(File file)
    {
        for(File subfile: file.listFiles())
            {
                if(subfile.isDirectory())
                    deleteDirectory(subfile);

                subfile.delete();
            }
    }

    public static String create_temp_folder() throws IOException//need to change the File, Files, FileUtils part
    {
        String base_temp_dir = System.getProperty("java.io.tmpdir"); //get system temp dir
        Path app_temp_dir = Paths.get(base_temp_dir, "repo"); //create app-specific base dir inside temp

        if(!Files.isDirectory(app_temp_dir)) //prepare base dir, if does not exist
        {
            Files.createDirectories(app_temp_dir);
        }

        local_temp_path = app_temp_dir.resolve(repo_name); //build full local path

        if(Files.isDirectory(local_temp_path))
        {
            File dir = local_temp_path.toFile();
            //try 
            {
                deleteDirectory(dir);
            }          
            // catch(IOException e)
            // {
            //     System.err.println("Failed to delete");
            //     throw e;
            // }
        }
        Files.createDirectories(local_temp_path);

        System.out.println("Base temp dir: " + base_temp_dir);
        System.out.println("App temp dir: " + app_temp_dir.toAbsolutePath());
        System.out.println("Local temp dir: " + local_temp_path.toAbsolutePath());

        return local_temp_path.toString();
    }


public static void main(String[] args) throws IOException
    {
        String valid_repo = accept_input();
        System.out.println(valid_repo);
        System.out.println(repo_name);
        String path = create_temp_folder();
        System.out.println("Temp folder: " + path);
        return;
    }
}