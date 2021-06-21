package com.demidrolll.myphotos.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnglishLanguageConstraintValidator implements ConstraintValidator<EnglishLanguage, String> {

    private boolean withNumbers;
    private boolean withPunctuations;
    private boolean withSpecialSymbols;

    @Override
    public void initialize(EnglishLanguage englishLanguage) {
        this.withNumbers = englishLanguage.withNumbers();
        this.withPunctuations = englishLanguage.withPunctuations();
        this.withSpecialSymbols = englishLanguage.withSpecialSymbols();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String template = getValidationTemplate();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (template.indexOf(ch) == -1) {
                return false;
            }
        }
        return true;
    }

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String PUNCTUATIONS = ".,?!-:()'\"[]{}; \t\n";
    private static final String SPECIAL_SYMBOLS = "~#$%^*+-=_\\|/@`";

    private String getValidationTemplate() {
        StringBuilder template = new StringBuilder(LETTERS);
        if (withNumbers) {
            template.append(NUMBERS);
        }
        if (withPunctuations) {
            template.append(PUNCTUATIONS);
        }
        if (withSpecialSymbols) {
            template.append(SPECIAL_SYMBOLS);
        }
        return template.toString();
    }
}
