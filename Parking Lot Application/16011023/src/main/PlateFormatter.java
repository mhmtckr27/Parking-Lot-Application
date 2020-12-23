package main;

class PlateFormatter {

    static String formatPlate(String plate){
        StringBuilder finalString = new StringBuilder();
        //to correctly format user input
        String[] tokens = plate.replaceAll("[^a-zA-Z0-9 ]", "").toUpperCase().split("\\s+");
        for(String str : tokens){
              finalString.append(str);
        }
        return finalString.toString();
    }

    static String printPlate(String plate){

        StringBuilder finalString = new StringBuilder();
        int i = 0;
        while (i < plate.length() && Character.isDigit(plate.charAt(i))){
            finalString.append(plate.charAt(i));
            ++i;
        }
        finalString.append(" ");
        while (i < plate.length() && Character.isAlphabetic(plate.charAt(i))){
            finalString.append(plate.charAt(i));
            ++i;
        }
        finalString.append(" ");
        while (i<plate.length()){
            finalString.append(plate.charAt(i));
            i++;
        }
        return finalString.toString();
    }
}
