package com.codecool.reshuffle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Reshuffle {

    private List<Team> oldTeams = new ArrayList<>();
    private final List<Team> newTeams = new ArrayList<>();
    private Map<String, ArrayList<ArrayList<String>>> history = new HashMap<>();
    private Map<String, ArrayList<String>> bestHash = new HashMap<>();
    int round;

    public void letsRoll(){
        newTeams.clear();
        Team newTeam = new Team();
        List<String> temp = temporaryList();Collections.shuffle(temp);
        int index2 = 1;int index1 = 0; int tries=0;
        while (temp.size() > 0){
            if (index1 >= temp.size()-1){index1 = 0;}
            String o1 = temp.get(index1++);
            newTeam.addMember(o1);
            while (newTeam.getMembers().size() < 4){
                //System.out.println(newTeams);
                if (temp.size() == 1){
                    String last = temp.get(0);
                    newTeam.addMember(last);
                } else {
                    if (index2 >= temp.size()-1){ index2 = 1;}
                    String o2 = temp.get(index2++);
                    //if (!bestHash.get(o1).contains(o2) && o1.charAt(0)-'0' != o2.charAt(0)-'0'){
                        if (!bestHash.get(o1).toString().trim().contains(o2) && o1.charAt(0)-'0' != o2.charAt(0)-'0'){
                            newTeam.addMember(o2);
                            //System.exit(3);
                            //}

                    } else { tries++;

                        if (tries > 10){
                            Collections.shuffle(temp);
                            if (tries > 50){
                                temp = temporaryList();
                                newTeams.clear();
                                newTeam = new Team();
                                tries = 0;
                            } } } } }

            if (isReadyToGo(newTeam)){
                newTeams.add(newTeam);
                for (int i = 0; i < 4; i++) {
                    temp.remove(newTeam.getMembers().get(i));
                }
                //temp.removeAll(newTeam.getMembers());
                newTeam = new Team();
            } else {
                newTeam = new Team();
            }
        }
        /*try {
            for (int i = 0; i < 4; i++) {
                newTeam.addMember(temp.get(i));
            }

        } catch (IndexOutOfBoundsException e){
            System.out.println(e);
            System.out.println(temp);
            System.out.println(newTeam);
            System.out.println(newTeams);
            System.out.println();
            System.exit(1);
        }*/
        //newTeams.add(newTeam);
    }
    public boolean isReadyToGo(Team tempTeam){
        for (String member: tempTeam.getMembers()) {
            for (String member2: tempTeam.getMembers()) {
                if (member != member2){
                    if (bestHash.get(member).contains(member2)){
                        return false;
                    }
                    /*if (member.charAt(0)-'0' == member2.charAt(0)-'0'){
                        return false;
                    }*/
                }
            }
        }
        return true;
    }

    /*public void letsRoll() {
        newTeams.clear();
        Team newTeam = new Team();
        List<String> temp = temporaryList();Collections.shuffle(temp);
        int index = 0;int tries = 0;boolean isOver = false;
        while (!isOver){
            while (temp.size() > 4){
                String o1 = temp.get(index++);
                if (index >= temp.size()-4){ index = 0; }

                String o2 = temp.get(index+1);String o3 = temp.get(index+2);String o4 = temp.get(index+3);
                if (isSameChar(o1.charAt(0), o2.charAt(0), o3.charAt(0), o4.charAt(0))){
                    newTeam.addMember(o1); temp.remove(o1);newTeam.addMember(o2); temp.remove(o2);
                    newTeam.addMember(o3); temp.remove(o3);newTeam.addMember(o4); temp.remove(o4);
                } else {
                    tries++;index++;
                }
                if (newTeam.getMembers().size() == 4){
                    newTeams.add(newTeam);newTeam = new Team();
                }
                if (tries > 10){
                    Collections.shuffle(temp);
                    if (tries > 50){
                        newTeams.clear();temp = temporaryList();
                        tries = 0;
                    } } }

            if (isSameChar(temp.get(0).charAt(0), temp.get(1).charAt(0), temp.get(2).charAt(0), temp.get(3).charAt(0))){
                newTeam.addMember(temp.get(0));newTeam.addMember(temp.get(1));newTeam.addMember(temp.get(2));newTeam.addMember(temp.get(3));
                newTeams.add(newTeam);
                isOver = true;
            } else {
                temp = temporaryList();
                newTeams.clear();
            } } }

    public boolean isSameChar(char one, char two, char three, char four){
        return one != two && one != three && two != three && one != four && two != four && three != four;
    }*/

    public List<String> temporaryList(){
        List<String> temp = new ArrayList<>();
        for (int team = 0; team < 7; team++) {
            for (int member = 0; member < 4; member++) {
                temp.add(oldTeams.get(team).getMembers().get(member));
            }
        }
        return temp;
    }

    public void loadOldTeams(String fileName) {
        oldTeams.clear();
        try {
            Scanner s = new Scanner(new File(fileName));
            Team team = null;
            while (s.hasNext()) {
                String nextLine = s.next();
                if (nextLine.contentEquals("#")) {
                    team = null;
                } else {
                    if (team == null) {
                        team = new Team();
                        oldTeams.add(team);
                    }
                    team.addMember(nextLine);
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printOldTeams() {
        System.out.println("\nOld: " + oldTeams);
    }

    public void printNewTeams() {
        System.out.println("New: " + newTeams+"\n");
    }
    
    public boolean isGood() {
        for (Team team: newTeams) {
            int counter1 = 0;int counter2 = 0;int counter3 = 0;int counter4 = 0;int counter5 = 0;int counter6 = 0;int counter7 = 0;
            for (String member: team.getMembers()) {
                //member = "1_A";
                switch (member.charAt(0)){
                    case '1': counter1++;break;
                    case '2': counter2++;break;
                    case '3': counter3++;break;
                    case '4': counter4++;break;
                    case '5': counter5++;break;
                    case '6': counter6++;break;
                    case '7': counter7++;break;
                }
            }
            //System.out.printf("%s, %s, %s, %s, %s, %s, %s", counter1, counter2, counter3, counter4, counter5, counter6, counter7);
            if (counter1 > 1 || counter2 > 1 || counter3 > 1 || counter4 > 1 || counter5 > 1 || counter6 > 1 || counter7 > 1){
                //System.out.println(team + "is not good\n");
                return false;
                //System.exit(1);
            } else {
                //System.out.printf("%s, %s, %s, %s, %s, %s, %s", counter1, counter2, counter3, counter4, counter5, counter6, counter7);
                //System.out.println(team + " is good");
            }
        }
        return true;
    }

    private void bestHashMapEver(){
        for (Team team: oldTeams) {
            for (String member: team.getMembers()) {
                List<String> tempList = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    if (member != team.getMembers().get(i)){
                        tempList.add(team.getMembers().get(i));
                    }
                }
                bestHash.put(member, new ArrayList<>(tempList));
            }
        }
        System.out.println(bestHash);
    }

    public void updateBestHashMap(){
        oldTeams.clear();
        for (Team team: newTeams) {
            for (String member: team.getMembers()) {
                for (int i = 0; i < 4; i++) {
                    if (member != team.getMembers().get(i)){
                        bestHash.get(member).add(team.getMembers().get(i));
                    }
                }
            }
            oldTeams.add(team);
        }
        System.out.println(bestHash);
    }

    private void setHashMap(){
        for (Team team: oldTeams) {
            for (String member: team.getMembers()) {
                List temp = new ArrayList();
                temp.addAll(team.getMembers());
                history.put(member, new ArrayList<>());
                history.get(member).add(new ArrayList<>(temp));
            }
        }
    }

    private void updateHashMap(){
        for (Team team: newTeams) {                     //7
            for (String member: team.getMembers()) {    //4
                //history.get(member).add(team.getMembers());
                history.get(member).add(new ArrayList<>(team.getMembers()));
            }
        }
        System.out.println(history);
    }

    public static void main(String[] args) {
            boolean isGood;
            int howManyRounds = 4;
            Reshuffle reshuffle = new Reshuffle();
            reshuffle.loadOldTeams("resources/teams_sample.txt");
            reshuffle.setHashMap();
            reshuffle.bestHashMapEver();
            while(reshuffle.round < howManyRounds) {
                reshuffle.printOldTeams();
                isGood = false;
                while (!isGood){
                    reshuffle.letsRoll();
                    isGood = reshuffle.isGood();
                }
                reshuffle.printNewTeams();
                reshuffle.updateHashMap();
                reshuffle.updateBestHashMap();
                reshuffle.round++;

            }
            int falseCounter = 0;
        for (Team team: reshuffle.newTeams) {
            for (String member: team.getMembers()) {
                for (int i = 0; i < reshuffle.bestHash.get(member).size(); i++) {
                    String a = reshuffle.bestHash.get(member).get(i);
                    for (int j = 0; j < reshuffle.bestHash.get(member).size(); j++) {
                        if (reshuffle.bestHash.get(member).get(j) == a){
                            falseCounter++;
                            if (falseCounter > 1){
                                System.out.println("\n"+reshuffle.bestHash.get(member));
                                System.out.println(a);
                                System.exit(2);
                            }
                        }
                    }
                    falseCounter = 0;

                }
            }
        }
        System.out.println("\nEverything is fine. :)");
    }

}
