package in.sukruta.gdoc;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.File;
import java.io.IOException;

public class clone_repo extends repo_ingestion
{
    // Read GitHub token from environment variable
    private static String github_token = System.getenv("GITHUB_TOKEN");
    
    public static void repo_clone()
    {
        System.out.println("Starting Clone");
        System.out.println("Cloning repo from " + repo_url + " to " + local_temp_path.toString());
        
        try
        {
            // Check if authentication is available
            if (github_token != null && !github_token.isEmpty())
            {
                System.out.println("✓ GitHub token found - can clone private repositories");
                Git.cloneRepository()
                    .setURI(repo_url)
                    .setDirectory(new File(local_temp_path.toString()))
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(github_token, ""))
                    .call();
            }
            else
            {
                System.out.println("⚠️  No GITHUB_TOKEN found - can only clone public repositories");
                System.out.println("   For private repos, see setup instructions at: https://github.com/your-username/your-repo#setup");
                System.out.println("   Attempting public clone...");
                Git.cloneRepository()
                    .setURI(repo_url)
                    .setDirectory(new File(local_temp_path.toString()))
                    .call();
            }
            
            System.out.println("✓ Repo cloned successfully!");
        }
        catch(GitAPIException e)
        {
            System.err.println("✗ Failed to clone repository");
            
            // Provide helpful error messages
            if (e.getMessage().contains("Authentication") || e.getMessage().contains("not authorized"))
            {
                System.err.println("\n❌ Authentication Error:");
                System.err.println("   This appears to be a private repository.");
                System.err.println("   Please set up your GITHUB_TOKEN environment variable.");
                System.err.println("   See setup guide: https://github.com/your-username/your-repo#setup");
            }
            else if (e.getMessage().contains("not found"))
            {
                System.err.println("\n❌ Repository Not Found:");
                System.err.println("   Please check that the repository URL is correct.");
                System.err.println("   For private repos, make sure GITHUB_TOKEN is set.");
            }
            else
            {
                System.err.println("\n❌ Error details:");
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]) throws IOException
    {
        // Display startup info
        System.out.println("=== GitHub Repository Clone Tool ===\n");
        
        String valid_repo = accept_input();
        System.out.println("URL Validated. Repo: " + valid_repo);
        System.out.println("Repo Name: " + repo_name);
        String path = create_temp_folder();
        System.out.println("Temp folder: " + path);
        System.out.println();
        
        repo_clone();
        return;
    }
}