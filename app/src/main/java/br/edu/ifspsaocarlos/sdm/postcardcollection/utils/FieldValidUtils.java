package br.edu.ifspsaocarlos.sdm.postcardcollection.utils;

public class FieldValidUtils {

    public static boolean isEmailValid(String email) {
        //TODO: Melhorar a validação do Email
        return ( email.contains("@") && email.contains(".") );
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Melhorar a validação da Senha
        return password.length() >= 6;
    }

}
