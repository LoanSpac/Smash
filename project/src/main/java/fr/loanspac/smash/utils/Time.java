package fr.loanspac.smash.utils;

public class Time {
    public static String convert(int seconds) {
        seconds = seconds % (24 * 3600);
        seconds %= 3600;
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
