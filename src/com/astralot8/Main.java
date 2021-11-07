package com.astralot8;

interface TextAnalyzer {
    Label processText(String text);
}

abstract class KeywordAnalyzer implements TextAnalyzer {
    abstract protected Label getLabel();

    abstract protected String[] getKeyWords();

    public Label processText(String text) {

        return Label.OK;
    }

}

public class Main {


    static class SpamAnalyzer extends KeywordAnalyzer {
        private String[] keywords;

        protected String[] getKeyWords() {
            return keywords;
        }

        @Override
        protected Label getLabel() {
            return Label.SPAM;
        }

        public Label processText(String text) {
            for (String keyword : getKeyWords()) {
                if (text.contains(keyword)) {
                    return getLabel();
                }
            }
            return Label.OK;
        }

        SpamAnalyzer(String a, String b) {
                keywords = new String[] {a,b};
        }

    }

    static class NegativeTextAnalyzer extends KeywordAnalyzer {
        private String[] keywords = {":(", "=(", ":|"};

        protected String[] getKeyWords() {
            return keywords;
        }

        @Override
        protected Label getLabel() {
            return Label.NEGATIVE_TEXT;
        }

        public Label processText(String text) {
            for (String keyword : getKeyWords()) {
                if (text.contains(keyword)) {
                    return getLabel();
                }
            }
            return Label.OK;
        }

        NegativeTextAnalyzer() {
            this.keywords = getKeyWords();
        }

    }


    // класс проверяющий слишком длинные комментарии
    static class TooLongTextAnalyzer implements TextAnalyzer {
        // создаем переменную определяющую максимальную длинну коментария
        private int maxLength;

        // создаем комнструктор класса
        TooLongTextAnalyzer(int maxLength) {
            this.maxLength = maxLength;
        }

        public Label processText(String text) {
            if (text.length() > maxLength) {
                return Label.TOO_LONG;
            } else {

                return Label.OK;
            }
        }
    }


    public static Label checkLabels(TextAnalyzer[] analyzers, String text) {
        for (TextAnalyzer anal : analyzers) {
            Label label = anal.processText(text);
            if (label != Label.OK) {
                return label;
            } else {
                continue;
            }
        }
        return Label.OK;
    }

    public static void main(String[] args) {
        TooLongTextAnalyzer tooLong = new TooLongTextAnalyzer(10);
        SpamAnalyzer spam = new SpamAnalyzer("Ебану", "Дичь");
        NegativeTextAnalyzer negative = new NegativeTextAnalyzer();

        TextAnalyzer[] A = {tooLong,spam, negative};
        System.out.println(checkLabels(A, "Ебануться туфли гнуться"));
        System.out.println(checkLabels(A, " =( ть"));
        System.out.println(checkLabels(A, "Ебан :("));
        System.out.println(checkLabels(A, "Ебану"));
    }
}
