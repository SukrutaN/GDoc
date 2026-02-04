package in.sukruta.gdoc;

import java.io.IOException;

// import org.eclipse.jgit.api.Git;
// import org.eclipse.jgit.api.errors.GitAPIException;

// import java.io.File;



class clone_repo extends repo_ingestion
{
    public static void repo_clone()
    {
        System.out.println(repo_ingestion.repo_url);
    }
    public static void main(String args[]) throws IOException
    {
        String valid_repo = accept_input();
        System.out.println(valid_repo);
        System.out.println(repo_name);
        String path = create_temp_folder();
        System.out.println("Temp folder: " + path);
        repo_clone();
        return;
    }
}