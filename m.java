mport java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectedFieldsParser {

    public SelectedFields parse(String input) {
        SelectedFields selectedFields = new SelectedFields();
        selectedFields.setFields(new ArrayList<>());

        // Pattern to match the main structure
        Pattern mainPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
        Matcher mainMatcher = mainPattern.matcher(input);

        if (mainMatcher.find()) {
            String fieldsContent = mainMatcher.group(1).trim();

            // Pattern to match each field
            Pattern fieldPattern = Pattern.compile("(\\w+)\\s*\\{(.*?)\\}");
            Matcher fieldMatcher = fieldPattern.matcher(fieldsContent);

            while (fieldMatcher.find()) {
                SelectedField selectedField = new SelectedField();
                selectedField.setName(fieldMatcher.group(1));

                String subFieldsContent = fieldMatcher.group(2).trim();
                if (!subFieldsContent.isEmpty()) {
                    // Pattern to match subfields
                    Pattern subFieldPattern = Pattern.compile("(\\w+)");
                    Matcher subFieldMatcher = subFieldPattern.matcher(subFieldsContent);

                    selectedField.setSubFields(new ArrayList<>());
                    while (subFieldMatcher.find()) {
                        selectedField.getSubFields().add(subFieldMatcher.group(1));
                    }
                }

                selectedFields.getFields().add(selectedField);
            }
        }

        return selectedFields;
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
