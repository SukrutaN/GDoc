package in.sukruta.gdoc;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;



public class clone_repo extends repo_ingestion
{
    public static void repo_clone()
    {
        System.out.println("Starting Clone");
        try
        {
            System.out.println("Cloning repo from " + repo_url + " to " + local_temp_path.toString());
            Git.cloneRepository()
                .setURI(repo_url)
                .setDirectory(new File(local_temp_path.toString()))
                .call();
                System.out.print("Repo cloned succesfully.");
        }
        catch(GitAPIException e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) throws IOException
    {
        String valid_repo = accept_input();
        System.out.println("URL Validated. Repo: " + valid_repo);
        System.out.println("Repo Name: " + repo_name);
        String path = create_temp_folder();
        System.out.println("Temp folder: " + path);
        repo_clone();
        return;
    }
}