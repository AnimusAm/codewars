package hr.smilebacksmile.codewars.catalog.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Catalog {

    public static String NOTHING = "Nothing";

    public static String SEPARATOR = "\\n\\n";

    public static String NAME_PATTERN = "((?<=\\<name\\>).*(?=\\<\\/name\\>))";
    public static String PRICE_PATTERN = "((?<=\\<prx\\>).*(?=\\<\\/prx\\>))";
    public static String QUANTITY_PATTERN = "((?<=\\<qty\\>).*(?=\\<\\/qty\\>))";

    public static String catalog(String s, String article) {

      final String output = splitInput(s, SEPARATOR).stream()
                .map(Catalog::getItem)
                .filter(i -> Optional.ofNullable(i.getName())
                        .map(n -> n.contains(article))
                        .orElse(false)
                ).map(i -> i.getName() +
                        " > prx: $" +
                        i.getPrx() +
                        " qty: " +
                        i.getQty()
                ).collect(Collectors.joining("\n"));

        return output.isEmpty() ? NOTHING : output;
    }

    public static List<String> splitInput(final String input, final String separator) {
        return Arrays.asList(input.split(separator));
    }

    private static String match(final String input, final String stringPattern) {
        Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    private static CatalogDto getItem(final String input) {
        final String name = match(input, NAME_PATTERN);
        final String price = match(input, PRICE_PATTERN);
        final String quantity = match(input, QUANTITY_PATTERN);

        return new CatalogDto(name, price, quantity);
    }

    private static class CatalogDto {
        String name;
        String prx;
        String qty;

        public CatalogDto(String name, String prx, String qty) {
            this.name = name;
            this.prx = prx;
            this.qty = qty;
        }

        public String getName() {
            return name;
        }

        public String getPrx() {
            return prx;
        }

        public String getQty() {
            return qty;
        }
    }

}