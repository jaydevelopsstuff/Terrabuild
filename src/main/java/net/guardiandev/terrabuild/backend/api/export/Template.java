package net.guardiandev.terrabuild.backend.api.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Template {
    public static final String OptionalOpen = "<[";
    public static final String OptionalClose = "]>";
    public static final String Divider = "~";
    public static final String VariableOpen = "<{";
    public static final String VariableClose = "}>";

    public static String apply(String template, Optional[] optionals, Variable[] variables) {
        String optionalRun = optionalRun(template, optionals);
        return variableRun(optionalRun, variables);
    }

    private static String optionalRun(String template, Optional[] optionals) {
        String optionalString;
        StringBuilder templateBuilder = new StringBuilder(template);
        while((optionalString = StringUtils.substringBetween(templateBuilder.toString(), OptionalOpen, OptionalClose)) != null) {
            String templateUpdated = templateBuilder.toString();
            String[] splitted = optionalString.split(Divider);
            String name = splitted[0];
            String content = splitted[1];

            int openIndex = templateUpdated.indexOf(OptionalOpen);
            int closeIndex = templateUpdated.indexOf(OptionalClose) + 2;

            boolean use = false;
            for(Optional optional : optionals) {
                if(!optional.name.equals(name)) continue;
                if(optional.use) {
                    use = true;
                    templateBuilder.replace(openIndex, closeIndex, content);
                }
            }
            if(!use) {
                int separatorLength = System.lineSeparator().length();
                if((templateUpdated.indexOf(System.lineSeparator(), closeIndex) - closeIndex) <= separatorLength) closeIndex += separatorLength;
                templateBuilder.replace(openIndex, closeIndex, "");
            }
        }
        return templateBuilder.toString();
    }

    private static List<OptionalPosition> getOptionals(String template, Optional[] optionals) {
        String[] optionalStrings = StringUtils.substringsBetween(template, OptionalOpen, OptionalClose);
        List<String> names = new ArrayList<>();
        List<String> contents = new ArrayList<>();
        for(String optional : optionalStrings) {
            String[] splitted = optional.split(Divider);
            names.add(splitted[0]);
            contents.add(splitted[1]);
        }

        List<Integer> openingIndices = new ArrayList<>();
        List<Integer> closingIndices = new ArrayList<>();
        for(int i = -1; (i = template.indexOf(OptionalOpen, i + 1)) != -1; i++) {
            openingIndices.add(i);
        }
        for(int i = -1; (i = template.indexOf(OptionalClose, i + 1)) != -1; i++) {
            closingIndices.add(i);
        }

        List<OptionalPosition> optionalPositions = new ArrayList<>();
        int index = 0;
        for(int openingIndex : openingIndices) {
            int closingIndex = closingIndices.get(0);
            String name = names.get(index);
            String content = contents.get(index);

            optionalPositions.add(new OptionalPosition(name, content, openingIndex, closingIndex));
            index++;
        }
        return optionalPositions;
    }

    public static String variableRun(String template, Variable[] variables) {
        String variableName;
        StringBuilder templateBuilder = new StringBuilder(template);
        while((variableName = StringUtils.substringBetween(templateBuilder.toString(), VariableOpen, VariableClose)) != null) {
            String templateUpdated = templateBuilder.toString();

            int openIndex = templateUpdated.indexOf(VariableOpen);
            int closeIndex = templateUpdated.indexOf(VariableClose) + 2;

            boolean used = false;
            for(Variable variable : variables) {
                if(!variable.name.equals(variableName)) continue;
                templateBuilder.replace(openIndex, closeIndex, variable.value);
                used = true;
            }
            if(!used) {
                templateBuilder.replace(openIndex, closeIndex, "No Variable Assigned");
            }
        }
        return templateBuilder.toString();
    }

    @Getter
    @RequiredArgsConstructor
    public static class Optional {
        private final String name;
        private final boolean use;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Variable {
        private final String name;
        private final String value;
    }

    @Getter
    @RequiredArgsConstructor
    private static class OptionalPosition {
        private final String name;
        private final String content;
        private final int startIndex;
        private final int endIndex;
    }
}
