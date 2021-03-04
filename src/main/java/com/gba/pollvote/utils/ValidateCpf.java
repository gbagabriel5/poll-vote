package com.gba.pollvote.utils;

import com.gba.pollvote.config.ErrorsInfo;
import com.gba.pollvote.exception.InvalidCpfException;
import java.util.InputMismatchException;

public class ValidateCpf {
    public static void isCPF(String cpf) throws InvalidCpfException {
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11)) {
            throw new InvalidCpfException(ErrorsInfo.INVALID_CPF);
        }
        char dig10;
        char dig11;
        int sm;
        int i;
        int r;
        int num;
        int peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if (!((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))))
                throw new InvalidCpfException(ErrorsInfo.INVALID_CPF);
        } catch (InputMismatchException error) {
            throw new InvalidCpfException(ErrorsInfo.INVALID_CPF, error);
        }
    }
}
