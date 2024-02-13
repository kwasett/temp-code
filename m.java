mport java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectedFieldsParser {

    public SelectedFields parse(String input) {
        return parseFields(input.trim());
    }

    private SelectedFields parseFields(String input) {
        SelectedFields selectedFields = new SelectedFields();
        selectedFields.setFields(new ArrayList<>());

        // Pattern to match each field
        Pattern fieldPattern = Pattern.compile("(\\w+)\\s*\\{(.*?)\\}", Pattern.DOTALL);
        Matcher fieldMatcher = fieldPattern.matcher(input);

        while (fieldMatcher.find()) {
            SelectedField selectedField = new SelectedField();
            selectedField.setName(fieldMatcher.group(1));

            String subFieldsContent = fieldMatcher.group(2).trim();
            if (!subFieldsContent.isEmpty()) {
                // Recursive call to parse nested fields
                selectedField.setSubFields(parseFields(subFieldsContent).getFields());
            }

            selectedFields.getFields().add(selectedField);
        }

        return selectedFields;
    }
    }

    public static void main(String[] args) {
        String input = "{\n" +
                "    name {\n" +
                "        firstName,\n" +
                "        lastName\n" +
                "    },\n" +
                "    id\n" +
                "}";

        SelectedFieldsParser parser = new SelectedFieldsParser();
        SelectedFields selectedFields = parser.parse(input);

        // Print the parsed object
        System.out.println(selectedFields);
    }
}
