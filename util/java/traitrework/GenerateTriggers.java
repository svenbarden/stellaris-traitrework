package traitrework;

public class GenerateTriggers {
    private static final String MAX_AMOUNT_TEMPLATE = "traitrework_trigger_tile_has_${resourceType}_most = {\n" +
            "\tor = {\n" +
            "\t\thas_resource = {\n" +
            "\t\t\ttype = ${resourceType}\n" +
            "\t\t\tamount > ${maxResourceAmount}\n" +
            "\t\t}\n";
    private static final String AMOUNT_TEMPLATE = "\t\tand = {\n" +
            "\t\t\thas_resource = {\n" +
            "\t\t\t\ttype = ${resourceType}\n" +
            "\t\t\t\tamount = ${amount}\n" +
            "\t\t\t}\n" +
            "\t\t\tnor = {\n${amountCheck}" +
            "\t\t\t}\n" +
            "\t\t}\n";
    private static final String AMOUNT_CHECK_TEMPLATE = "\t\t\t\thas_resource = {\n" +
            "\t\t\t\t\ttype = ${resourceType}\n" +
            "\t\t\t\t\tamount > ${amount}\n" +
            "\t\t\t\t}\n";
    private static final String AMOUNT_END_TEMPLATE =
            //"\t\t\t}\n" +
            //"\t\t}\n" +
            "\t}\n" +
            "}\n";
    private static final int MAX_RESOURCE_AMOUNT = 4;


    private GenerateTriggers() {}



    public static void main(String[] args) {
        final String[] resourceTypes = {"minerals", "energy", "physics_research", "society_research", "engineering_research"};

        StringBuilder triggers = new StringBuilder();
        for (String resourceType : resourceTypes) {
            triggers.append(MAX_AMOUNT_TEMPLATE
                    .replace("${resourceType}", resourceType)
                    .replace("${maxResourceAmount}", String.valueOf(MAX_RESOURCE_AMOUNT)));
            for(int i = MAX_RESOURCE_AMOUNT; i > 0; i--) {
                StringBuilder amountCheck = new StringBuilder();
                for (String checkResourceType : resourceTypes) {
                    if(!checkResourceType.equals(resourceType)) {
                        amountCheck.append(AMOUNT_CHECK_TEMPLATE
                                .replace("${resourceType}", checkResourceType)
                                .replace("${amount}", String.valueOf(i)));
                    }
                }
                triggers.append(AMOUNT_TEMPLATE
                        .replace("${resourceType}", resourceType)
                        .replace("${amount}", String.valueOf(i))
                        .replace("${amountCheck}", amountCheck));
            }
            triggers.append(AMOUNT_END_TEMPLATE);
        }

        System.out.println(triggers);
    }
}
