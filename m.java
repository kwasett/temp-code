public SelectedFields parse(String input) {
        return parseFields(input.trim());
    }

    private SelectedFields parseFields(String input) {
        SelectedFields selectedFields = new SelectedFields();
        selectedFields.setFields(new ArrayList<>());

        // Pattern to match each field, with or without subfields
        Pattern fieldPattern = Pattern.compile("(\\w+)\\s*\\{([^{}]*)\\}");
        Matcher fieldMatcher = fieldPattern.matcher(input);

        while (fieldMatcher.find()) {
            SelectedField selectedField = new SelectedField();
            selectedField.setName(fieldMatcher.group(1).trim());

            String subFieldsContent = fieldMatcher.group(2).trim();
            if (!subFieldsContent.isEmpty()) {
                // Recursive call to parse nested fields
                selectedField.setSubFields(parseFields(subFieldsContent).getFields());
            }

            selectedFields.getFields().add(selectedField);
        }

        return selectedFields;
    }
