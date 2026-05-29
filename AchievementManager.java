import javax.swing.JOptionPane;


public class AchievementManager {

    public static void checkAchievements(User user, UserManager manager) {

        awardIfEligible(user, "First Win", user.wins >= 1);

        awardIfEligible(user, "5 Wins Streak", user.wins >= 5);
        awardIfEligible(user, "Veteran (10 Wins)", user.wins >= 10);

        awardIfEligible(user, "Rich Player (200 Coins)", user.coins >= 200);

        awardIfEligible(user, "Experienced (20 Games)", user.getTotalGames() >= 20);

        manager.saveUsers();

    }

    private static void awardIfEligible(User user, String badge, boolean condition) {

        if (condition && !user.badges.contains(badge)) {

            user.badges.add(badge);
            user.coins += 20; 

            JOptionPane.showMessageDialog(null,
                "🏆 Achievement Unlocked!\n" + badge + "\n(+20 coins)",
                "Achievement", JOptionPane.INFORMATION_MESSAGE);
        }

    }

}