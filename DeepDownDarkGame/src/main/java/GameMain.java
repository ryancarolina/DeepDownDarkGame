import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GameMain {

    JFrame window;
    Container con;
    JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel, townGatePanel;
    JLabel titleNameLabel, classLabel, hpLabel, mpLabel, weaponLable, acLabel, goldLabel, bossCrownLabel, locLabel, healingLabel, luckLabel, townGateLabel;
    JButton startButton, choice1, choice2, choice3, choice4, choice5, choice6;
    JTextArea mainTextArea;

    TitleScreenHandler tsHandler = new TitleScreenHandler();
    ChoiceHandler choiceHandler = new ChoiceHandler();

    //Fonts
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 28);
    Font smallFont = new Font("Times New Roman", Font.PLAIN, 20);

    //Game World
    int level;
    boolean pickClass = false;

    //Player
    String classType, location;
    int goldAward;
    int gold;
    int ac;
    int hp;
    int mp;
    int luck;
    int atkDmg;
    int numHpPots;
    int numMpPots;
    int hpPotsHealingAmount;
    int mpPotsAmount;
    int hpPotDropChance; //%
    int mpPotDropChance; //%
    int wepDropChance; //%
    int armDropChance; //%
    int exploreChance; //%
    int bossCrowns;
    int hitRating;

    //Monsters
    String [] monsterArray = new String[5];
    String monsterType;
    int monsterHp;
    int monsterAc;
    int monsterMp;
    int monsterDmg;
    int monHitRating;

    //Main Game Loop
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        new GameMain();
    }

    public GameMain() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        //Game UI
        window = new JFrame();
        window.setSize(1440, 900);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        con = window.getContentPane();

        //Title
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setBackground(Color.BLACK);

        titleNameLabel = new JLabel("DEEP DOWN DARK");
        titleNameLabel.setForeground(Color.WHITE);
        titleNameLabel.setFont(titleFont);

        //Start button
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 400, 200, 100);
        startButtonPanel.setBackground(Color.BLACK);

        startButton = new JButton("START");
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);
        startButton.setFont(normalFont);
        startButton.addActionListener(tsHandler);

        //Town Gate Image
        townGatePanel = new JPanel();
        townGatePanel.setBounds(920, 100, 500, 700);
        townGatePanel.setBackground(Color.BLACK);

        //todo use if statement to check loc and change image
        townGateLabel = new JLabel(new ImageIcon("DeepDownDarkGame/src/main/Resources/Dungeon_Entrance500x700.jpg"));

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);
        townGatePanel.add(townGateLabel);
        con.add(townGatePanel);
        con.add(titleNamePanel);
        con.add(startButtonPanel);

        //Play title song
        PlayMusic musicPlayer = new PlayMusic();
        musicPlayer.playMusic("DeepDownDarkGame/src/main/Resources/alexander-nakarada-cold-journey.wav");

        window.setVisible(true);
    }

    //Game Screen
    public void createGameScreen(){



        //Disable previous panels
        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);
        //townGatePanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.BLACK);
        con.add(mainTextPanel);

        mainTextArea = new JTextArea();
        mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setBackground(Color.BLACK);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);

        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(250, 350, 650, 350);
        choiceButtonPanel.setBackground(Color.BLACK);
        choiceButtonPanel.setLayout(new GridLayout(4, 1));
        con.add(choiceButtonPanel);

        choice1 = new JButton("1");
        choice1.setBackground(Color.WHITE);
        choice1.setForeground(Color.BLACK);
        choice1.setFont(smallFont);
        choice1.addActionListener(choiceHandler);
        choice1.setActionCommand("c1");
        choiceButtonPanel.add(choice1);

        choice2 = new JButton("2");
        choice2.setBackground(Color.WHITE);
        choice2.setForeground(Color.BLACK);
        choice2.setFont(smallFont);
        choice2.addActionListener(choiceHandler);
        choice2.setActionCommand("c2");
        choiceButtonPanel.add(choice2);

        choice3 = new JButton("3");
        choice3.setBackground(Color.WHITE);
        choice3.setForeground(Color.BLACK);
        choice3.setFont(smallFont);
        choice3.addActionListener(choiceHandler);
        choice3.setActionCommand("c3");
        choiceButtonPanel.add(choice3);

        choice4 = new JButton("4");
        choice4.setBackground(Color.WHITE);
        choice4.setForeground(Color.BLACK);
        choice4.setFont(smallFont);
        choice4.addActionListener(choiceHandler);
        choice4.setActionCommand("c4");
        choiceButtonPanel.add(choice4);

        choice5 = new JButton("5");
        choice5.setBackground(Color.WHITE);
        choice5.setForeground(Color.BLACK);
        choice5.setFont(smallFont);
        choice5.addActionListener(choiceHandler);
        choice5.setActionCommand("c5");
        choiceButtonPanel.add(choice5);

        choice6 = new JButton("6");
        choice6.setBackground(Color.WHITE);
        choice6.setForeground(Color.BLACK);
        choice6.setFont(smallFont);
        choice6.addActionListener(choiceHandler);
        choice6.setActionCommand("c6");
        choiceButtonPanel.add(choice6);

        playerPanel = new JPanel();
        playerPanel.setBounds(100, 15, 1300, 50);
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setLayout(new GridLayout(1, 4));
        con.add(playerPanel);

        classLabel = new JLabel();
        classLabel.setFont(smallFont);
        classLabel.setForeground(Color.WHITE);
        playerPanel.add(classLabel);

        locLabel = new JLabel();
        locLabel.setFont(smallFont);
        locLabel.setForeground(Color.WHITE);
        playerPanel.add(locLabel);

        hpLabel = new JLabel();
        hpLabel.setFont(smallFont);
        hpLabel.setForeground(Color.WHITE);
        playerPanel.add(hpLabel);

        acLabel = new JLabel();
        acLabel.setFont(smallFont);
        acLabel.setForeground(Color.WHITE);
        playerPanel.add(acLabel);

        healingLabel = new JLabel();
        healingLabel.setFont(smallFont);
        healingLabel.setForeground(Color.WHITE);
        playerPanel.add(healingLabel);

        mpLabel = new JLabel();
        mpLabel.setFont(smallFont);
        mpLabel.setForeground(Color.WHITE);
        playerPanel.add(mpLabel);

        goldLabel = new JLabel();
        goldLabel.setFont(smallFont);
        goldLabel.setForeground(Color.WHITE);
        playerPanel.add(goldLabel);

        luckLabel = new JLabel();
        luckLabel.setFont(smallFont);
        luckLabel.setForeground(Color.WHITE);
        playerPanel.add(luckLabel);

        bossCrownLabel = new JLabel();
        bossCrownLabel.setFont(smallFont);
        bossCrownLabel.setForeground(Color.WHITE);
        playerPanel.add(bossCrownLabel);

        weaponLable = new JLabel();
        weaponLable.setFont(smallFont);
        weaponLable.setForeground(Color.WHITE);
        playerPanel.add(weaponLable);

        playerSetup();

    }

    public void playerSetup(){
         String classType = " ";
         goldAward = 2 + level;
         gold = 0;
         ac = 1;
         hp = 60 + level;
         mp = 10 + level;
         luck = 5;
         atkDmg = new Random().nextInt(15) + level;
         numHpPots = 2;
         numMpPots = 0;
         hpPotsHealingAmount = 15;
         mpPotsAmount = 10;
         hpPotDropChance = 35; //%
         mpPotDropChance = 10; //%
         wepDropChance = 15; //%
         armDropChance = 10; //%
         exploreChance = 35; //%
         bossCrowns = 0;
         level = 1;
         hitRating = 5;

         classLabel.setText("Class:" + classType);
         hpLabel.setText("HP:" + hp);
         goldLabel.setText("GP:" + gold);
         acLabel.setText("AC:" + ac);
         mpLabel.setText("MP:" + mp);
         bossCrownLabel.setText("Crowns:" + bossCrowns);
         weaponLable.setText("Wep:");
         locLabel.setText("Loc:" + location);
         healingLabel.setText("Heal:" + hpPotsHealingAmount);
         luckLabel.setText("Luck:" + luck);

         townGate();
    }

    public void town(){
        location = "Town";
        locLabel.setText("Loc:Town");
        mainTextArea.setText("Welcome to SurfaceTown!");

        choice1.setText("Shop");
        choice2.setText("Smith");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("Back to the road");
    }

    public void townGate(){
        location = "Town Gate";
        locLabel.setText("Loc:Town Gate");
        mainTextArea.setText("Welcome to Deep Down Dark!\n\nWhat Class would you like to play?");

        choice1.setText("Fighter");
        choice2.setText("Bard");
        choice3.setText("Wizard");
        choice4.setText("Cleric");
        choice5.setText("Rogue");
        choice6.setText("Monk");
    }

    public void roadOutsideTownGate(){
        location = "Road Outside Town";
        locLabel.setText("Loc:Road outside town");
        mainTextArea.setText("It's early morning, with a slight chill in the air. \n The sky is clear and the wind is at your back.\n\n What will you do?");

        choice1.setText("Back to town");
        choice2.setText("Enter the DEEP DOWN DARK");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void deepDownDark(){
        location = "DEEP DOWN DARK";
        locLabel.setText("Loc:DEEP DOWN DARK");
        mainTextArea.setText("You enter the DEEP DOWN DARK with clear eyes \nand hope in your heart!\n\n What will you do?");

        choice1.setText("Light your torch and have at them!");
        choice2.setText("Back to the road");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void monster(){
        location = "Battle";
        locLabel.setText("loc:Battle");

        if(populateMonsters() == 0){
            monsterType = "Zombie";
            monsterHp = (int) getRandomIntegerBetweenRange(0, 5) + 10 + level;
            monsterAc = 5 + level;
            monsterMp = 0 + level;
            monsterDmg = (int) getRandomIntegerBetweenRange(0, 5) + level;
            monHitRating = 1 + level;

            mainTextArea.setText("You encounter a Zombie!\n\n HP:" + monsterHp);
        }else if(populateMonsters() == 1){
            monsterType = "Skeleton";
            monsterHp = (int) getRandomIntegerBetweenRange(0, 5) + 10 + level;
            monsterAc = 5 + level;
            monsterMp = 0 + level;
            monsterDmg = (int) getRandomIntegerBetweenRange(0, 5) + level;
            monHitRating = 1 + level;

            mainTextArea.setText("You encounter a Skeleton!\n\n HP:" + monsterHp);
        }else if(populateMonsters() == 2){
            monsterType = "Giant Rat";
            monsterHp = (int) getRandomIntegerBetweenRange(0, 5) + 10 + level;
            monsterAc = 5 + level;
            monsterMp = 0 + level;
            monsterDmg = (int) getRandomIntegerBetweenRange(0, 5) + level;
            monHitRating = 1 + level;

            mainTextArea.setText("You encounter a Giant Rat!\n\n HP:" + monsterHp);
        }else if(populateMonsters() == 3){
            monsterType = "Giant Spider";
            monsterHp = (int) getRandomIntegerBetweenRange(0, 5) + 10 + level;
            monsterAc = 5 + level;
            monsterMp = 0 + level;
            monsterDmg = (int) getRandomIntegerBetweenRange(0, 5) + level;
            monHitRating = 1 + level;

            mainTextArea.setText("You encounter a Giant Spider!\n\n HP:" + monsterHp);
        }else if(populateMonsters() == 4){
            monsterType = "Ghoul";
            monsterHp = (int) getRandomIntegerBetweenRange(0, 5) + 10 + level;
            monsterAc = 5 + level;
            monsterMp = 0 + level;
            monsterDmg = (int) getRandomIntegerBetweenRange(0, 5) + level;
            monHitRating = 1 + level;

            mainTextArea.setText("You encounter a Ghoul!\n\n HP:" + monsterHp);
        }
        else{
            mainTextArea.setText("Nothing lurks here...");
        }

        choice1.setText("Attack");
        choice2.setText("Keep exploring");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");

    }

    public int populateMonsters(){
        monsterArray[0] = "Zombie";
        monsterArray[1] = "Skeleton";
        monsterArray[2] = "Giant Rat";
        monsterArray[3] = "Giant Spider";
        monsterArray[4] = "Ghoul";

        int rnd = new Random().nextInt(monsterArray.length);
        return rnd;
    }

    public void playerAttack(){
        location = "Player Attack";
        locLabel.setText("Loc:Player ATK");

        //If the player is not dead, keep fighting
        if(hp > 0){
            //Resolve player dmg to monster
            int hitCheck = new Random().nextInt(21) + hitRating;

            atkDmg = new Random().nextInt(15) + level;

            if(hitCheck > monsterAc){
                monsterHp -= atkDmg;
            }

            //If the monster is not dead, keep fighting
            if(monsterHp > 0){
                //Reslove monster dmg to player
                int monHitCheck = new Random().nextInt(21) + monHitRating;

                monsterDmg = new Random().nextInt(10) + level;

                if(monHitCheck > ac){
                    hp -= monsterDmg;
                    hpLabel.setText("HP:" + hp);
                }

                mainTextArea.setText(classType + " hits " + monsterType + " for " + atkDmg + "\n\n" + monsterType + " HP is now " + monsterHp
                        + "\n\n" + monsterType + " hits " + classType + " for " + monsterDmg);

                choice1.setText("Attack");
                choice2.setText("Attempt to run");
                choice3.setText("");
                choice4.setText("");
                choice5.setText("");
                choice6.setText("");
            }

        }

        if(hp <= 0){
            loseGame();
        }

        if(monsterHp <= 0){
            mainTextArea.setText("You have slain the " + monsterType);
            loot();
        }


    }

    public void loot(){
        location = "Loot";
        locLabel.setText("Loc:Loot");
        int rollForLoot = new Random().nextInt(8);

        if(rollForLoot > 4){
            int loot = gold += 1 + level;
            goldLabel.setText("GP:" + gold);

            mainTextArea.setText("You looted " + loot + " gold");
        }

        choice1.setText("Keep exploring");
        choice2.setText("Return to town");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void loseGame(){
        location = "Game Over";
        locLabel.setText("Loc:Game Over");
        mainTextArea.setText("Your clear eyes have been shut \nand the hope in your heart snuffed out!\n\n What will you do?");

        choice1.setText("Play Again?");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    //TODO Create shop
    public void shop(){

    }

    //TODO Create smith
    public void smith(){

    }

    public void fighter(){
        location = "Fighter Selection";
        locLabel.setText("Loc:Town Gate");
        mainTextArea.setText("You have chosen the Fighter! \nThey start with a little extra HP and AC.");

        //Redraw the UI after a stat change
        hp += 4;
        hpLabel.setText("HP:" + hp);

        ac += 4;
        acLabel.setText("AC:" + ac);

        pickClass = true;

        classType = "Fighter";
        classLabel.setText("Class:" + classType);

        choice1.setText("Onward to adventure, fighter!");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void bard(){
        location = "Bard Selection";
        locLabel.setText("Loc:Town Gate");
        mainTextArea.setText("You have chosen the Bard! \nThey start with a little extra MP and Gold");

        //Redraw the UI after a stat change
        mp += 4;
        mpLabel.setText("MP:" + mp);

        gold += 10;
        goldLabel.setText("GP:" + gold);

        pickClass = true;

        classType = "Bard";
        classLabel.setText("Class:" + classType);

        choice1.setText("Onward to adventure, bard!");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void wizard(){
        location = "Wizard Selection";
        locLabel.setText("Loc:Town Gate");
        mainTextArea.setText("You have chosen the Wizard! \nThey start with extra MP.");

        //Redraw the UI after a stat change
        mp += 10;
        mpLabel.setText("MP:" + mp);

        pickClass = true;

        classType = "Wizard";
        classLabel.setText("Class:" + classType);

        choice1.setText("Onward to adventure, wizard!");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void cleric(){
        location = "Cleric Selection";
        locLabel.setText("Loc:Town Gate");
        mainTextArea.setText("You have chosen the Cleric! \nThey start with a little extra HP and Healing %.");

        //Redraw the UI after a stat change
        hp += 10;
        hpLabel.setText("HP:" + hp);

        hpPotsHealingAmount += 5;
        healingLabel.setText("Heal:" + hpPotsHealingAmount);

        pickClass = true;

        classType = "Cleric";
        classLabel.setText("Class:" + classType);

        choice1.setText("Onward to adventure, cleric!");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void rogue(){
        location = "Rogue Selection";
        locLabel.setText("Loc:Town Gate");
        mainTextArea.setText("You have chosen the Rouge! \nThey start with extra luck and a little AC.");

        //Redraw the UI after a stat change
        luck += 8;
        luckLabel.setText("Luck:" + luck);

        ac += 2;
        acLabel.setText("AC:" + ac);

        pickClass = true;

        classType = "Rogue";
        classLabel.setText("Class:" + classType);

        choice1.setText("Onward to adventure, Rogue!");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void monk(){
        location = "Fighter Selection";
        locLabel.setText("Loc:Town Gate");
        mainTextArea.setText("You have chosen the Fighter! \nThey start with extra HP.");

        //Redraw the UI after a stat change
        hp += 8;
        hpLabel.setText("HP:" + hp);

        ac += 5;
        acLabel.setText("AC:" + ac);

        pickClass = true;

        classType = "Fighter";
        classLabel.setText("Class:" + classType);

        choice1.setText("Onward to adventure, fighter!");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    //Music/SFX
    public static class PlayMusic{

        //Title Song
        //DeepDownDarkGame/src/main/Resources/alexander-nakarada-cold-journey.wav

        public AudioInputStream audioStream(String filePath) throws IOException, UnsupportedAudioFileException {
            return AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        }

        Clip clip = AudioSystem.getClip();

        public PlayMusic() throws LineUnavailableException {
        }

        public void playMusic(String filePath){
            try {
                clip.open(audioStream(filePath));
                clip.start();
            } catch(Exception ex) {
                System.out.println("Error with playing sound.");
                ex.printStackTrace();
            }
        }

        public void stopMusic(){
            try{
                clip.stop();
                clip.close();
            }catch (Exception e){
                System.out.println(e + "Error with stopping sound");
            }
        }

    }

    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public class TitleScreenHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            createGameScreen();
        }
    }

    public class ChoiceHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            String yourChoice = e.getActionCommand();

            switch(location){
                case "Town Gate":
                    switch(yourChoice){
                        case "c1": fighter(); break;
                        case "c2": bard(); break;
                        case "c3": wizard(); break;
                        case "c4": cleric(); break;
                        case "c5": rogue(); break;
                        case "c6": monk(); break;
                    }
                    break;
                case "Fighter Selection":
                case "Bard Selection":
                case "Wizard Selection":
                case "Cleric Selection":
                case "Rogue Selection":
                case "Monk Selection":
                    switch(yourChoice){
                        case "c1": roadOutsideTownGate(); break;
                    }
                    break;
                case "Road Outside Town":
                    switch(yourChoice){
                        case "c1": town(); break;
                        case "c2": deepDownDark(); break;
                    }
                    break;
                case "Town":
                    switch(yourChoice){
                        case "c1": break;
                        case "c2": break; //todo add smith and shop here
                        case "c6": roadOutsideTownGate(); break;
                    }
                    break;
                case "DEEP DOWN DARK":
                    switch(yourChoice){
                        case "c1": monster(); break;
                        case "c2": roadOutsideTownGate(); break;
                    }
                    break;
                case "Battle":
                case "Player Attack":
                    switch(yourChoice){
                        case "c1": playerAttack(); break;
                        case "c2": monster(); break;
                    }
                    break;
                case "Game Over":
                    switch(yourChoice){
                        case "c1": townGate(); break;
                    }
                    break;
                case "Loot":
                    switch(yourChoice){
                        case "c1": monster(); break;
                        case "c2": town(); break;
                    }

            }
        }
    }

}
