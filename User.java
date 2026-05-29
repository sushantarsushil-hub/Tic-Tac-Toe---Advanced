import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    String username;
    String password;
    int wins;
    int losses;
    int draws;
    int coins;                 
    ArrayList<String> badges;  
    ArrayList<String> ownedItems; 
    String selectedTheme;      
    String selectedAvatar;     
    public String selectedIcon; 

    public User(String username, String password) {

        this.username = username;

        this.password = password;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.coins = 100; 

        this.badges = new ArrayList<>();
        this.ownedItems = new ArrayList<>();
        this.selectedTheme = "Classic";
        this.selectedAvatar = "Default";
        this.selectedIcon = "Classic";
        
        this.ownedItems.add("Classic");
        this.ownedItems.add("Default");

    }

    public String getSelectedIcon() {

        if (selectedIcon == null) {
            selectedIcon = "Classic";
        }
        return selectedIcon;

    }

    public String getSelectedTheme() {
        if (selectedTheme == null) {
            selectedTheme = "Classic";
        }
        return selectedTheme;
    }

    public String getSelectedAvatar() {
        if (selectedAvatar == null) {
            selectedAvatar = "Default";
        }
        return selectedAvatar;
    }

    public int getTotalGames() {
        return wins + losses + draws;
    }

    public int getScore() {
        return wins * 10 + draws * 2; 
    }
}