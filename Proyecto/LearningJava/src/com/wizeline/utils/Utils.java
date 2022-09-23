package com.wizeline.utils;

import com.wizeline.enums.AccountType;
import com.wizeline.enums.Country;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static long randomAccountNumber(){
        return new Random().nextLong();
    }

    public static double randomBalance(){
        return new Random()
                .doubles(1000, 9000)
                .limit(1)
                .findFirst()
                .getAsDouble();
    }

    public static AccountType pickRandomCountType(){
        AccountType[] accountTypes = AccountType.values();
        return accountTypes[new Random().nextInt(accountTypes.length)];
    }

    public static String randomInt(){
        return String.valueOf(new Random().ints(1,10)
                .findFirst()
                .getAsInt());
    }

    public static String getCountry(Country country){
        Map<Country, String> countries = new HashMap<>();
        countries.put(Country.US, "United States");
        countries.put(Country.MX, "Mexico");
        countries.put(Country.FR, "France");

        return countries.get(country);
    }

    /* Uso de por lo menos una expresión regular: Exp reg valida contraseña */
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    // Definicion del patron para validar formato de fecha (dd-mm-yyyy)
    private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

    public static boolean isPasswordValid(String password) {
        // Valida la contraseña contra el patron que definimos
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isDateFormatValid(String date) {
        // Valida la fecha contra el patron que definimos
        return DATE_PATTERN.matcher(date).matches();
    }

    public static boolean validaFormatoFechaLocalDate(String date) {

        //String formattedDate = fecha.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate localDate = LocalDate.parse(date, formatter);

        System.out.println(formatter.format(localDate).toString());
        if(date.equals( formatter.format(localDate).toString())){
            return true;
        }
        return false;
    }

    public static String getString(String valor){
        if(valor != null){
            return valor;
        }
        return "";
    }

    public static boolean validarValorNulo(String valor){
        if(valor != null){
            return true;
        }
        return false;
    }


}
