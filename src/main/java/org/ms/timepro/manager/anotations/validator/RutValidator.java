package org.ms.timepro.manager.anotations.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ms.timepro.manager.anotations.Rut;

/**
 * @author Nelson Alvarado
 * Funcion para validar el rut, actualmente solo valida el formato se debe mejorar.
 *
 *
 * TODO: Se deben agregar estas notaciones en alguna libreria tipo common
 */
public class RutValidator implements ConstraintValidator<Rut, String> {

    @Override
    public boolean isValid(final String rutValue, final ConstraintValidatorContext context) {
        return isValidFormat(rutValue);
    }

    private boolean isValidFormat(final String rut) {
        boolean value =  rut.matches("^[0-9]+-[0-9kK]$");
        if (!value){ return false; }
        String[] stringRut = rut.split("-");
        return stringRut[1].toLowerCase().equals(RutValidator.dv(stringRut[0]));
    }

    public static String dv ( String rut ) {
        int M=0,S=1,T=Integer.parseInt(rut);
        for (;T!=0;T=(int) Math.floor(T/=10))
            S=(S+T%10*(9-M++%6))%11;
        return ( S > 0 ) ? String.valueOf(S-1) : "k";
    }
}