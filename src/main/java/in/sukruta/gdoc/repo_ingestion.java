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

import org.apache.commons.io.FileUtils;




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

    private static void deleteDirectory(File file) throws IOException // Success
    {
        try
        {
            FileUtils.cleanDirectory(file);
            FileUtils.deleteDirectory(file);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }    

public static String create_temp_folder() throws IOException// InterruptedException
{
    String base_temp_dir = System.getProperty("java.io.tmpdir");
    Path app_temp_dir = Paths.get(base_temp_dir, "repo");

    if(!Files.isDirectory(app_temp_dir))
    {
        Files.createDirectories(app_temp_dir);
    }

    local_temp_path = app_temp_dir.resolve(repo_name);

    if(Files.exists(local_temp_path))
    {
        System.out.println("Deleting existing directory: " + local_temp_path);
        
        File dir = local_temp_path.toFile();
        deleteDirectory(dir);
        System.out.println("Directory deleted successfully");
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