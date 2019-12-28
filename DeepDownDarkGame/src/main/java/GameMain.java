import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

public class GameMain {

    JFrame window;
    Container con;
    JPanel titleNamePanel, startButtonPanel, mainTextPanel;
    JLabel titleNameLabel;
    JButton startButton;
    JTextArea mainTextArea;

    TitleScreenHandler tsHandler = new TitleScreenHandler();

    //Fonts
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);

    public static void main(String[] args) {

        new GameMain();
    }

    public GameMain(){

        window = new JFrame();
        window.setSize(800, 600);
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

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);
        con.add(titleNamePanel);
        con.add(startButtonPanel);

        window.setVisible(true);

        //System objects
        Scanner scanIn = new Scanner(System.in);
        Random rand = new Random();

        //Game vars
        //TODO read enemies in from flat flie/data source
        String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin", "Ghoul", "Tusker", "Dire Wolf", "Giant Rat", "Giant Spider", "Swarm of Rats", "Gelatinous Cube", "Drow"};
        String[] wep = {"Dagger", "Spear", "Crossbow", "Long Sword", "Short Sword", "Saber", "Maul", "Morning Star", "Great Axe", "Staff", "Mace", "Warhammer", "Flail"};
        String[] arm = {"Bronze Chestplate", "Chainmail Shirt", "Leather Bracers", "Wooden Buckler"};
        String[] trait = {"Shiny", "Rusty", "Bent", "Blazing", "Frozen"};
        String[] quality = {"Uncommon", "Rare", "Epic", "Artifact", "Mythical"};
        int level = 1;
        int maxEnemyhealth = 18 + level;
        int maxEnemyAtkDmg = 12 + level;

        //Player vars
        String classType = " ";
        int goldAward = 2 + level;
        int gold = 0;
        int ac = 1;
        int hp = 60 + level;
        int mp = 10 + level;
        int luck = 5;
        int atkDmg = 15 + level;
        int numHpPots = 2;
        int numMpPots = 0;
        int hpPotsHealingAmount = 15;
        int mpPotsAmount = 10;
        int hpPotDropChance = 35; //%
        int mpPotDropChance = 10; //%
        int wepDropChance = 15; //%
        int armDropChance = 10; //%
        int exploreChance = 35; //%
        int bossCrowns = 0;

        boolean running = true;
        boolean pickClass = false;

        System.out.println("Welcome to the Deep Down Dark!");
        System.out.println("\n\tWhat class would you like to play?");
        System.out.println("\t1. Fighter");
        System.out.println("\t2. Bard");
        System.out.println("\t3. Wizard");
        System.out.println("\t4. Cleric");
        System.out.println("\t5. Rogue");
        System.out.println("\t6. Monk");

        String input = scanIn.nextLine();

        GAME:
        while (running) {

            while (pickClass == false) {

                if (input.equals("1")) {
                    System.out.println("\t## You have chosen the Fighter! They start with extra HP.");
                    hp += 8;
                    ac += 5;
                    System.out.println("\tYour HP: " + hp);
                    pickClass = true;
                    classType = "Fighter";

                } else if (input.equals("2")) {
                    System.out.println("\t## You have chosen the Bard! They start with an extra healing potion.");
                    numHpPots += 1;
                    ac += 3;
                    System.out.println("\tYou have " + numHpPots + " health potion(s) left in your bag");
                    pickClass = true;
                    classType = "Bard";

                } else if (input.equals("3")) {
                    System.out.println("\t## You have chosen the Wizard! They start with a bonus to attack damage and total mana.");
                    atkDmg += 4;
                    mp += 10;
                    System.out.println("\tYour AtkDMG: " + atkDmg);
                    System.out.println("\tYour MP: " + mp);
                    pickClass = true;
                    classType = "Wizard";

                } else if (input.equals("4")) {
                    System.out.println("\t## You have chosen the Cleric! They start with a bonus to HP healed via potions.");
                    hpPotsHealingAmount += 3;
                    ac += 4;
                    System.out.println("\tYour Potion Healing amount is now: " + hpPotsHealingAmount);
                    pickClass = true;
                    classType = "Cleric";

                } else if (input.equals("5")) {
                    System.out.println("\t## You have chosen the Rogue! They have a greater chance of finding loot and greater luck.");
                    hpPotDropChance += 2;
                    wepDropChance += 2;
                    armDropChance += 2;
                    luck += 3;
                    ac += 2;
                    System.out.println("\tYour chance to find loot is now: " + hpPotDropChance + "%");
                    pickClass = true;
                    classType = "Rogue";

                } else if (input.equals("6")) {
                    System.out.println("\t## You have chosen the Monk! They have a greater chance of sensing hidden locations.");
                    exploreChance += 5;
                    ac += 1;
                    System.out.println("\tYour chance to locate hidden areas is now: " + exploreChance + "%");
                    pickClass = true;
                    classType = "Monk";

                } else {
                    //TODO Fix endless invalid command loop bug
                    System.out.println("\t>> Invalid command!");
                }

            }

            System.out.println("-------------------------------------------------------------------------------------");

            //Set enemy hp
            int enemyHealth = rand.nextInt(maxEnemyhealth);
            String enemy = enemies[rand.nextInt(enemies.length)];

            System.out.println("\t# " + enemy + " has appeared! #\n");

            //Combat
            while (enemyHealth > 0) {

                //Player status
                System.out.println("\tYour HP: " + hp);
                System.out.println("\tYou are on dungeon level: " + level);
                if (level == 1) {
                    System.out.println("P-----------------------------------------------------------------------------------");
                } else if (level == 2) {
                    System.out.println("---P--------------------------------------------------------------------------------");
                } else if (level == 3) {
                    System.out.println("------P-----------------------------------------------------------------------------");
                } else if (level == 4) {
                    System.out.println("---------P---------------------------------------------------------------------------");
                } else if (level == 5) {
                    System.out.println("------------P------------------------------------------------------------------------");
                } else if (level == 6) {
                    System.out.println("--------------P----------------------------------------------------------------------");
                } else if (level == 7) {
                    System.out.println("----------------P--------------------------------------------------------------------");
                } else if (level == 8) {
                    System.out.println("------------------P------------------------------------------------------------------");
                } else if (level == 9) {
                    System.out.println("--------------------P----------------------------------------------------------------");
                } else if (level == 10) {
                    System.out.println("----------------------P--------------------------------------------------------------");
                    System.out.println("\t # Congratulations on reaching dungeon level 10! # ");
                    //TODO Add the rest of the levels
                }

                //Enemy status
                System.out.println("\t" + enemy + "'s HP: " + enemyHealth);

                //Actions
                System.out.println("\n\tWhat would you like to do?");
                System.out.println("\t1. Attack");
                System.out.println("\t2. Drink health potion");
                System.out.println("\t3. Run!");
                System.out.println("\t4. View Character Sheet");
                System.out.println("\t5. Magic");

                input = scanIn.nextLine();
                if (input.equals("1")) {
                    //Attack enemy
                    int dmgDelt = rand.nextInt(atkDmg);

                    //Enemy attack player
                    int dmgTaken = rand.nextInt(maxEnemyAtkDmg);

                    //Player damages monster
                    enemyHealth -= dmgDelt;

                    //Calc dmg taken after reducing it by player armor class
                    int totalDmgTaken = dmgTaken - ac;

                    //Monsters cannot deal negative damage
                    if (totalDmgTaken < 0) {
                        totalDmgTaken = 0;
                    }

                    //Reduce the players HP by the total dmg amount after AC calc
                    hp -= totalDmgTaken;

                    //Set enemy stats based on enemy type and current dungeon level
                    if (enemy == "Zombie") {
                        if (level > 6) {
                            System.out.println("-------------------------------------------------------------------------------------");
                            System.out.println("BOSS ENCOUNTER!!");
                            System.out.println("-------------------------------------------------------------------------------------");
                            enemy = "Hulking Zombie Lord";
                            System.out.println("With a crown of bone upon its rotting head, it moves to devour you!");
                            maxEnemyAtkDmg += level;
                            maxEnemyhealth += level;
                            enemyHealth += 30;
                        }

                    }

                    if (enemy == "Giant Spider") {
                        if (level > 7) {
                            System.out.println("-------------------------------------------------------------------------------------");
                            System.out.println("BOSS ENCOUNTER!!");
                            System.out.println("-------------------------------------------------------------------------------------");
                            enemy = "Widow Queen";
                            System.out.println("With a crown of web upon her black head, she lunges to crush you!");
                            maxEnemyAtkDmg += level;
                            maxEnemyhealth += level;
                            enemyHealth += 40;
                        }

                    }

                    //Report combat round information to the player
                    System.out.println("\t## You STRIKE the " + enemy + " for " + dmgDelt + " damage. ##");
                    System.out.println("\t## Enemy " + enemy + " ATTACKS you for " + dmgTaken + " damage. ##");
                    System.out.println("\t## You are DAMAGED by " + enemy + " for " + totalDmgTaken + " damage due to your armor ##");

                    //Player is slain by monster
                    if (hp <= 0) {
                        System.out.println("\t## You have been slain! " + enemy + " has ended your life...");
                        break;
                    }

                    //Drink a HP potion
                } else if (input.equals("2")) {

                    if (numHpPots > 0) {
                        hp += hpPotsHealingAmount;
                        numHpPots--;
                        System.out.println("\t## You drink a health potion, healing for " + hpPotsHealingAmount + "." + "\n\t>> You now have " + hp + " HP.");
                    } else {
                        System.out.println("\t## You do not have any health potions! Slay enemies and loot them for a chance to find one!");
                    }

                    //Attempt to run from combat
                } else if (input.equals("3")) {

                    //Check to see if the player is able to run away
                    if (rand.nextInt(20) > luck) {
                        System.out.println("\t## You run away from " + enemy + "!");
                        System.out.println("\t## You are on dungeon level: " + level);
                        continue GAME;
                    } else {
                        System.out.println("\t## The " + enemy + " prevents your escape and attacks!");

                        //Check to see if the monster damages the player during the escape attempt
                        if (rand.nextInt(20) >= 10) {
                            int dmgTaken = rand.nextInt(maxEnemyAtkDmg);

                            //Calc dmg taken after reducing it by player armor class
                            int totalDmgTaken = dmgTaken - ac;

                            //Monsters cannot deal negative damage
                            if (totalDmgTaken < 0) {
                                totalDmgTaken = 0;
                            }

                            //Reduce the players HP by the total dmg amount after AC calc
                            hp -= totalDmgTaken;

                            System.out.println("\t## Attack of opportunity from " + enemy);
                            System.out.println("\t## Enemy " + enemy + " ATTACKS you for " + dmgTaken + " damage. ##");
                            System.out.println("\t## You are DAMAGED by " + enemy + " for " + totalDmgTaken + " damage due to your armor ##");

                            //Player is slain by monster
                            if (hp <= 0) {
                                System.out.println("\t## You have been slain! " + enemy + " has ended your life...");
                                break;
                            }

                            //The attack of opportunity fails
                        } else {
                            System.out.println("\t## The attack of opportunity fails!");
                        }
                    }

                    //View character sheet
                } else if (input.equals("4")) {
                    System.out.println("-------------------------------------------------------------------------------------");
                    System.out.println("CHARACTER SHEET:");
                    System.out.println("-------------------------------------------------------------------------------------");

                    System.out.println("\tCLASS: " + classType);
                    System.out.println("\tHP: " + hp);
                    System.out.println("\tMP: " + mp);
                    System.out.println("\tAC: " + ac);
                    System.out.println("\tLUCK: " + luck);
                    System.out.println("\tATTACK: " + atkDmg);
                    System.out.println("\tHP POTIONS: " + numHpPots);
                    System.out.println("\tHEALING: " + hpPotsHealingAmount + "%");
                    System.out.println("\tPOTION FIND CHANCE: " + hpPotDropChance + "%");
                    System.out.println("\tWEAPON FIND CHANCE: " + wepDropChance + "%");
                    System.out.println("\tARMOR FIND CHANCE: " + armDropChance + "%");
                    System.out.println("\tSPOT CHANCE: " + exploreChance + "%");
                    System.out.println("\tGOLD: " + gold);
                    System.out.println("\tBOSS CROWNS: " + bossCrowns);

                    System.out.println("-------------------------------------------------------------------------------------");
                    System.out.println("END OF CHARACTER SHEET");
                    System.out.println("-------------------------------------------------------------------------------------");

                    //Magic System
                } else if (input.equals("5")) {

                    if (classType == "Wizard") {

                        System.out.println("-------------------------------------------------------------------------------------");
                        System.out.println("SPELL BOOK");
                        System.out.println("-------------------------------------------------------------------------------------");

                        System.out.println("\t1. Force Bolt - 5 MP");
                        System.out.println("\t0. Drink Mana Potion");

                        input = scanIn.nextLine();

                        if (input.equals("1")) {

                            if (mp >= 5) {

                                System.out.println("You cast FORCE BOLT at the " + enemy);

                                //Player attacks monster with a spell
                                int dmgDelt = rand.nextInt(atkDmg);

                                //Player spends mana
                                mp -= 5;

                                //Player damages monster with a spell
                                enemyHealth -= dmgDelt;

                                //Report combat round information to the player
                                System.out.println("\t## Your spell impacts the " + enemy + " for " + dmgDelt + " damage. ##");
                                System.out.println("\t## Your remaining mana is " + mp + " ##");

                            } else {
                                System.out.println("-------------------------------------------------------------------------------------");
                                System.out.println("You do not have enough mana to cast the spell!");
                                System.out.println("-------------------------------------------------------------------------------------");
                            }
                        }

                        if (input.equals("0")) {

                            if (numMpPots > 0) {
                                mp += mpPotsAmount;
                                numMpPots--;
                                System.out.println("\t## You drink a Mana potion, restoring " + mpPotsAmount + "." + "\n\t>> Mana, you now have " + mp + " MP.");
                            } else {
                                System.out.println("\t## You do not have any health potions! Slay enemies and loot them for a chance to find one!");
                            }
                        }

                    } else {
                        System.out.println("-------------------------------------------------------------------------------------");
                        System.out.println("You are no Wizard and do not understand magic!");
                        System.out.println("-------------------------------------------------------------------------------------");
                    }

                } else {

                    System.out.println("\t## Invalid command!");

                }

            }

            //Game over message
            if (hp < 1) {
                System.out.println("Your corpse litters the dungeon floor on level " + level);
                System.out.println("Your total gold looted: " + gold);
                break;
            }

            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println(" # " + enemy + " was defeated! # ");
            System.out.println(" # You have " + hp + " HP left. #");
            System.out.println(" # You have " + mp + " MP left. #");
            System.out.println(" # You find " + goldAward + " gold!");
            gold += goldAward;
            System.out.println(" # Your total gold is: " + gold);

            //If the monster killed was a boss
            if (enemy == "Widow Queen"){
                System.out.println("Congratulations on defeating the Widow Queen!");
                bossCrowns ++;
            }

            if (enemy == "Hulking Zombie Lord"){
                System.out.println("Congratulations on defeating the Hulking Zombie Lord!");
                bossCrowns ++;
            }

            //Health potion drop chance
            if (rand.nextInt(100) < hpPotDropChance) {
                numHpPots++;
                System.out.println(" # The " + enemy + " dropped a health potion! # ");
                System.out.println(" # You now have " + numHpPots + " health potion(s) in your bag. # ");
            }

            //Health potion drop chance
            if (rand.nextInt(100) < mpPotDropChance) {
                numMpPots++;
                System.out.println(" # The " + enemy + " dropped a mana potion! # ");
                System.out.println(" # You now have " + numMpPots + " mana potion(s) in your bag. # ");
            }

            //Weapon drop chance
            if (rand.nextInt(100) < wepDropChance) {

                if(rand.nextInt(100) >= luck){

                    String wepType = wep[rand.nextInt(wep.length)];
                    String qualityType = quality[rand.nextInt(quality.length)];
                    System.out.println(" # LUCKY! You find a " + wepType + " of " + qualityType + " quality! #");

                    //"Uncommon", "Rare", "Epic", "Artifact", "Mythical"
                    if(qualityType == "Uncommon"){
                        atkDmg += 2;
                    }

                    if(qualityType == "Rare"){
                        atkDmg += 3;
                    }

                    if(qualityType == "Epic"){
                        atkDmg += 4;
                    }

                    if(qualityType == "Artifact"){
                        atkDmg += 5;
                    }

                    if(qualityType == "Mythical"){
                        atkDmg += 6;
                    }

                    System.out.println(" # Your atkDmg is now: " + atkDmg + " # ");

                }else{

                    String wepType = wep[rand.nextInt(wep.length)];
                    System.out.println(" # You find a " + wepType + " of " + enemy + " slaying +1! # ");
                    atkDmg += 1;
                    System.out.println(" # Your atkDmg is now: " + atkDmg + " # ");

                }

            }

            //Armor drop chance
            if (rand.nextInt(100) < armDropChance) {

                String armType = arm[rand.nextInt(arm.length)];
                String traitType = trait[rand.nextInt(trait.length)];
                System.out.println(" # You find a " + traitType + " " + armType + " +1! # ");
                ac += 1;
                System.out.println(" # Your AC is now: " + ac + " # ");

            }

            //Chance to advance to next dungeon level
            if (rand.nextInt(100) < exploreChance) {
                level++;
                System.out.println(" # You locate a passage that leads deeper into the dungeon. # ");
                System.out.println(" # You are on dungeon level: " + level + " # ");
            }
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("What would you like to do now?");
            System.out.println("1. Continue your dungeon crawl?");
            //System.out.println("2. Make camp");
            System.out.println("2. Exit the dungeon?");

            input = scanIn.nextLine();

            while (!input.equals("1") && !input.equals("2")) {
                System.out.println("Invalid command!");
                input = scanIn.nextLine();
            }

            if (input.equals("1")) {
                System.out.println("### YOU DELVE DEEPER INTO THE DUNGEON... ###");
                if (rand.nextInt(100) < 5) {
                    System.out.println("As you make your way down the passage, the air becomes humid and thick. The air is foul with the stench of rotting plant life.");
                    atkDmg -= 1;
                    System.out.println("Your shortness of breath has robbed you of endurance, atkDmg reduced to " + atkDmg);
                } else if (rand.nextInt(100) < 10 && (rand.nextInt(100) > 5)) {
                    System.out.println("The passage opens up into a large room with highly polished walls of marble. As you enter the middle of the room a white light washes over you.");
                    hp += 10 + level;
                    System.out.println("You are healed! Your HP is now: " + hp);
                } else if (rand.nextInt(100) < 20 && (rand.nextInt(100) > 10)) {
                    System.out.println("The echo of your foot steps seem to go on forever. Suddenly the floor falls out from beneath you!");
                    hp -= 10 + level;
                    System.out.println("You have fallen into a pitfall trap! You suffer fall damage, your HP is now: " + hp);
                    //Player is slain by trap
                    if (hp <= 0) {
                        System.out.println("\t>> You have fallen to your death");
                        break;
                    }
                } else if (rand.nextInt(100) < 30 && (rand.nextInt(100) > 20)) {
                    System.out.println("The passage has a slight slope to it and the walls are more cave like now.");
                } else if (rand.nextInt(100) < 40 && (rand.nextInt(100) > 30)) {
                    System.out.println("You decided to make camp and start a small fire. You awake to the sound of someone or something rustling through your bags!");

                    if (numHpPots > 0) {
                        numHpPots -= 1;
                    } else {
                        numHpPots = 0;
                    }
                    System.out.println("You rush to your feet, weapon in hand! The thief evades you and disappears into the dark. You have lost a healing potion.");
                } else if (rand.nextInt(100) < luck){
                    System.out.println("Hail Hero! I am the traveling merchant, would you like to see my wares?");
                    System.out.println("-------------------------------------------------------------------------------------");
                    System.out.println("Merchant Menu");
                    System.out.println("-------------------------------------------------------------------------------------");

                    System.out.println("\t1. Buy items");
                    System.out.println("\t0. Exit");

                    input = scanIn.nextLine();

                    if(input.equals("1")){
                        System.out.println("-------------------------------------------------------------------------------------");
                        System.out.println("Merchant Menu");
                        System.out.println("-------------------------------------------------------------------------------------");

                        System.out.println("\t1. Scroll of MAX HP+ - 50g");
                        System.out.println("\t2. Scroll of MAX MP+ - 50g");

                        input = scanIn.nextLine();

                        if(input.equals("1")){
                            System.out.println("Wise choice Hero, can't go wrong with more health!");

                            if(gold >= 50){
                                hp += 10+level;
                                gold -= 50;

                                System.out.println("You now have " + gold + " remaining");
                            }

                        }else if(input.equals("2")){
                            System.out.println("Ah caster type eh? Well more mana means more magic!");

                            if(gold >= 50){
                                mp += 10+level;
                                gold -= 50;

                                System.out.println("You now have " + gold + " remaining");
                            }
                        }
                    }else if(input.equals("0")){
                        System.out.println("Farewell Hero...");
                    }
                }
            } else if (input.equals("2")) {
                System.out.println("You escape the dungeon with your life and loot. Though you wonder what may await you deeper yet.");
                System.out.println("Your total gold looted: " + gold);
                break;
            }

        }

        System.out.println("#######################");
        System.out.println("# Thanks for playing! #");
        System.out.println("#######################");


    }

    public void createGameScreen(){

        //Disable previous panels
        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.BLUE);
        con.add(mainTextPanel);

        mainTextArea = new JTextArea();
        mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setBackground(Color.BLACK);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);
    }

    public class TitleScreenHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            createGameScreen();
        }
    }
}
